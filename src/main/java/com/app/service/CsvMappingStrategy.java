package com.app.service;

import java.util.Map;

import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CsvMappingStrategy<T> extends HeaderColumnNameTranslateMappingStrategy<T> {

	@Override
	public void setColumnMapping(Map<String, String> columnMapping) {
		super.setColumnMapping(columnMapping);
		header = new String[columnMapping.size()];
		int i = 0;
		for (Map.Entry entry : columnMapping.entrySet()) {
			header[i] = entry.getKey().toString();
			i++;
		}
	}

	public String[] getHeader() {
		return header;
	}

}
