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

import com.osc.json.ItemJson;
import com.osc.json.PageJson;
import com.osc.service.ItemService;
import com.osc.util.Util;

@RestController
@RequestMapping("ItemController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ItemController {
	private Logger LOG = Logger.getLogger(ItemController.class);
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody ItemJson itemJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
		try {
			itemJson.setCreatedBy(Util.getLoginUserId(request));
			itemJson.setUpdatedBy(Util.getLoginUserId(request));
			itemService.saveOrUpdate(itemJson);
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

	
	@RequestMapping(value="getAllItems",method=RequestMethod.POST)
	public ResponseEntity<List<ItemJson>> getAllItems(HttpServletRequest request,@RequestBody PageJson pageJson) {
		if (Util.getLoginUserId(request) != null) {
			List<ItemJson> itemJsons = null;
			try {
				itemJsons = itemService.getAllItems(pageJson);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<ItemJson>>(itemJsons, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<ItemJson>>(itemJsons, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value="findNoOfItems")
	public ResponseEntity<Long> findNoOfItems(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			Long noOfRecords = null;
			try {
				noOfRecords = itemService.findNoOfItems();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Long>(noOfRecords, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Long>(noOfRecords, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	
	@RequestMapping("getItemById/{id}")
	public ResponseEntity<ItemJson> getItemById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			ItemJson itemJson = null;
			try {
				itemJson = itemService.getItemById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<ItemJson>(itemJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<ItemJson>(itemJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteItemById/{id}")
	public ResponseEntity<ItemJson> deleteItemById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			ItemJson itemJson = null;
			try {
				itemService.deleteItemById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<ItemJson>(itemJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<ItemJson>(itemJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}


}
