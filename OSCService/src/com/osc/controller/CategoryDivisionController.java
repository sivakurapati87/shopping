package com.osc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osc.json.CategoryDivisionJson;
import com.osc.service.CategoryDivisionService;
import com.osc.util.Util;

@RestController
@RequestMapping("CategoryDivisionController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CategoryDivisionController {
	private Logger LOG = Logger.getLogger(CategoryDivisionController.class);
	@Autowired
	private CategoryDivisionService categoryDivisionService;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody CategoryDivisionJson categoryDivisionJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			try {
				categoryDivisionJson.setCreatedBy(Util.getLoginUserId(request));
				categoryDivisionJson.setUpdatedBy(Util.getLoginUserId(request));
				categoryDivisionService.saveOrUpdate(categoryDivisionJson);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("getAllCategoryDivisions")
	public ResponseEntity<List<CategoryDivisionJson>> getAllCategoryDivisions(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<CategoryDivisionJson> categoryDivisionJsons = null;
			try {
				categoryDivisionJsons = categoryDivisionService.getAllCategoryDivisions();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<CategoryDivisionJson>>(categoryDivisionJsons, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<CategoryDivisionJson>>(categoryDivisionJsons, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("getCategoryDivisionById/{id}")
	public ResponseEntity<CategoryDivisionJson> getCategoryDivisionById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			CategoryDivisionJson categoryDivisionJson = null;
			try {
				categoryDivisionJson = categoryDivisionService.getCategoryDivisionById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CategoryDivisionJson>(categoryDivisionJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CategoryDivisionJson>(categoryDivisionJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteCategoryDivisionById/{id}")
	public ResponseEntity<CategoryDivisionJson> deleteCategoryDivisionById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			CategoryDivisionJson categoryDivisionJson = null;
			try {
				categoryDivisionService.deleteCategoryDivisionById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CategoryDivisionJson>(categoryDivisionJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CategoryDivisionJson>(categoryDivisionJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

}
