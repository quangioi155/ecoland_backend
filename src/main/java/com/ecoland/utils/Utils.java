package com.ecoland.utils;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.data.domain.Page;

import com.ecoland.model.request.PageRequest;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@SuppressWarnings("deprecation")
public class Utils {
    
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static final String ALGORITHM = "AES";

    public static void prepareSecreteKey(String myKey) {
	MessageDigest sha = null;
	try {
	    key = myKey.getBytes(StandardCharsets.UTF_8);
	    sha = MessageDigest.getInstance("SHA-1");
	    key = sha.digest(key);
	    key = Arrays.copyOf(key, 16);
	    secretKey = new SecretKeySpec(key, ALGORITHM);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
    }

    public static String encrypt(String strToEncrypt, String secret) {
	try {
	    prepareSecreteKey(secret);
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	} catch (Exception e) {
	    System.out.println("Error while encrypting: " + e.toString());
	}
	return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
	try {
	    prepareSecreteKey(secret);
	    Cipher cipher = Cipher.getInstance(ALGORITHM);
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	} catch (Exception e) {
	    System.out.println("Error while decrypting: " + e.toString());
	}
	return null;
    }

    public static List<?> resultList(Page<?> page) {
	List<Object> response = new ArrayList<>();
	PageRequest pageRequest = new PageRequest();

	pageRequest.setTotalItems(page.getTotalElements());
	pageRequest.setTotalPages(page.getTotalPages());
	pageRequest.setCurrentPage(page.getNumber());
	pageRequest.setItems(page.toList());

	response.add(pageRequest);
	return response;
    }

    public static <T> void addScalr(NativeQuery<?> sqlQuery, Class<T> clazz) {
	if (clazz == null) {
	    throw new NullPointerException("[clazz] could not be null!");
	}
	Field[] fields = clazz.getDeclaredFields();
	for (Field field : fields) {
	    if (field.getType() == long.class || field.getType() == Long.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
	    }
	    if (field.getType() == int.class || field.getType() == Integer.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
	    }
	    if (field.getType() == char.class || field.getType() == Character.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.CHARACTER);
	    }
	    if (field.getType() == short.class || field.getType() == Short.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.SHORT);
	    }
	    if (field.getType() == double.class || field.getType() == Double.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
	    }
	    if (field.getType() == float.class || field.getType() == Float.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
	    }
	    if (field.getType() == boolean.class || field.getType() == Boolean.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
	    }
	    if (field.getType() == String.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
	    }
	    if (field.getType() == Date.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.DATE);
	    }
	    if (field.getType() == Timestamp.class) {
		sqlQuery.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
	    }
	}
	sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
    }
    
    public static Date convertStringToDate(String format, String date) {
    	try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    public static String  convertDateToString(String format, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(format);  
        return dateFormat.format(date);  
    }
}
