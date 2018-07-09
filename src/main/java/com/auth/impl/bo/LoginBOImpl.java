package com.auth.impl.bo;

import com.auth.bo.LoginBO;
import com.auth.dao.LoginDAO;
import com.auth.enums.LoginEnum;
import com.auth.impl.dao.LoginDAOImpl;

import io.vertx.core.MultiMap;

public class LoginBOImpl implements LoginBO {

	@Override
	public LoginEnum userAuthenticate(MultiMap params) {
		LoginDAO loginDAO = new LoginDAOImpl();
		String userName = params.get("user");
		boolean accLocked = loginDAO.checkAccountLocked(userName);
		if (accLocked) {
			return LoginEnum.ACCOUNT_LOCKED;
		}
		
		boolean userNotExists = loginDAO.checkUserExists(userName);
		if (userNotExists) {
			return LoginEnum.USER_NOT_EXISTS;
		}
		
		boolean passwordIncorrect = loginDAO.authPassword(params);
		if (passwordIncorrect) {
			return LoginEnum.USER_NOT_EXISTS;
		}
		return LoginEnum.AUTHENTICATED;
	}
}
