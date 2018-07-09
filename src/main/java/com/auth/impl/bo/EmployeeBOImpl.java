package com.auth.impl.bo;

import com.auth.bo.EmployeeBO;
import com.auth.dao.EmployeeDAO;
import com.auth.handler.EmployeeHandler;
import com.auth.impl.dao.EmployeeDAOImpl;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class EmployeeBOImpl implements EmployeeBO {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBOImpl.class);
	@Override
	public int addEmployee(MultiMap params) {
		int count = 0;
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		long totalNoOfEmployees = employeeDAO.checkEmployeeCount();
		logger.info("total employees:: "+totalNoOfEmployees);
		params.add("_id", String.valueOf(totalNoOfEmployees++));
		count = employeeDAO.addEmployee(params);
		logger.info("count::"+count);
		return count;
	}

	@Override
	public JsonArray getEmployees() {
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		JsonArray resultArray = employeeDAO.getEmployees();
		
		return resultArray;
	}
	
	@Override
	public int updateEmployee(MultiMap params) {
		int count = 0;
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		count = employeeDAO.updateEmployee(params);
		return count;
	}
	
	@Override
	public int deleteEmployee(long employeeId) {
		int count = 0;
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		count = employeeDAO.deleteEmployee(employeeId);
		return count;
	}
}
