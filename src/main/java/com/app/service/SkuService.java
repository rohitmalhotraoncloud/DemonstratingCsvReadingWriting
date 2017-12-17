package com.app.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.app.dto.SkuDto;
import com.app.dto.SkuOutputDto;
import com.app.model.Sku;
import com.app.repository.SkuRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

@Service
@Transactional
public class SkuService {
	@Autowired
	SkuRepository skuRepository;

	private static int FEW_RECORDS = 12;
	private static int ALL_RECORDS = 204;
	private static String SOURCE_TEMPLATE = "sourcetemplate.csv";

	@Value("${csv.file.systempath:/opt}")
	private String csvSystemPath;

	public String processModel(MultipartFile skuFile, SkuDto skumodel) {
		if (StringUtils.isBlank(skumodel.getSkustr())) {
			CSVReader reader;
			try {
				reader = new CSVReader(new InputStreamReader(skuFile.getInputStream()));
				List<String[]> data = reader.readAll();
				for (String[] str : data) {
					skumodel.addSkus(Arrays.asList(str));
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			skumodel.setSkus(Arrays.asList(skumodel.getSkustr().split(",")));
		}

		return createAndSaveOutput(skumodel);
	}

	private String createAndSaveOutput(SkuDto skumodel) {

		if (skumodel.getSkus() == null) {
			throw new RuntimeException("Sorry! No Sku Found");
		}

		List<SkuOutputDto> skuOutputDtoList = new ArrayList<SkuOutputDto>();
		CsvMappingStrategy<SkuOutputDto> strategy = new CsvMappingStrategy<SkuOutputDto>();
		strategy.setType(SkuOutputDto.class);
		strategy.setColumnMapping(getColumnNames());
		File file = null;
		CSVWriter writer = null;

		CsvBeanToCsv<SkuOutputDto> bc = new CsvBeanToCsv<SkuOutputDto>();
		String filenname = null;
		try {
			filenname = UUID.randomUUID().toString();
			int maxrecords = skumodel.getSkutypes().length == 1 ? FEW_RECORDS : ALL_RECORDS;
			file = new File(csvSystemPath + "/" + filenname + ".csv");

			List<SkuOutputDto> template = loadTemplate();

			skumodel.getSkus().removeAll(Collections.singleton(StringUtils.EMPTY));
			skumodel.getSkus().stream().forEach(e -> {
				int skuUniqueIndex = updateVendorIndex(skumodel.getVendor());
				int recordcount = 0;
				for (SkuOutputDto item : template) {
					recordcount++;
					skuOutputDtoList.add(item.getFormattedData(skuUniqueIndex, e, skumodel.getVendor()));
					if (recordcount >= maxrecords) {
						break;
					}
				}
			});

			writer = new CSVWriter(new FileWriter(file), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
			if (writer != null) {
				bc.write(strategy, writer, skuOutputDtoList);
			}
		} catch (IOException e) {

		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (Exception ex) {

			}
		}
		return filenname;
	}

	private List<SkuOutputDto> loadTemplate() {
		List<SkuOutputDto> skuOutputDtoList = null;
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(SOURCE_TEMPLATE)),
					CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, 1);
			ColumnPositionMappingStrategy<SkuOutputDto> mappingStrategy = new ColumnPositionMappingStrategy<SkuOutputDto>();
			mappingStrategy.setType(SkuOutputDto.class);
			mappingStrategy.setColumnMapping(getColumnNames().values().toArray(new String[0]));
			CsvToBean<SkuOutputDto> ctb = new CsvToBean<SkuOutputDto>();
			skuOutputDtoList = ctb.parse(mappingStrategy, reader);
		} catch (Exception ex) {

		} finally {
			try {
				reader.close();
			} catch (Exception ex) {

			}
		}
		return skuOutputDtoList;
	}

	public File getFile(String filename) {
		return new File(csvSystemPath + "/" + filename + ".csv");
	}

	private Map<String, String> getColumnNames() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Type", "type");
		map.put("BundleitemSKU", "bundleitemsku");
		map.put("Short", "shortname");
		map.put("Long", "longname");
		map.put("Description", "description");
		map.put("Vendor", "vendor");
		map.put("ID", "id");
		return map;
	}

	// Repository Function
	private int updateVendorIndex(String vendor) {
		Sku entity = skuRepository.findByVendor(vendor);
		if (entity != null) {
			entity.setIndex(entity.getIndex().longValue() + 1);
		} else {
			entity = new Sku(vendor, 1l);
		}
		skuRepository.save(entity);
		return entity.getIndex().intValue();
	}

}
