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

import com.osc.json.PromoCodeJson;
import com.osc.service.PromoCodeService;
import com.osc.util.Util;

@RestController
@RequestMapping("PromoCodeController")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PromoCodeController {
	private Logger LOG = Logger.getLogger(PromoCodeController.class);
	@Autowired
	private PromoCodeService promoCodeService;

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrUpdate(@RequestBody PromoCodeJson promoCodeJson, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
		try {
			promoCodeJson.setCreatedBy(Util.getLoginUserId(request));
			promoCodeJson.setUpdatedBy(Util.getLoginUserId(request));
			promoCodeService.saveOrUpdate(promoCodeJson);
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

	
	@RequestMapping("getAllPromoCodes")
	public ResponseEntity<List<PromoCodeJson>> getAllPromoCodes(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<PromoCodeJson> promoCodeJsons = null;
			try {
				promoCodeJsons = promoCodeService.getAllPromoCodes();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<PromoCodeJson>>(promoCodeJsons, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<PromoCodeJson>>(promoCodeJsons, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("getAllPromoCodeImages")
	public ResponseEntity<List<String>> getAllPromoCodeImages(HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			List<String> promoCodeImageList = null;
			try {
				promoCodeImageList = promoCodeService.getAllPromoCodeImages();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<List<String>>(promoCodeImageList, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<List<String>>(promoCodeImageList, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("getPromoCodeById/{id}")
	public ResponseEntity<PromoCodeJson> getPromoCodeById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			PromoCodeJson promoCodeJson = null;
			try {
				promoCodeJson = promoCodeService.getPromoCodeById(id);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<PromoCodeJson>(promoCodeJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<PromoCodeJson>(promoCodeJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("deletePromoCodeById/{id}")
	public ResponseEntity<PromoCodeJson> deletePromoCodeById(@PathVariable("id") Long id, HttpServletRequest request) {
		if (Util.getLoginUserId(request) != null) {
			PromoCodeJson promoCodeJson = null;
			try {
				promoCodeService.deletePromoCodeById(id, Util.getLoginUserId(request));
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<PromoCodeJson>(promoCodeJson, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<PromoCodeJson>(promoCodeJson, HttpStatus.OK);
		} else {
			LOG.error("User must login");
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("applyPromoCode")
	public ResponseEntity<Map<String,Object>> applyPromoCode(@RequestParam("customerId")Long customerId,@RequestParam("promoCode")String promoCode,
			@RequestParam("totalAmount")Double totalAmount, HttpServletRequest request) {
//		if (Util.getLoginUserId(request) != null) {
		Map<String,Object> map = null;
			try {
				map =promoCodeService.applyPromoCode(promoCode,customerId,totalAmount);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error(e.getMessage(), e);
				return new ResponseEntity<Map<String,Object>>(map, HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//		} else {
//			LOG.error("User must login");
//			return new ResponseEntity(HttpStatus.FORBIDDEN);
//		}
	}


}
