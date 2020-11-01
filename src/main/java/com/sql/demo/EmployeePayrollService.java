package com.sql.demo;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.*;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayrollData> empList;

	public EmployeePayrollService() {
	}

	public EmployeePayrollService(List<EmployeePayrollData> empList) {
		this.empList = empList;
	}

	private void readEmployeePayrollData(Scanner s) {
		System.out.println("Enter ID");
		int id = s.nextInt();
		System.out.println("Enter Name");
		String name = s.next();
		System.out.println("Enter salary");
		Double salary = s.nextDouble();
		empList.add(new EmployeePayrollData(id, name, salary));
	}

	void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Payroll to Console\n" + empList);
		else if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(empList);

	}

	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> empList = new ArrayList<>();
		EmployeePayrollService empService = new EmployeePayrollService(empList);
		Scanner consoleInputReader = new Scanner(System.in);
		empService.readEmployeePayrollData(consoleInputReader);
		empService.writeEmployeePayrollData(IOService.CONSOLE_IO);

	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			return new EmployeePayrollFileIOService().countEntries();
		return 0;
	}

	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().printData();
	}


	public List<EmployeePayrollData> readEmployeePayrollData(IOService dbIo) {
		if (dbIo.equals(IOService.DB_IO)) {
			this.empList = new EmployeePayrollDBService().readData();
		}
		return this.empList;
	}

	public void updateEmployeeSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmployeeData(name, salary);
		if (result == 0)
			return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.basic_pay=salary;
	}
	private EmployeePayrollData getEmployeePayrollData(String name) {
		for (EmployeePayrollData data : empList) {
			if (data.name.equals(name)) {
				return data;
			}
		}
		return null;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		for (EmployeePayrollData data : empList) {
			if (data.name.equals(name)) {
				if (Double.compare(data.basic_pay, 3000000.00) == 0) {
					return true;
				}
			}
		}
		return false;
	}



}