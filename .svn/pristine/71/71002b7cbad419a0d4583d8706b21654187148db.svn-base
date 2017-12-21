package com.gamejelly.game.gong2.login.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.dao.InvitationCodeRecordDao;
import com.gamejelly.game.gong2.login.dao.InvitationPlayerModelDao;
import com.gamejelly.game.gong2.login.meta.InvitationCodeRecord;
import com.gamejelly.game.gong2.login.meta.InvitationPlayerModel;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.common.lang.Triple;

@Component("invitationService")
@Transactional
public class InvitationService {

	@Autowired
	private InvitationCodeRecordDao invitationCodeRecordDao;

	@Autowired
	private InvitationPlayerModelDao invitationPlayerModelDao;

	public void addInvitationCode(InvitationCodeRecord invitationCodeRecord) {
		if (!invitationCodeRecordDao.existByCondition("invitationCode=?", invitationCodeRecord.getInvitationCode())) {
			invitationCodeRecordDao.addObject(invitationCodeRecord);
		}
	}

	public Pair<Integer, String> verifyAndUseCode(InvitationPlayerModel invitationPlayerModel, int maxInviteCount) {
		InvitationCodeRecord invitationCodeRecord = invitationCodeRecordDao.getObjectByCondition("invitationCode=?",
				invitationPlayerModel.getInvitationCode());
		if (invitationCodeRecord == null) {
			return Pair.newInstance(-1, null);// 不存在
		}
		if (StringUtils.isNotBlank(invitationCodeRecord.getAccountName())
				&& invitationCodeRecord.getAccountName().equalsIgnoreCase(invitationPlayerModel.getAccountName())) {
			return Pair.newInstance(-4, null);// 自己不能邀请自己
		}
		int inviteCount = invitationCodeRecord.getInviteCount();
		if (inviteCount >= maxInviteCount) {
			return Pair.newInstance(-2, null);// 邀请数量超上限
		}
		if (invitationPlayerModelDao.existByCondition("crossServerId = ? and avatarId = ?",
				invitationPlayerModel.getCrossServerId(), invitationPlayerModel.getAvatarId())) {
			return Pair.newInstance(-3, null);// 已经被邀请
		}
		invitationPlayerModelDao.addObject(invitationPlayerModel);
		invitationCodeRecordDao.incrColumnCountByCondition("invitationCode=?", "inviteCount", 1,
				invitationPlayerModel.getInvitationCode());
		return Pair.newInstance(0,
				getCrossPlayerIdentify(invitationCodeRecord.getCrossServerId(), invitationCodeRecord.getAvatarId()));
	}

	public static String getCrossPlayerIdentify(String crossServerId, String avatarId) {
		return crossServerId + "_" + avatarId;
	}

	public List<InvitationPlayerModel> getPlayersByCode(String code) {
		return invitationPlayerModelDao.getListByFullCondition("invitationCode=?", code);
	}

	public void changeInvitePlayerData(final String crossServerId, final String avatarId, int lv, int vipLv) {
		InvitationPlayerModel invitationPlayerModel = invitationPlayerModelDao.getObjectByCondition(
				"crossServerId = ? and avatarId = ?", crossServerId, avatarId);
		System.out.println("invitationPlayerModel----null-----" + invitationPlayerModel);
		if (invitationPlayerModel != null) {
			invitationPlayerModelDao.updateColumnValue(new String[] { "lv", "vipLv" }, new Object[] { lv, vipLv },
					invitationPlayerModel.getId());
		}
	}

	public Triple<Integer, Integer, Integer> calcInvitationByCode(String code, int thresholdLv) {
		int totalCount = invitationPlayerModelDao.getCountByCondition("invitationCode=?", code);
		int thresholdLvCount = invitationPlayerModelDao.getCountByCondition("invitationCode=? and lv >= ?", code,
				thresholdLv);
		Integer maxVipLv = invitationPlayerModelDao.getCommonJdbcTemplate().queryForObject(
				"select max(vipLv) from InvitationPlayerModel where invitationCode=?", new Object[] { code },
				Integer.class);
		return Triple.newInstance(totalCount, thresholdLvCount, maxVipLv == null ? 0 : maxVipLv);
	}

}
