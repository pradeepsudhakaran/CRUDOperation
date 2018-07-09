package com.auth.impl.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.auth.builder.DbBuilder;
import com.auth.dao.EmployeeDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;

public class EmployeeDAOImpl implements EmployeeDAO {
	@Override
	public long checkEmployeeCount() {
		long count = 0;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		count = collection.count();
		DbBuilder.clientClose(mongoClient);
		return count;
	}
	
	@Override
	public int addEmployee(MultiMap params) {
		int count = 0;
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		Document document = new Document("_id", params.get("_id"))
				.append("name", params.get("name"))
				.append("address", params.get("address"));
		collection.insertOne(document);
		count++;
		document.clear();
		DbBuilder.clientClose(mongoClient);
		return count;
	}
	
	@Override
	public JsonArray getEmployees() {
		JsonArray resultArray = new JsonArray();
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
			while (cursor.hasNext()) {
				resultArray.add(cursor.next().toJson());
			}
		} finally {
			cursor.close();
			DbBuilder.clientClose(mongoClient);
		}
		return resultArray;
	}
	
	@Override
	public int updateEmployee(MultiMap params) {
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		UpdateResult updateResult = collection.updateOne(eq("_id", params.get("_id")),
				new Document("$set", new Document("name", params.get("name"))
						.append("address", params.get("address"))));
		DbBuilder.clientClose(mongoClient);
		return (int) updateResult.getModifiedCount();
	}
	
	@Override
	public int deleteEmployee(long employeeId) {
		MongoClient mongoClient = DbBuilder.initializeDb();
		MongoDatabase database = DbBuilder.getDB(mongoClient);
		MongoCollection<Document> collection = 
				database.getCollection("Employee");
		DeleteResult result = collection.deleteOne(new Document("_id", employeeId));
		DbBuilder.clientClose(mongoClient);
		return (int) result.getDeletedCount();
	}
}
