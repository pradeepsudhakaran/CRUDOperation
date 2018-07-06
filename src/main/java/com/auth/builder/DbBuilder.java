package com.auth.builder;

import java.util.Arrays;

import com.auth.constants.ConnectionConstants;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class DbBuilder {
	
	private static String connectionUrl;
	private static int connectionPort;
	private static String dataBaseName;
	private static String userName;
	private static String password;
	
	@SuppressWarnings("deprecation")
	public static MongoClient initializeDb() {
		getContext(Vertx.currentContext().config());
		MongoCredential credential = MongoCredential.createCredential(userName,
				dataBaseName,
                password.toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(connectionUrl, connectionPort),
        Arrays.asList(credential));
		return mongoClient;
	}
	
	public static MongoDatabase getDB() {
		MongoDatabase database = DbBuilder.initializeDb().getDatabase(dataBaseName);
		return database;
	}
	
	public static void getContext(JsonObject contextConfig) {
		connectionUrl = contextConfig.getString(ConnectionConstants.CONNECTION_URL);
		connectionPort = contextConfig.getInteger(ConnectionConstants.CONNECTION_PORT);
		dataBaseName = contextConfig.getString(ConnectionConstants.DATABASE_NAME);
		userName = contextConfig.getString(ConnectionConstants.USER);
		password = contextConfig.getString(ConnectionConstants.PASSWORD);
	}
	
	public static void clientClose(MongoClient mongoClient) {
		mongoClient.close();
	}
}
