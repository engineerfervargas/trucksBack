package com.test.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataNotFoundException extends Exception{
	
	private String message;
	private String uuid;

}
