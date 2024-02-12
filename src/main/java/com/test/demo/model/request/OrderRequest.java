package com.test.demo.model.request;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class OrderRequest {

	private String store;
	private List<Map<String, Integer>> hawas;
	private String client;
	private String ip;
	
}
