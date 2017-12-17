package com.app.dto;

import org.apache.commons.lang3.StringUtils;

import com.app.util.CommonUtil;

public class SkuOutputDto {

	private String type;
	private String bundleitemsku;
	private String shortname;
	private String longname;
	private String description;
	private String vendor;
	private String id;

	public SkuOutputDto() {
	}

	public SkuOutputDto(String type, String bundleitemsku, String shortname, String longname, String description,
			String vendor, String id) {
		this.type = type;
		this.bundleitemsku = bundleitemsku;
		this.shortname = shortname;
		this.longname = longname;
		this.description = description;
		this.vendor = vendor;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBundleitemsku() {
		return bundleitemsku;
	}

	public void setBundleitemsku(String bundleitemsku) {
		this.bundleitemsku = bundleitemsku;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Fields To Replace
	private int indexreplacement;
	private String skureplacement;
	private String vendorreplacement;

	public void setIndexreplacement(int indexreplacement) {
		this.indexreplacement = indexreplacement;
	}

	public void setSkureplacement(String skureplacement) {
		this.skureplacement = skureplacement;
	}

	public void setVendorreplacement(String vendorreplacement) {
		this.vendorreplacement = vendorreplacement;
	}

	public SkuOutputDto getFormattedData(int index, String sku, String vendor) {
		SkuOutputDto record = new SkuOutputDto();
		this.setSkureplacement(CommonUtil.replaceSpecialChars(sku));
		this.setVendorreplacement(vendor);
		this.setIndexreplacement(index);

		record.setBundleitemsku(formatRecord(this.getBundleitemsku()));
		record.setVendor(formatRecord(this.getVendor()));
		record.setDescription(formatRecord(this.getDescription()));
		record.setId(formatRecord(this.getId()));
		record.setLongname(formatRecord(this.getLongname()));
		record.setShortname(formatRecord(this.getShortname()));
		record.setType(formatRecord(this.getType()));
		record.setId(String.valueOf(indexreplacement));
		return record;
	}

	private String formatRecord(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		return value.replace("@sku", skureplacement).replace("@ph1", CommonUtil.increaseDig(indexreplacement, 4))
				.replace("@ph2", CommonUtil.increaseDig(indexreplacement, 3)).replace("@vendor", vendorreplacement);
	}

}
