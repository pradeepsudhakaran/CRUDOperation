package com.auth.dao;

import io.vertx.core.MultiMap;

public interface LoginDAO {
	boolean checkAccountLocked(String userName);
	boolean checkUserExists(String userName);
	boolean authPassword(MultiMap params);
	int getRetriesCount(String userName);
	int updateRetriesCount(String userName, int count);
}
