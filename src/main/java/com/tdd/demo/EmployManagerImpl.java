package com.tdd.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployManagerImpl {

	public Integer createEmployee() {
		return new Integer(123);
		
	}

	
	public static void main(String ...args) {
		List<String> arry = Arrays.asList("a","b","c","a","b");
		System.out.println(arry.stream().filter(s -> "a".equals(s)).collect(Collectors.toList()));
		List<Employee> emps=Arrays.asList(new Employee("A","x"),new Employee("B","y"), new Employee("C","x"));
		Map<String,List<Employee>> grp=emps.stream().collect(Collectors.groupingBy(Employee::getDepartment));
		System.out.println(grp.get("x"));
	}
	
	
}
class Employee
{
	
	private String name;
	private String department;
	public Employee(String n,String d) {
		this.name=n;
		this.department=d;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", department=" + department + "]";
	}
	
}