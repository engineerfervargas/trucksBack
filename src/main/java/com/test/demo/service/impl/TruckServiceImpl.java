package com.test.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.demo.entity.Truck;
import com.test.demo.exception.DataNotFoundException;
import com.test.demo.repository.TruckRepository;
import com.test.demo.service.TruckService;

@Service
public class TruckServiceImpl implements TruckService {

	@Autowired
	private TruckRepository repository;
	
	@Override
	public Truck find(String hawa) throws DataNotFoundException {
		return repository.findById(hawa)
				.orElseThrow(() -> new DataNotFoundException("truck not found: ", hawa));
	}

	@Override
	public List<Truck> all() {
		return repository.findAll();
	}

}
