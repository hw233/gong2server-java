package com.hadoit.game.engine.guardian.gas.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import com.hadoit.game.common.lang.MultivaluedMap;
import com.hadoit.game.engine.core.executor.TimerListener;
import com.hadoit.game.engine.guardian.gas.GasManager;

/**
 * @author bezy 2012-9-17
 * 
 */
public class TimerManager implements Timerable {
	private static final AtomicLong timerIdx = new AtomicLong(0);
	private final Map<Long, EntityTimer> entityTimerMap = new HashMap<Long, EntityTimer>();
	private final MultivaluedMap<Long, Long> entityTimerTickMap = new MultivaluedMap<Long, Long>(LinkedList.class);

	private final GasManager context;

	public TimerManager(GasManager context) {
		this.context = context;
	}

	@Override
	public long addTimer(TimerListener listener, float start, float interval, Object... params) {
		long idx = timerIdx.incrementAndGet();
		long startTick = context.getServerTick() + Math.max(Math.round(start * context.getServerFreq()), 1);
		int periodTick = 0;
		if (interval > 0f) {
			periodTick = Math.max(Math.round(interval * context.getServerFreq()), 1);
		}
		EntityTimer entityTimer = new EntityTimer(idx, listener, params, startTick, periodTick);
		entityTimerMap.put(idx, entityTimer);
		entityTimerTickMap.add(entityTimer.getStartTick(), entityTimer.getId());
		return idx;
	}

	@Override
	public void clearTimer(long id) {
		entityTimerMap.remove(id);
	}

	public void destroy() {
		entityTimerMap.clear();
		entityTimerTickMap.clear();
	}

	public void runOnTick() {
		long curTick = context.getServerTick();
		Queue<Long> timerQueue = (Queue<Long>) entityTimerTickMap.get(curTick);
		if (timerQueue != null) {
			try {
				Long id;
				EntityTimer entityTimer;
				while ((id = timerQueue.poll()) != null) {
					entityTimer = entityTimerMap.get(id);
					if (entityTimer == null) {
						continue;
					}
					if (entityTimer.getPeriodTick() > 0) {
						entityTimer.setStartTick(curTick + entityTimer.getPeriodTick());
						entityTimerTickMap.add(entityTimer.getStartTick(), entityTimer.getId());
					} else {
						entityTimerMap.remove(entityTimer.getId());
					}
					entityTimer.run();
				}
			} finally {
				entityTimerTickMap.remove(curTick);
			}
		}
	}

}
