package com.osc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Key;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.osc.json.UserJson;

public class Util {
	private static Logger LOG = Logger.getLogger(Util.class);
	private final static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("dd-MM-yyyy");
		}
	};

	public static Date convertDiffferentFormatString(String str) {
		Date date = null;
		try {
			if (str != null && str.length() > 9) {
				// SimpleDateFormat dateformat = null;
				String s1 = null;
				if (str.length() == 11) {
					// dateformat = new SimpleDateFormat("dd-MMM-yyyy");
					s1 = str.subSequence(0, 11).toString();
				} else {
					// dateformat = new SimpleDateFormat("yyyy-MM-dd");
					s1 = str.subSequence(0, 10).toString();
				}

				date = sdf.get().parse(s1);
				date = convertStringToDate(convertDateToString(date));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at convertDateToString() in TransformDomainToJson:" + e.getMessage(), e);
		}
		return date;
	}

	public static String convertDoubleToMoney(Double dbl) {
		String str = null;
		if (dbl != null) {
			Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			str = format.format(new BigDecimal(dbl));
			if (str != null && str.length() > 0) {
				str = str.split("\\.")[1];
			}
		}
		return str;
	}

	public static Long convertDoubleToLong(Double dbl) {
		Long value = null;
		if (dbl != null) {
			value = dbl.longValue();
		}
		return value;
	}

	public static String convertLongToMoney(Long ln) {
		String str = null;
		if (ln != null) {
			Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			str = format.format(ln);
			if (str != null && str.length() > 0) {
				str = str.split("\\.")[1];
			}
		}
		return str;
	}

	public static String concateStringValues(String[] strArray) {
		if (strArray != null && strArray.length > 0) {
			String strValue = null;
			for (String str : strArray) {
				if (strValue == null) {
					strValue = str;
				} else {
					strValue += "," + str;
				}
			}
			return strValue;
		}
		return null;
	}

	public static List<Integer> getListByString(String strvalue) {
		List<Integer> list = null;
		if (strvalue != null) {
			String[] strArray = strvalue.split(",");
			if (strArray != null && strArray.length > 0) {
				list = new ArrayList<Integer>();
				for (String str : strArray) {
					list.add(Integer.parseInt(str));
				}
			}
		}
		return list;
	}

	public static String getInputStream(String fileName, String filePath) {
		String inputStreamToString = null;
		try {
			File initialFile = new File(filePath + "\\" + fileName);
			InputStream inputStream = new FileInputStream(initialFile);
			inputStreamToString = IOUtils.toString(inputStream, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStreamToString;

	}

	public static String[] getListOfStringsByIntArray(Integer[] listOfIds, Map<Integer, String> idWithDescriptionMap) {
		String[] strAmenities = null;
		if (listOfIds != null && listOfIds.length > 0) {
			strAmenities = new String[listOfIds.length];
			for (int i = 0; i < listOfIds.length; i++) {
				strAmenities[i] = idWithDescriptionMap.get(listOfIds[i]);
			}
		}
		return strAmenities;

	}

	public static Date convertStringToDate(String strDate) {
		Date date = null;
		try {
			if (strDate != null && strDate.trim().length() > 0) {
				date = sdf.get().parse(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at convertStringToDate() in TransformDomainToJson:" + e.getMessage(), e);
		}
		return date;
	}

	/**
	 * This method is to convert Date to String
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sdf.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at convertDateToString() in TransformDomainToJson:" + e.getMessage(), e);
		}
		return strDate;
	}

	/**
	 * This is the method to encrypt the given String
	 * 
	 * @param string
	 * @return encrypted String
	 */
	public static String passwordEncryption(String string) {
		String encryptedString = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(Constants.General.ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(string.getBytes());
			encryptedString = new BASE64Encoder().encode(encVal);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at passwordEncryption() in CommonUtil:" + e.getMessage(), e);
		}
		return encryptedString;
	}

	/**
	 * This method is to decrypt the encrypted String
	 * 
	 * @param encryptedString
	 * @return actual value of encrypted String
	 */
	public static String passwordDecryption(String encryptedString) {
		String decryptedString = null;
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(Constants.General.ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedString);
			byte[] decValue = c.doFinal(decordedValue);
			decryptedString = new String(decValue);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error at passwordDecryption() in CommonUtil:" + e.getMessage(), e);
		}
		return decryptedString;
	}

	/**
	 * This method is to generate the key based on the selected Algorithm
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(Constants.General.KEYVALUE, Constants.General.ALGO);
		return key;
	}
	

	public static Integer getLoginUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserJson json = (UserJson) session.getAttribute(Constants.General.LOGIN_USER);
		if (json != null) {
			return json.getId();
		}
		return null;
	}

	public static UserJson getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserJson json = (UserJson) session.getAttribute(Constants.General.LOGIN_USER);
		return json;
	}
	
	public static String getStringValueOfObj(Object obj) {
		String strValue = "";
		if (obj != null) {
			strValue = obj.toString();
		}
		return strValue;
	}
	
	public static Integer getIntegerValueOfObj(Object obj) {
		Integer intValue = 0;
		if (obj != null) {
			if (obj instanceof BigInteger) {
				intValue = ((BigInteger) obj).intValue();
			} else {
				intValue = (Integer) obj;
			}

		}
		return intValue;
	}

}
