package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sku {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String vendor;

	@Column(name="\"index\"")
	private Long index;

	public Sku() {
		// TODO Auto-generated constructor stub
	}

	public Sku(String vendor, Long index) {
		this.vendor = vendor;
		this.index = index;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
}
