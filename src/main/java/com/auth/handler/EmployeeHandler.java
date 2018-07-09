package com.auth.handler;

import org.apache.commons.lang3.StringUtils;

import com.auth.bo.EmployeeBO;
import com.auth.builder.ResponseBuilder;
import com.auth.impl.bo.EmployeeBOImpl;
import com.auth.utils.ResponseUtil;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

public class EmployeeHandler {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeHandler.class);
    public static void addEmployee(RoutingContext routingContext) {
    	HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        MultiMap params = request.params();
        
        String name = params.get("name");
        String address = params.get("address");
        
        if (StringUtils.isEmpty(name)) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        } else if (StringUtils.isEmpty(address)) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        }
        
        EmployeeBO employeeBO = new EmployeeBOImpl();
        int count = employeeBO.addEmployee(params);
        
        if (count > 0) {
        	logger.info("Employee posted");
        	JsonObject responseObject = new JsonObject();
        	responseObject.put("message", "Employee Posted");
        	ResponseUtil.writeSuccessObject(201, responseObject, response);
        } else {
        	logger.error("Problem with server. Employee insert count 0");
        	ResponseBuilder.writeInternalServerError(response);
        }
    }
    
    public static void getEmployees(RoutingContext routingContext) {
    	HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        MultiMap params = request.params();
        
        EmployeeBO employeeBO = new EmployeeBOImpl();
        JsonArray resultArray = employeeBO.getEmployees();
        logger.info("Total Count :: "+resultArray.size());
        ResponseUtil.writeSuccessObject(200, resultArray, response);
    }
    
    public static void updateEmployee(RoutingContext routingContext) {
    	HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        MultiMap params = request.params();
        
        long employeeId = (StringUtils.isEmpty(params.get("_id"))) ? Long.valueOf(params.get("_id")) : 0;
        if (employeeId == 0) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        }
        
        EmployeeBO employeeBO = new EmployeeBOImpl();
        int count = employeeBO.updateEmployee(params);
        logger.info("Update Count :: "+count);
        JsonObject responseObject = new JsonObject();
    	responseObject.put("message", "Employee Updated");
        ResponseUtil.writeSuccessObject(200, responseObject, response);
    }
    
    public static void deleteEmployee(RoutingContext routingContext) {
    	HttpServerRequest request = routingContext.request();
        HttpServerResponse response = routingContext.response();
        MultiMap params = request.params();
        
        long employeeId = (StringUtils.isEmpty(params.get("_id"))) ? Long.valueOf(params.get("_id")) : 0;
        if (employeeId == 0) {
        	logger.error("required params missing");
        	ResponseUtil.writeErrorObject(400, "Required params missing",
        			"Required params missing", response);
        	return;
        }
        
        EmployeeBO employeeBO = new EmployeeBOImpl();
        int count = employeeBO.updateEmployee(params);
        logger.info("Delete Count :: "+count);
        JsonObject responseObject = new JsonObject();
    	responseObject.put("message", "Employee Deleted");
        ResponseUtil.writeSuccessObject(200, responseObject, response);
    }
} 
