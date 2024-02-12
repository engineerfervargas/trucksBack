package com.test.demo.service;

import java.util.List;
import java.util.UUID;

import com.test.demo.entity.Truck;
import com.test.demo.exception.DataNotFoundException;

public interface TruckService {
	
	public Truck find(String hawa) throws DataNotFoundException;

	public List<Truck> all();
	

}
