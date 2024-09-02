package com.ap.cypto.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.SecretKey;

import com.ap.cypto.model.AesLocalKey;

public class KeyStoreUtil {
	private static final String KEYSTORE_FILE = "C:\\Users\\Akash Pratap\\OneDrive\\Desktop\\CRYPTO_SERVICE\\keystore\\";
    private static final String KEY_ALIAS = "my_aes_key";
    
    
    public static void main(String args[]) {
    	
	       
    }
	
    public static AesLocalKey creatKeyStore(SecretKey aesKey,Integer tenantId) {
		KeyStore keyStore;
		AesLocalKey  aesLocalKey=new AesLocalKey();
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			String password =PasswordUtil.generatePassword();
			keyStore.load(null, password.toCharArray());
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(aesKey);
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(password.toCharArray());
            keyStore.setEntry(KEY_ALIAS, secretKeyEntry, protectionParameter);
            String filePath=createTenantDir(tenantId);            
            FileOutputStream fos = new FileOutputStream(filePath);
            keyStore.store(fos, password.toCharArray());
            fos.close();
            
            aesLocalKey.setKeystorePath(filePath);
            aesLocalKey.setTenantId(tenantId);
            aesLocalKey.setKeystorePassword(password);
	        return aesLocalKey;
			
		} catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
			e.printStackTrace();
		}
		return aesLocalKey;
		
	}
	
    public static String createTenantDir(Integer tenantId) {
    	Path dirPath = Paths.get(KEYSTORE_FILE+"\\"+tenantId);
    	if (!Files.exists(dirPath)) {
    	    try {
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}    	
    	String filename = "keystore.p12";
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = currentDateTime.format(formatter);
        int dotIndex = filename.lastIndexOf('.');
        String base = (dotIndex == -1) ? filename : filename.substring(0, dotIndex);
        String ext = (dotIndex == -1) ? "" : filename.substring(dotIndex);
        String newFilename = base + "_" + timestamp + ext;
    	return dirPath.toString()+"\\"+newFilename;
    }
   
	public static Key fetchKeyFromKeyStore(AesLocalKey keyDteails){
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			 FileInputStream fis = new FileInputStream(keyDteails.getKeystorePath());
		        keyStore.load(fis, keyDteails.getKeystorePassword().toCharArray());
		        fis.close();
		        Key key = keyStore.getKey(KEY_ALIAS, keyDteails.getKeystorePassword().toCharArray());
		        return key;
		} catch (Exception e) {
			e.printStackTrace();
		} 
       return null;
        
  
	}

	

}
