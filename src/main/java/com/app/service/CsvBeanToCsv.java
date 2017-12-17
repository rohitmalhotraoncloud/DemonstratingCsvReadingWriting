package com.app.service;

import java.beans.IntrospectionException;

import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.MappingStrategy;

class CsvBeanToCsv<T> extends BeanToCsv<T> {
	@Override
	protected String[] processHeader(MappingStrategy<T> mapper) throws IntrospectionException {
		if (mapper instanceof CsvMappingStrategy) {
			return ((CsvMappingStrategy) mapper).getHeader();
		} else {
			return super.processHeader(mapper);
		}
	}
}
