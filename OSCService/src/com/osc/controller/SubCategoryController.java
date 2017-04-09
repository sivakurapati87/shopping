package com.osc.controller;

import java.util.List;
import java.util.Map;

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

import com.osc.json.CategoryJson;
import com.osc.json.SubCategoryJson;
import com.osc.service.SubCategoryService;
import com.osc.util.Util;

@RestController
@RequestMapping("SubCategoryController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SubCategoryController {
	private Logger LOG = Logger.getLogger(SubCategoryController.class);
	@Autowired
	private SubCategoryService subCategoryService;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody SubCategoryJson subCategoryJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			try {
				subCategoryJson.setCreatedBy(Util.getLoginUserId(request));
				subCategoryJson.setUpdatedBy(Util.getLoginUserId(request));
				subCategoryService.saveOrUpdate(subCategoryJson);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Object>(HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
	}


	@RequestMapping("getAllSubCategories")
	public ResponseEntity<List<SubCategoryJson>> getAllSubCategories(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<SubCategoryJson> subCategoryJsons = null;
			try {
				subCategoryJsons = subCategoryService.getAllSubCategories();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<SubCategoryJson>>(subCategoryJsons, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<SubCategoryJson>>(subCategoryJsons, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("getAllSubCategoriesWithCategory")
	public ResponseEntity<Map<String,List<SubCategoryJson>>> getAllSubCategoriesWithCategory(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			Map<String,List<SubCategoryJson>> subCategoryJsonsMap = null;
			try {
				subCategoryJsonsMap = subCategoryService.getAllSubCategoriesWithCategory();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Map<String,List<SubCategoryJson>>>(subCategoryJsonsMap, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Map<String,List<SubCategoryJson>>>(subCategoryJsonsMap, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("getSubCategoryById/{id}")
	public ResponseEntity<SubCategoryJson> getSubCategoryById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			SubCategoryJson subCategoryJson = null;
			try {
				subCategoryJson = subCategoryService.getSubCategoryById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<SubCategoryJson>(subCategoryJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<SubCategoryJson>(subCategoryJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteSubCategoryById/{id}")
	public ResponseEntity<CategoryJson> deleteSubCategoryById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			CategoryJson categoryJson = null;
			try {
				subCategoryService.deleteSubCategoryById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<CategoryJson>(categoryJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

}
