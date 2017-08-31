package com.tdd.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmployManagerImplTest {

	@Test
	public void createEmployee() {
		EmployManagerImpl emp = new EmployManagerImpl();
		Integer empNo=emp.createEmployee();
		assertEquals(new Integer(123),empNo);
		
	}
}
