package com.ap.cypto.util;

import javax.crypto.Cipher;

public class BaseCipher implements java.io.Serializable {
	private static final long serialVersionUID = 1L;


	private Cipher cipher;


	public Cipher getCipher() {
		return cipher;
	}


	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
	
}
	
