package com.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.SkuService;
import com.app.util.VendorUtil;

@Controller
public class SkuController {

	@Autowired
	SkuService skuService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String productsList(Model model) {
		model.addAttribute("vendors", VendorUtil.getVendors());
		return "skuentryform";
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void getFile(@PathVariable("id") String fileName, HttpServletResponse response) {
		try {
			File file = skuService.getFile(fileName);
			InputStream is = new FileInputStream(file);
			response.setContentType("text/csv");
			response.setHeader("Content-disposition", "attachment;filename=output.csv");

			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			file.delete();
			response.flushBuffer();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}
	}

}