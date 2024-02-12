package com.test.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.entity.Store;
import com.test.demo.repository.StoreRepository;

@RestController
public class StoreController {
	
	@Autowired
	private StoreRepository repository;
	
	@GetMapping("stores")
	public ResponseEntity<List<Store>> getMethodName() {
		List<Store> response = repository.findAll();
		return ResponseEntity.ok(response);
	}

}
