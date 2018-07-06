package com.auth.builder;

import com.auth.utils.ResponseUtil;

import io.vertx.core.http.HttpServerResponse;

public class ResponseBuilder {
	public static void writeInternalServerError(HttpServerResponse response) {

	    ResponseUtil.writeErrorObject(500, "Internal Server Error",
	        "Internal Server Error", response);

	  }
}
