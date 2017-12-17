package com.app.restcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.SkuDto;
import com.app.service.SkuService;

@RestController
@RequestMapping("/api")
public class SkuRestController {

	@Autowired
	SkuService skuService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String create(@Valid SkuDto skumodel, @RequestParam("files") MultipartFile[] uploadfiles) {
		return skuService.processModel(uploadfiles[0], skumodel);
	}

}
