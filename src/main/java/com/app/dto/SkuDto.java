package com.app.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang3.StringUtils;

public class SkuDto {

	private String skustr;
	private Integer startingid;
	private String vendor;
	private String[] skutypes;
	private List<String> skus;

	public String getSkustr() {
		return skustr;
	}

	public void setSkustr(String skustr) {
		this.skustr = skustr;
	}

	public Integer getStartingid() {
		return startingid;
	}

	public void setStartingid(Integer startingid) {
		this.startingid = startingid;
	}

	public List<String> getSkus() {
		return skus;
	}

	public void setSkus(List<String> skus) {
		this.skus = skus;
	}

	public void addSkus(List<String> skus) {
		if (this.skus == null) {
			this.skus = new ArrayList<String>();
		}
		
		this.skus.addAll(skus);
	}

	public String[] getSkutypes() {
		return skutypes;
	}

	public void setSkutypes(String[] skutypes) {
		this.skutypes = skutypes;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

}
