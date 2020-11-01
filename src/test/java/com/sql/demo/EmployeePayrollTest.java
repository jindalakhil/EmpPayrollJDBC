package com.sql.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.sql.demo.EmployeePayrollService.IOService;


public class EmployeePayrollTest {
	@Test
	public void givenDatabase_ShouldReturn_SizeOfEmplList() {
		EmployeePayrollService service = new EmployeePayrollService();
		List<EmployeePayrollData> empList = service.readEmployeePayrollData(IOService.DB_IO);
		System.out.println(empList);
		Assert.assertEquals(6, empList.size());
	}
	
	@Test
	public void givenUpdatedSalaryForEmployee_WhenUpdated_ShouldMatchWithDB() {
		EmployeePayrollService service = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData = service.readEmployeePayrollData(IOService.DB_IO);
		service.updateEmployeeSalary("Terisa", 3000000.00);
		boolean result = service.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}
}