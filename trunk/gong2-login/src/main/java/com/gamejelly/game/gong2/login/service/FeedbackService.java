package com.gamejelly.game.gong2.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.FeedbackDao;
import com.gamejelly.game.gong2.login.meta.Feedback;

@Transactional
@Component("feedbackService")
public class FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;

	public void addFeedback(Feedback fb) {
		feedbackDao.addObject(fb);
	}

	public List<Feedback> getFeedbackBy(String avatarId) {
		return feedbackDao.getListByFullCondition("avatarId = ? order by createTime desc limit 100", avatarId);
	}

	public List<Feedback> getFeedbackList(int limit, long offset) {
		return feedbackDao.getListByCondition(null, "createTime", false, limit, offset, new Object[] {});
	}
	
	public int getFeedbackCount() {
		return feedbackDao.getCount();
	}

	public void replyFeedback(long id, String replyContent) {
		Feedback fb = feedbackDao.getById(id);
		if (fb == null) {
			return;
		}
		fb.setReply(replyContent);
		fb.setReplyTime(System.currentTimeMillis());
		feedbackDao.updateObject(fb);
	}

	public void removeFeedback(long id) {
		feedbackDao.deleteById(id);
	}
}
