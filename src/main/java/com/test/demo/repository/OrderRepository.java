package com.test.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	
	@Query(value = "SELECT * FROM dbo.orders WHERE uuid = :uuid",
			nativeQuery = true)
	public Optional<Order> findByUuid(@Param("uuid")String uuid);
	
}
