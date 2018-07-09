package com.auth.impl.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.auth.builder.DbBuilder;
import com.auth.dao.LoginDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

public class LoginDAOImpl implements LoginDAO {

	@Override
	public boolean checkAccountLocked(String userName) {
		boolean result = false;
		JsonObject loginObject = null;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Login");
		MongoCursor<Document> cursor = collection.find(eq("user", userName)).iterator();
		while(cursor.hasNext()) {
			loginObject = new JsonObject(cursor.next().toJson());
		}
		result = loginObject.getBoolean("isAccountLocked");
		cursor.close();
		DbBuilder.clientClose(mongoClient);
		return result;
	}
	
	@Override
	public boolean checkUserExists(String userName){
		boolean result = false;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Login");
		MongoCursor<Document> cursor = collection.find(eq("user", userName)).iterator();
		
		if (cursor.hasNext()) {
			result = true;
		}
		cursor.close();
		DbBuilder.clientClose(mongoClient);
		return result;
	}
	
	@Override
	public boolean authPassword(MultiMap params) {
		boolean result = false;
		JsonObject loginObject = null;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Login");
		MongoCursor<Document> cursor = collection.find(eq("user", params.get("user"))).iterator();
		while(cursor.hasNext()) {
			loginObject = new JsonObject(cursor.next().toJson());
		}
		
		if (params.get("password").equals(loginObject.getString("password"))) {
			result = true;
		}
		cursor.close();
		DbBuilder.clientClose(mongoClient);
		return result;
	}
	
	@Override
	public int getRetriesCount(String userName) {
		JsonObject loginObject = null;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Login");
		MongoCursor<Document> cursor = collection.find(eq("user", userName)).iterator();
		while(cursor.hasNext()) {
			loginObject = new JsonObject(cursor.next().toJson());
		}
		
		int count = 0;
		count = loginObject.getInteger("retry_count");
		cursor.close();
		DbBuilder.clientClose(mongoClient);
		return count;
	}
	
	@Override
	public int updateRetriesCount(String userName, int count) {
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Login");
		UpdateResult updateResult = collection.updateOne(eq("user", userName),
				new Document("$set", new Document("retry_count", count)));
		DbBuilder.clientClose(mongoClient);
		return (int) updateResult.getModifiedCount();
	}
}
