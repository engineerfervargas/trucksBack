package com.test.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
	
	@Query(value = "SELECT * FROM dbo.clients WHERE uuid = :uuid",
			nativeQuery = true)
	public Optional<Client> findByUuid(@Param("uuid") String uuid);

}
