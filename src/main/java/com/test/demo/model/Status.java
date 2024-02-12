package com.test.demo.model;

public enum Status  {
	PENDING(0), COMPLETED(1), CANCELED(2);

	private int value;
	
	Status(int value) {
		this.value = value;
	} 
	
	public int getValue() {
		return value;
	}
	
}
