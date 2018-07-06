package com.auth.bo;

import io.vertx.core.MultiMap;

public interface EmployeeBO {
	int addEmployee(MultiMap params);
}
