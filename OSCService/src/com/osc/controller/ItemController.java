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
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "getAllItems", method = RequestMethod.POST)
	public ResponseEntity<List<ItemJson>> getAllItems(HttpServletRequest request, @RequestBody PageJson pageJson) {
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

	@RequestMapping(value = "findNoOfItems", method = RequestMethod.POST)
	public ResponseEntity<Long> findNoOfItems(HttpServletRequest request, @RequestBody PageJson pageJson) {
		if (Util.getLoginUserId(request) != null) {
			Long noOfRecords = null;
			try {
				noOfRecords = itemService.findNoOfItems(pageJson);
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
		// if (Util.getLoginUserId(request) != null) {
		ItemJson itemJson = null;
		try {
			itemJson = itemService.getItemById(id);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<ItemJson>(itemJson, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<ItemJson>(itemJson, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
	}

	@RequestMapping("getItemsBySubCategoryId")
	public ResponseEntity<List<ItemJson>> getItemsBySubCategoryId(@RequestParam("subCategoryId") Long subCategoryId, @RequestParam("firstResult") Integer firstResult,
			HttpServletRequest request) {
		// if (Util.getLoginUserId(request) != null) {
		List<ItemJson> itemJsonList = null;
		try {
			itemJsonList = itemService.getItemsBySubCategoryId(subCategoryId, firstResult);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<List<ItemJson>>(itemJsonList, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<List<ItemJson>>(itemJsonList, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
	}

	@RequestMapping("getNoOfProductsBySubCategory")
	public ResponseEntity<Long> getNoOfProductsBySubCategory(@RequestParam("subCategoryId") Long subCategoryId, HttpServletRequest request) {
		// if (Util.getLoginUserId(request) != null) {
		Long noOfRecords = null;
		try {
			noOfRecords = itemService.getNoOfProductsBySubCategory(subCategoryId);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Long>(noOfRecords, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Long>(noOfRecords, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
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

	@RequestMapping("getAllHomeProducts")
	public ResponseEntity<Map<String, List<ItemJson>>> getAllHomeProducts(HttpServletRequest request) {
		// if (Util.getLoginUserId(request) != null) {
		Map<String, List<ItemJson>> homeProductList = null;
		try {
			homeProductList = itemService.getAllHomeProducts();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Map<String, List<ItemJson>>>(homeProductList, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Map<String, List<ItemJson>>>(homeProductList, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
	}
	
	
	@RequestMapping("getAllSearchableWords")
	public ResponseEntity<List<Map<String,String>>> getAllSearchableWords(HttpServletRequest request) {
		// if (Util.getLoginUserId(request) != null) {
		List<Map<String,String>> searchableWordsList = null;
		try {
			searchableWordsList = itemService.getAllSearchableWords();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<List<Map<String,String>>>(searchableWordsList, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<List<Map<String,String>>>(searchableWordsList, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
	}
	
	@RequestMapping("getPageBySearchValue/{searchName}")
	public ResponseEntity<Map<String,Long>> getPageBySearchValue(HttpServletRequest request,@PathVariable("searchName")String searchName) {
		// if (Util.getLoginUserId(request) != null) {
		Map<String,Long> map = null;
		try {
			map = itemService.getPageBySearchValue(searchName);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return new ResponseEntity<Map<String,Long>>(map, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Map<String,Long>>(map, HttpStatus.OK);
		// } else {
		// LOG.error("User must login");
		// return new ResponseEntity(HttpStatus.FORBIDDEN);
		// }
	}


}
