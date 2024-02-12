package com.test.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Store;

@Repository
public interface StoreRepository  extends JpaRepository<Store, UUID>{
	
	@Query(value = "SELECT s.* FROM dbo.stores s WHERE uuid = :uuid",
			nativeQuery = true)
	public Optional<Store> findByUuid(@Param("uuid")String uuid);

}
