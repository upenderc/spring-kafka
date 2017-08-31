package com.poc.spring.kafka;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class TestElvis {

	public static void main(String ...args) {
		Object[] arr =new String[]{"12805","_","GOLD","_","TIER"};
		System.out.println(String.format("%s%s%s%s%s",arr));
		Person p=new Person();
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setRootObject(p);
		System.out.println(parser.parseExpression("name?:'Upender'").getValue(context,String.class));
		System.out.println(parser.parseExpression("address?.line1").getValue(context,String.class));

	}
	static class Person {
		private String name;
		private Address address;
		public Person() {
			name="default";
			address=new Address();
		}
		public Person(String n){
			this.name=n;
		}
		public String getName() {
			return this.name;
		}
		public Address getAddress() {
			return address;
		}
		
	}
	static class Address
	{
		private String line1;
		public Address() {
			line1="default Address";
		}
		public String getLine1() {
			return line1;
		}
		
	}
}
