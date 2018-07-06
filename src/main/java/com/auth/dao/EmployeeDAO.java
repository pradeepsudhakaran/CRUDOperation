package com.auth.dao;

import io.vertx.core.MultiMap;

public interface EmployeeDAO {
    int addEmployee(MultiMap params);
}
