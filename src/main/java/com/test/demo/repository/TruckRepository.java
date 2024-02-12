package com.test.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Truck;

@Repository
public interface TruckRepository extends JpaRepository<Truck, String> {

	@Query(value = "SELECT * FROM dbo.trucks WHERE hawa IN :uuid", 
			nativeQuery = true)
	public List<Truck> findByHawaIn(@Param("uuid")List<String> ids);

}
