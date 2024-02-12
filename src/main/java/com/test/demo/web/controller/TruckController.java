package com.test.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.entity.Truck;
import com.test.demo.exception.DataNotFoundException;
import com.test.demo.service.TruckService;


@RestController
@RequestMapping("/truck")
public class TruckController {
	
	@Autowired
	private TruckService service;
	
	@GetMapping("/{hawa}")
	public ResponseEntity<Truck> getMethodName(@PathVariable("hawa") final String hawa) throws DataNotFoundException {
		return ResponseEntity.ok(service.find(hawa));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Truck>> all(){
		List<Truck> trucks = service.all();
		return ResponseEntity.ok(trucks);
	}

}
