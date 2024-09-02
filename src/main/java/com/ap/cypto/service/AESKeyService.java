package com.ap.cypto.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ap.cypto.Dao.CyptoDaoImpl;
import com.ap.cypto.model.AesLocalKey;
import com.ap.cypto.util.AESUtils;

@RestController
public class AESKeyService {
	
	@Autowired
	CyptoDaoImpl cyptoDaoImpl;
	
	@GetMapping("/create/{tenantId}")
	public String createAesKey(@PathVariable int tenantId) {
		AesLocalKey aesLocalKey = new AesLocalKey();
		
		aesLocalKey=AESUtils.generateAESKey(tenantId);
		if(aesLocalKey==null)
		return "Failed to create";
		
		cyptoDaoImpl.save(aesLocalKey);
		return "Success	";
	}
	
	@GetMapping("/encryptString/{tenantId}")
	public String encryptString(@PathVariable int tenantId,@RequestParam("data")String data) {
		AesLocalKey aesLocalKey=cyptoDaoImpl.getAesDetails(tenantId);
		
		return AESUtils.encryptString(data, aesLocalKey);
	}

	@GetMapping("/decryptString/{tenantId}")
	public String decryptString(@PathVariable int tenantId,@RequestParam("data")String data) {
		AesLocalKey aesLocalKey=cyptoDaoImpl.getAesDetails(tenantId);
		
		return AESUtils.decryptString(data, aesLocalKey);
	}

	
	public CyptoDaoImpl getCyptoDaoImpl() {
		return cyptoDaoImpl;
	}

	public void setCyptoDaoImpl(CyptoDaoImpl cyptoDaoImpl) {
		this.cyptoDaoImpl = cyptoDaoImpl;
	}
	
	

}
