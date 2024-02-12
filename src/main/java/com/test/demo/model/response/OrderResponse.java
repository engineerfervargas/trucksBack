package com.test.demo.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	private String uuid;
	private String store;
	private String date;
	private String user;
	private String client;
	private List<OrderDetail> trucks;
	private float subtotal;
	private float total;
	private String status;
	private String ip;
	
}
