package com.gamejelly.game.gong2.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.NoticeDao;
import com.gamejelly.game.gong2.login.meta.Notice;

@Transactional
@Component("noticeService")
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	public Notice getRecentlyNotice() {
		return noticeDao.getRecentlyNotice();
	}

	public void addNewNotice(String text) {
		Notice newNotice = new Notice();
		newNotice.setContent(text);
		newNotice.setCreateTime(System.currentTimeMillis());
		noticeDao.addObject(newNotice);
	}

	public void updateNotice(int id, String text) {
		Notice old = noticeDao.getById(id);
		if (old == null) {
			addNewNotice(text);
		}

		old.setContent(text);
		old.setCreateTime(System.currentTimeMillis());
		noticeDao.updateObject(old);
	}

	public void delNotice(int id) {
		noticeDao.deleteByCondition("id=?", id);
	}

	public List<Notice> getNoticeList() {
		return noticeDao.getAll();
	}
}
