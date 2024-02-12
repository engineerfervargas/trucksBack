package com.test.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
	
	private String hawa;
	private String description;
	private int quantity;
	private float price;
	private float discount;
	private float subtotal;
	private float total;
	
}
