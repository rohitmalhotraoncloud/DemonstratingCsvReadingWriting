package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Sku;

public interface SkuRepository extends JpaRepository<Sku, Long> {
	
	@Query("Select s from Sku s where s.vendor=?1")
	public Sku findByVendor(String vendor);

}
