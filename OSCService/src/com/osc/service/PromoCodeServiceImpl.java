package com.osc.service;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.osc.dao.PromoCodeDao;
import com.osc.entity.PromoCode;
import com.osc.json.PromoCodeJson;
import com.osc.util.Constants;
import com.osc.util.TransformEntityToJson;
import com.osc.util.TransformJsonToEntity;
import com.osc.util.Util;

@Service
@Transactional
public class PromoCodeServiceImpl implements PromoCodeService {
	private Logger LOG = Logger.getLogger(PromoCodeServiceImpl.class);
	@Autowired
	private PromoCodeDao promoCodeDao;

	public void saveOrUpdate(PromoCodeJson promoCodeJson) {
		try {
			PromoCode promoCode = null;
			if (promoCodeJson.getId() != null) {
				promoCode = (PromoCode) promoCodeDao.getById(PromoCode.class, promoCodeJson.getId());
				if (promoCode.getPromoImagePath() != null && promoCode.getPromoImagePath().trim().length() > 0) {
					try {
						Files.delete(FileSystems.getDefault().getPath(promoCode.getPromoImagePath()));
					} catch (Exception e) {
						e.printStackTrace();
						LOG.error(e.getMessage(), e);
					}

				}
			} else {
				promoCode = new PromoCode();
			}
			promoCodeJson.setPromoImagePath(Util.saveImage(promoCodeJson.getPromoImageBlob().getBytes()));
			TransformJsonToEntity.getPromoCode(promoCode, promoCodeJson);
			promoCodeDao.saveOrUpdate(promoCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}

	}

	public List<PromoCodeJson> getAllPromoCodes() {
		List<PromoCodeJson> promoCodeJsons = null;
		try {
			StringBuilder sb = new StringBuilder(
					"select c.id,c.code,c.applyOnAmount,c.promoImagePath,c.amountToReduce,c.subCategoryId from PromoCode c where c.isDeleted = false order by c.createdOn DESC");
			List<?> categories = promoCodeDao.findByQuery(sb.toString(), null, null, null);
			if (categories != null && categories.size() > 0) {
				promoCodeJsons = new ArrayList<PromoCodeJson>();
				for (Object object : categories) {
					Object[] obj = (Object[]) object;
					PromoCodeJson json = new PromoCodeJson();
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setCode(Util.getStringValueOfObj(obj[1]));
					json.setApplyOnAmount(Util.getDoubleValueOfObj(obj[2]));
					json.setPromoImagePath(Util.getStringValueOfObj(obj[3]));
					json.setPromoImageBlob(Util.getStringFromLocation(json.getPromoImagePath()));
					json.setAmountToReduce(Util.getDoubleValueOfObj(obj[4]));
					json.setSubCategoryId(Util.getIntegerValueOfObj(obj[5]));
					promoCodeJsons.add(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return promoCodeJsons;
	}

	public PromoCodeJson getPromoCodeById(Long id) {
		PromoCodeJson promoCodeJson = null;
		try {
			PromoCode promoCode = (PromoCode) promoCodeDao.getById(PromoCode.class, id);

			if (promoCode != null) {
				promoCodeJson = new PromoCodeJson();
				TransformEntityToJson.getPromoCodeJson(promoCode, promoCodeJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return promoCodeJson;
	}

	public void deletePromoCodeById(Long id, Long userId) {
		try {
			PromoCode promoCode = (PromoCode) promoCodeDao.getById(PromoCode.class, id);

			if (promoCode != null && !promoCode.getIsDeleted()) {
				promoCode.setIsDeleted(Boolean.TRUE);
				promoCode.setUpdatedBy(userId);
				promoCode.setUpdatedOn(new Date());
				promoCodeDao.saveOrUpdate(promoCode);
				if (promoCode.getPromoImagePath() != null && promoCode.getPromoImagePath().trim().length() > 0) {
					try {
						Files.delete(FileSystems.getDefault().getPath(promoCode.getPromoImagePath()));
					} catch (Exception e) {
						e.printStackTrace();
						LOG.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
	}

	public Map<String, Object> applyPromoCode(String promoCode, Long customerId, Double totalAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder(
					"select c.id,c.code,c.applyOnAmount,c.amountToReduce,c.subCategoryId from PromoCode c where c.isDeleted = false and c.code = ?1 order by c.createdOn DESC");
			params.put("1", promoCode);
			List<?> promocodeList = promoCodeDao.findByQuery(sb.toString(), params, null, null);
			if (promocodeList != null && promocodeList.size() > 0) {
				PromoCodeJson json = new PromoCodeJson();
				for (Object object : promocodeList) {
					Object[] obj = (Object[]) object;
					json.setId(Util.getIntegerValueOfObj(obj[0]));
					json.setCode(Util.getStringValueOfObj(obj[1]));
					json.setApplyOnAmount(Util.getDoubleValueOfObj(obj[2]));
					json.setAmountToReduce(Util.getDoubleValueOfObj(obj[3]));
					json.setSubCategoryId(Util.getIntegerValueOfObj(obj[4]));
				}
				map.put(Constants.General.SUBCATEGORY_ID, json.getSubCategoryId());
				String subCategoryName = "";
				if (json.getSubCategoryId() > 0) {
					params.clear();
					List<Long> itemIds = new ArrayList<Long>();
					sb = new StringBuilder("select s.itemId,s.subCategory.name from SubCategoryItem s where s.subCategoryId = ?1");
					params.put("1", json.getSubCategoryId());
					List<?> list = promoCodeDao.findByQuery(sb.toString(), params, null, null);
					if (list != null && list.size() > 0) {
						for (Object object : list) {
							Object[] obj =(Object[]) object;
							itemIds.add(Util.getIntegerValueOfObj(obj[0]));
							subCategoryName = Util.getStringValueOfObj(obj[1]);
						}
					}
					map.put(Constants.General.ITEM_IDS, itemIds);
				}
					if (json.getApplyOnAmount() > totalAmount) {
						map.put(Constants.General.SUCCESS, false);
						if (json.getSubCategoryId().equals(0l)) {
							map.put(Constants.General.MSG, Constants.General.LESSMOUNT_ERR + json.getApplyOnAmount());
						} else {
							map.put(Constants.General.MSG, Constants.General.LESSMOUNT_ERR + json.getApplyOnAmount() + " for the products of " + subCategoryName);
						}
//					}
					return map;
				}
				params.clear();
				sb = new StringBuilder("select c.id from CustomerAppliedPromoCode c where c.customerId = ?1 and c.promoId = ?2");
				params.put("1", customerId);
				params.put("2", json.getId());
				promocodeList = promoCodeDao.findByQuery(sb.toString(), params, null, null);
				if (promocodeList != null && promocodeList.size() > 0) {
					map.put(Constants.General.SUCCESS, false);
					map.put(Constants.General.MSG, Constants.General.ALREADY_APPLIED_ERR);
					return map;
				} else {
					map.put(Constants.General.SUCCESS, true);
					map.put(Constants.General.MSG, Constants.General.PROMOCODE_APPLIED_SUCC);
					map.put(Constants.General.PROMOCODE_ID, json.getId());
					map.put(Constants.General.AMOUNT_TO_REDUCE, json.getAmountToReduce());
					return map;
				}
			} else {
				map.put(Constants.General.SUCCESS, false);
				map.put(Constants.General.MSG, Constants.General.INVALID_PROMOCODE_ERR);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
		}
		return map;
	}

}
