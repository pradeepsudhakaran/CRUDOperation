package com.auth.bo;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;

public interface EmployeeBO {
	int addEmployee(MultiMap params);
	JsonArray getEmployees();
	int updateEmployee(MultiMap params);
	int deleteEmployee(long employeeId);
}
