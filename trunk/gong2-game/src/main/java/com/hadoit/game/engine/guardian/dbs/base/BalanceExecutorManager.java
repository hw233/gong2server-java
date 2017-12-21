package com.hadoit.game.engine.guardian.dbs.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.util.internal.ExecutorUtil;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy 2012-12-13
 * 
 */ 
public class BalanceExecutorManager implements Runnable {
	private final AtomicBoolean disposed = new AtomicBoolean(false);
	private final LinkedBlockingQueue<BalanceTask> taskQueue = new LinkedBlockingQueue<BalanceTask>();
	private final Map<String, Queue<BalanceTask>> waitingQueueMap = Collections
			.synchronizedMap(new HashMap<String, Queue<BalanceTask>>());
	private final ExecutorService mainThread;
	private final ExecutorService[] threads;
	private final LinkedBlockingQueue<ExecutorService> workThreadPool;

	public BalanceExecutorManager(int workThreads) {
		assert workThreads > 0;
		ThreadFactory dbsMainTf = new ThreadFactoryBuilder().setNameFormat("Dbs-main-%d").setDaemon(false).build();
		ThreadFactory dbsWorkerTf = new ThreadFactoryBuilder().setNameFormat("Dbs-worker-%d").setDaemon(false).build();

		mainThread = Executors.newFixedThreadPool(1, dbsMainTf);
		threads = new ExecutorService[workThreads];
		workThreadPool = new LinkedBlockingQueue<ExecutorService>();
		for (int i = 0; i < workThreads; ++i) {
			threads[i] = Executors.newFixedThreadPool(1, dbsWorkerTf);
			workThreadPool.offer(threads[i]);
		}
	}

	/**
	 * 直接执行，不做balance
	 * 
	 * @param command
	 */
	public void exec(Runnable command) {
		if (disposed.get()) {
			return;
		}
		taskQueue.offer(new BalanceTask(null, command));
	}

	/**
	 * 根据type+balanceId做balance；id>0做balance，否则不做balance
	 * 
	 * @param type
	 * @param id
	 * @param command
	 */
	public void execBalance(String type, long id, Runnable command) {
		String balanceId = null;
		if (id > 0) {
			balanceId = type + "_" + id;
		}
		execBalance(balanceId, command);
	}

	public void execBalance(String balanceId, Runnable command) {
		if (disposed.get()) {
			return;
		}
		taskQueue.offer(new BalanceTask(balanceId, command));
	}

	public void start() {
		if (disposed.get()) {
			throw new IllegalStateException("balance executor manager already disposed!");
		}
		mainThread.execute(this);
	}

	public void dispose(boolean awaitFinish) {
		if (disposed.getAndSet(true)) {
			return;
		}
		if (awaitFinish) {
			awaitFinish();
		}
		ExecutorUtil.terminate(mainThread);
		ExecutorUtil.terminate(threads);
	}

	private void awaitFinish() {
		int i = 0;
		while (true) {
			if (taskQueue.size() == 0 && waitingQueueMap.size() == 0) {
				break;
			}
			GuardianLogger.info("balance executor manager await finish " + (i++) + "s... ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				GuardianLogger.error(e, "balance executor manager interrupted!");
			}
		}
		GuardianLogger.info("balance executor manager finished!");
	}

	public void run() {
		GuardianLogger.info("balance executor main thread start! worker=", threads.length);
		BalanceTask task;
		while (true) {
			try {
				task = taskQueue.take();
				runTask(task);
			} catch (InterruptedException e) {
				GuardianLogger.info("balance executor manager task queue take interrupted!");
				break;
			} catch (Exception e) {
				GuardianLogger.error(e, "balance executor manager run exception!");
			}
		}
	}

	private void runTask(BalanceTask task) throws InterruptedException {
		if (task.isBalance()) {
			boolean r = false;
			synchronized (waitingQueueMap) {
				Queue<BalanceTask> queue = waitingQueueMap.get(task.getBalanceId());
				if (queue == null) {
					queue = new LinkedList<BalanceTask>();
					waitingQueueMap.put(task.getBalanceId(), queue);
					r = true;
				}
				queue.offer(task);
			}
			if (r) {
				ExecutorService executorService = workThreadPool.take();
				execute(executorService, task);
			}
		} else {
			ExecutorService executorService = workThreadPool.take();
			execute(executorService, task);
		}
	}

	private void execute(ExecutorService executorService, BalanceTask task) {
		task.setExecutorService(executorService);
		executorService.execute(task);
	}

	private final class BalanceTask implements Runnable {
		private ExecutorService executorService;
		private String balanceId;
		private Runnable runnable;

		public BalanceTask(String balanceId, Runnable runnable) {
			this.balanceId = balanceId;
			this.runnable = runnable;
		}

		public void setExecutorService(ExecutorService executorService) {
			this.executorService = executorService;
		}

		public String getBalanceId() {
			return balanceId;
		}

		public boolean isBalance() {
			return balanceId != null;
		}

		public void run() {
			try {
				runnable.run();
			} catch (Exception e) {
				GuardianLogger.error(e, "balance task run exception for balanceId: " + getBalanceId());
			} finally {
				afterRun();
			}
		}

		private void afterRun() {
			if (!isBalance()) {
				workThreadPool.offer(executorService);
				return;
			}
			BalanceTask newTask;
			synchronized (waitingQueueMap) {
				Queue<BalanceTask> queue = waitingQueueMap.get(getBalanceId());
				queue.poll();
				newTask = queue.peek();
				if (newTask == null) {
					waitingQueueMap.remove(getBalanceId());
				}
			}
			if (newTask == null) {
				workThreadPool.offer(executorService);
			} else {
				execute(executorService, newTask);
			}
		}
	}

}
