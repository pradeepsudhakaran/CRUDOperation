package com.auth.dao;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;

public interface EmployeeDAO {
	long checkEmployeeCount();
    int addEmployee(MultiMap params);
    JsonArray getEmployees();
    int updateEmployee(MultiMap params);
    int deleteEmployee(long employeeId);
}
