package com.gamejelly.gong2.meta;

import java.util.List;

import com.gamejelly.gong2.meta.base.SimpleModel;
import com.hadoit.game.common.framework.dao.annotation.Column;
import com.hadoit.game.common.framework.dao.annotation.Index;
import com.hadoit.game.common.framework.dao.annotation.Table;
import com.hadoit.game.common.framework.dao.convert.JsonColumnConvert;

@Table(recursion = true, indexs = { @Index(columns = { "receiverId", "createTime" }),
		@Index(columns = { "receiverId", "majorType", "createTime" }) })
public class UserMessageModel extends SimpleModel {
	private static final long serialVersionUID = 1L;

	private String senderId;

	private String receiverId;

	private String senderName;

	private int senderLv;

	/**
	 * 主类型(玩家消息，系统消息)
	 */
	@Column(index = true)
	private int majorType;

	/**
	 * 子类型
	 */
	@Column(index = true)
	private int childType;

	/**
	 * 内容
	 */
	@Column(length = 65535)
	private String content;

	/**
	 * 动作
	 */
	@Column(length = 65535, convertClazz = JsonColumnConvert.class)
	private List<MessageAction> actionList;

	@Column(length = 65535 * 10)
	private String fightData;

	private boolean win;

	/**
	 * sender和receiver的关系类型
	 */
	@Column(ignore = true)
	private int relationType;

	private long createTime;

	/**
	 * anonymousMail是否是匿名邮件
	 */
	private boolean anonymousMail;

	/**
	 * anonymousPeopleName匿名者是谁
	 */
	private String anonymousPeopleName;

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getSenderLv() {
		return senderLv;
	}

	public void setSenderLv(int senderLv) {
		this.senderLv = senderLv;
	}

	public int getMajorType() {
		return majorType;
	}

	public void setMajorType(int majorType) {
		this.majorType = majorType;
	}

	public int getChildType() {
		return childType;
	}

	public void setChildType(int childType) {
		this.childType = childType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MessageAction> getActionList() {
		return actionList;
	}

	public void setActionList(List<MessageAction> actionList) {
		this.actionList = actionList;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getFightData() {
		return fightData;
	}

	public void setFightData(String fightData) {
		this.fightData = fightData;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public String getAnonymousPeopleName() {
		return anonymousPeopleName;
	}

	public void setAnonymousPeopleName(String anonymousPeopleName) {
		this.anonymousPeopleName = anonymousPeopleName;
	}

	public boolean isAnonymousMail() {
		return anonymousMail;
	}

	public void setAnonymousMail(boolean anonymousMail) {
		this.anonymousMail = anonymousMail;
	}
}
