package com.test.demo.service;

import java.text.ParseException;
import java.util.List;

import com.test.demo.model.request.OrderRequest;
import com.test.demo.model.response.OrderResponse;

public interface OrderService {
	
	public OrderResponse make(OrderRequest request) throws ParseException;
	
	public List<OrderResponse> orders();
	
	public void updateStatus(String uuid, int status);

}
