package com.ap.cypto.util;

import java.security.Key;
import java.util.Map;
import java.util.HashMap;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ap.cypto.model.AesLocalKey;
import com.ap.redis.service.CacheService;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AESUtils {
	private static String AES = "AES";
	private static int keySize = 256;
	
	static CacheService cacheService;

	public CacheService getCacheService() {
		return cacheService;
	}

	@Autowired
	public void setCacheService(CacheService cacheS) {
		cacheService = cacheS;
	}


	public static void main(String[] args) {
		try {		
			 AesLocalKey temp=generateAESKey(2);
		     encryptString("hello",temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String decryptString(String data,AesLocalKey keyDteails) {
		try {
		Cipher c=null;
		BaseCipher baseCipher=null;
		try {
			baseCipher = (BaseCipher) cacheService.getData("Cipher:"+1);
		} catch (Exception e) {
			 e.printStackTrace();
			 baseCipher=null;
		}
		
		
		if(baseCipher==null) {
		Key key=KeyStoreUtil.fetchKeyFromKeyStore(keyDteails);
		c=Cipher.getInstance(AES);
		c.init(Cipher.DECRYPT_MODE, key);
		baseCipher=new BaseCipher();
		baseCipher.setCipher(c);
		cacheService.putData("Cipher:"+1,baseCipher);
		}
		
		
		byte[] encryptedData = Base64.getDecoder().decode(data);
		byte[] decrptVal = baseCipher.getCipher().doFinal(encryptedData);
		return new String(decrptVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failed to decrypt";
	}

	public static String encryptString(String data,AesLocalKey keyDteails) {
		Cipher c;
		try {
			c = Cipher.getInstance(AES);
			Key key=KeyStoreUtil.fetchKeyFromKeyStore(keyDteails);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failed to encryptString";
		
	}

	public static AesLocalKey generateAESKey(Integer tenantId) {
		SecureRandom secureRandom = new SecureRandom();
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(keySize, secureRandom);
			return KeyStoreUtil.creatKeyStore(keyGenerator.generateKey(), tenantId);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}

}
