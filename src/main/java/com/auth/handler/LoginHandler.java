package com.auth.handler;

import org.apache.commons.lang3.StringUtils;

import com.auth.bo.LoginBO;
import com.auth.builder.ResponseBuilder;
import com.auth.enums.LoginEnum;
import com.auth.impl.bo.LoginBOImpl;
import com.auth.utils.ResponseUtil;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.web.RoutingContext;

public class LoginHandler {
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	public static void userAuth(RoutingContext routingContext) {
		HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        MultiMap params = request.params();
        
        String userName = params.get("user");
        String password = params.get("password");
        
        if (StringUtils.isEmpty(userName)) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        } else if (StringUtils.isEmpty(password)) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        }
        
        LoginBO loginBO = new LoginBOImpl();
        LoginEnum loginEnum = loginBO.userAuthenticate(params);
        
        LoginHandler.writeResponse(loginEnum, routingContext, params, response);
        
	}
	
	public static void writeResponse(LoginEnum loginEnum,RoutingContext routingContext,
			MultiMap params,
			HttpServerResponse response) {
		switch(loginEnum){
		case ACCOUNT_LOCKED:
			logger.error("Account locked");
        	ResponseUtil.writeErrorObject(403, "Account locked",
        			"Account locked", response);
		case AUTHENTICATED:
			logger.info("user authenticated");
			LoginHandler.generateToken(routingContext, params);
		case USER_NOT_EXISTS:
			logger.error("User does not exists");
        	ResponseUtil.writeErrorObject(403, "User does not exists",
        			"User does not exists", response);
		case INCORRECT_PASSWORD:
			logger.error("Incorrect password");
        	ResponseUtil.writeErrorObject(403, "Incorrect password",
        			"Incorrect password", response);
		default:
			ResponseBuilder.writeInternalServerError(response);
		}
	}
	
	public static void generateToken(RoutingContext routingContext, MultiMap params) {
		JWTAuthOptions config = new JWTAuthOptions()
				  .setKeyStore(new KeyStoreOptions()
				    .setPath("keystore.jceks")
				    .setPassword("secret"));
		JWTAuth provider = JWTAuth.create(routingContext.vertx(), config);
		
		String token = provider.generateToken(new JsonObject().put("user", params.get("user")),
				new JWTOptions().setAlgorithm("HS256")
				.setExpiresInMinutes(1*24*60*60));
		JsonObject response = new JsonObject();
		response.put("token", token);
		routingContext.session().put("token", token);
		routingContext.response().setStatusCode(200)
		.putHeader("Content-Type", "application/json; charset=utf-8")
		.end(response.toString());
	}
}
