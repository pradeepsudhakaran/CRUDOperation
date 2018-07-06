package com.auth.impl.dao;

import org.bson.Document;

import com.auth.builder.DbBuilder;
import com.auth.dao.EmployeeDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.vertx.core.MultiMap;

public class EmployeeDAOImpl implements EmployeeDAO {
	@Override
	public int addEmployee(MultiMap params) {
		int count = 0;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		Document document = new Document("name", params.get("name"))
				.append("address", params.get("address"));
		collection.insertOne(document);
		count++;
		DbBuilder.clientClose(mongoClient);
		return count;
	}
}
