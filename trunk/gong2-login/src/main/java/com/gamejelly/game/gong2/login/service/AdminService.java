package com.gamejelly.game.gong2.login.service;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamejelly.game.gong2.login.utils.FsGameLoginConst;
import com.gamejelly.game.gong2.login.utils.LoggerUtils;
import com.hadoit.game.common.lang.Pair;

@Transactional
@Component("adminService")
public class AdminService {
	@Autowired
	@Value("${config.admin_account}")
	private String adminAccount;

	@Autowired
	@Value("${config.admin_account_pass}")
	private String adminAccountPass;

	@Autowired
	private GameAccountService gameAccountService;

	@PostConstruct
	void init() {
		Pair<Integer, String> r = gameAccountService.createAccount(adminAccount, DigestUtils.md5Hex(adminAccountPass),
				"", "", "", "", "");
		if (r != null && r.getFirst() == FsGameLoginConst.CODE_OK) {
			LoggerUtils.info("Create " + adminAccount + " success!");
		}
	}

	public boolean checkAdmin(String name, String pass) {
		if (!adminAccount.equals(name) || !adminAccountPass.equals(pass)) {
			return false;
		}
		return true;
	}
}
