package com.auth.bo;

import com.auth.enums.LoginEnum;

import io.vertx.core.MultiMap;

public interface LoginBO {
	LoginEnum userAuthenticate(MultiMap params);
}
