package com.auth.impl.bo;

import com.auth.bo.EmployeeBO;
import com.auth.dao.EmployeeDAO;
import com.auth.impl.dao.EmployeeDAOImpl;

import io.vertx.core.MultiMap;

public class EmployeeBOImpl implements EmployeeBO {
	@Override
	public int addEmployee(MultiMap params) {
		int count = 0;
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		count = employeeDAO.addEmployee(params);
		return count;
	}

}
