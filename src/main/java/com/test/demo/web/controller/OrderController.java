package com.test.demo.web.controller;

import java.text.ParseException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.model.request.OrderRequest;
import com.test.demo.model.response.OrderResponse;
import com.test.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService service;

	@PostMapping("/make")
	public ResponseEntity<OrderResponse> make(@RequestBody OrderRequest request) throws ParseException {
		OrderResponse response = service.make(request);
		if(response != null)
			return ResponseEntity.ok(response);
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<OrderResponse>> all() {
		List<OrderResponse> orders = service.orders();
		return ResponseEntity.ok(orders);
	}
	
	@PatchMapping("/update/{uuid}")
	public ResponseEntity updateStatus(@PathVariable("uuid")String uuid, @PathParam("status") int status) {
		service.updateStatus(uuid, status);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
