package com.test.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.entity.Client;
import com.test.demo.repository.ClientRepository;

@RestController
public class ClientController {
	
	@Autowired
	private ClientRepository repository;
	
	@GetMapping("clients")
	public ResponseEntity<List<Client>> getMethodName() {
		List<Client> response = repository.findAll();
		return ResponseEntity.ok(response);
	}

}
