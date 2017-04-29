package com.osc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.osc.json.CustomerJson;

public class HashCodeGenerator {
	private static final Logger LOG = Logger.getLogger(HashCodeGenerator.class);

	public static void generatehash(CustomerJson customerJson) {
		try {
			// totalOrdersJson.setStrTotalAmount("1");
			customerJson.setStrTotalPurchase(String.valueOf(customerJson.getTotalPurchase()));
			Map<String, String> params = new HashMap<String, String>();
			params.put("key", "4XJHz7g1");
			params.put("service_provider", "payu_paisa");
			params.put("amount", customerJson.getStrTotalPurchase());
			params.put("firstname", customerJson.getFirstName());
			params.put("email", customerJson.getEmailId());
			params.put("phone", String.valueOf(customerJson.getPhoneNumber()));
			params.put("productinfo", "nothing");
//			params.put("surl", "http://localhost:8080/"+Constants.CONTEXT_ROOT+"/views/homeSuccess.faces");
//			params.put("furl", "http://localhost:8080/"+Constants.CONTEXT_ROOT+"/views/home.faces");
			params.put("surl", "http://localhost:8080/OSCWeb/");
			params.put("furl", "http://localhost:8080/OSCWeb/");
			params.put("lastname", "");
			params.put("curl", "");
			params.put("address1", "");
			params.put("address2", "");
			params.put("city", "");
			params.put("state", "");
			params.put("country", "");
			params.put("zipcode", "");
			params.put("udf1", "");
			params.put("udf3", "");
			params.put("udf4", "");
			params.put("udf5", "");
			params.put("pg", "");

			Random rand = new Random();
			String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
			String txnid = hashCal("SHA-256", rndm).substring(0, 20);
			System.out.println(txnid);
			params.put("txnid", txnid);
			params.put("udf2", txnid);
			String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";

			if (empty(params.get("hash")) && params.size() > 0) {
				if (empty(params.get("key")) || empty(params.get("txnid")) || empty(params.get("amount")) || empty(params.get("firstname")) || empty(params.get("email")) || empty(params.get("phone")) || empty(params.get("productinfo"))
						|| empty(params.get("surl")) || empty(params.get("furl")) || empty(params.get("service_provider"))) {
					System.out.println("hi");
				} else {
					String[] hashVarSeq = hashSequence.split("\\|");
					String hashString = "";
					for (String part : hashVarSeq) {
						hashString = (empty(params.get(part))) ? hashString.concat("") : hashString.concat(params.get(part));
						hashString = hashString.concat("|");
					}
					hashString = hashString.concat("6sIfMTdhl8");

					String hash = hashCal("SHA-512", hashString);
					System.out.println(hash);
					// action1=base_url.concat("/_payment");
					// System.out.println(action1);
					params.put("hash", hash);
					// for (String key : params.keySet()) {
					// System.out.println(" <input type=\"hidden\" name=\"" +
					// key +
					// "\" value=\"" + params.get(key) + "\" />");
					// }
					customerJson.setHashCodeId(hash);
					customerJson.setTxnId(txnid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at generatehash() in HashCodeGenerator:" + e.getMessage(), e);
		}
	}

	public static String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
			LOG.error("Error at hashCal() in HashCodeGenerator:" + nsae.getMessage(), nsae);
		}

		return hexString.toString();

	}

	public static boolean empty(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else
			return false;
	}
}
