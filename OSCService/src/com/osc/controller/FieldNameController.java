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

import com.osc.json.ItemFieldNameJson;
import com.osc.service.ItemFieldNameService;
import com.osc.util.Util;

@RestController
@RequestMapping("FieldNameController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FieldNameController {
	private Logger LOG = Logger.getLogger(FieldNameController.class);
	@Autowired
	private ItemFieldNameService itemFieldNamesService;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody ItemFieldNameJson itemFieldNamesJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
		try {
			itemFieldNamesJson.setCreatedBy(Util.getLoginUserId(request));
			itemFieldNamesJson.setUpdatedBy(Util.getLoginUserId(request));
			itemFieldNamesService.saveOrUpdate(itemFieldNamesJson);
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

	
	@RequestMapping("getAllItemFieldNames")
	public ResponseEntity<List<ItemFieldNameJson>> getAllItemFieldNames(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<ItemFieldNameJson> itemFieldNamesJsons = null;
			try {
				itemFieldNamesJsons = itemFieldNamesService.getAllItemFieldNames();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<ItemFieldNameJson>>(itemFieldNamesJsons, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<ItemFieldNameJson>>(itemFieldNamesJsons, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("getFieldNameById/{id}")
	public ResponseEntity<ItemFieldNameJson> getFieldNameById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			ItemFieldNameJson itemFieldNamesJson = null;
			try {
				itemFieldNamesJson = itemFieldNamesService.getFieldNameById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<ItemFieldNameJson>(itemFieldNamesJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<ItemFieldNameJson>(itemFieldNamesJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deleteFieldNameById/{id}")
	public ResponseEntity<ItemFieldNameJson> deleteFieldNameById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			ItemFieldNameJson itemFieldNamesJson = null;
			try {
				itemFieldNamesService.deleteFieldNameById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<ItemFieldNameJson>(itemFieldNamesJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<ItemFieldNameJson>(itemFieldNamesJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}


}
