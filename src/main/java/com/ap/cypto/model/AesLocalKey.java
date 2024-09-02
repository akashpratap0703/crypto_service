package com.ap.cypto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aes_local_key")
public class AesLocalKey{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_key_id")
	private Integer pkKeyId;
	
	@Column(name = "keystore_path")
	private String KeystorePath;
	
	@Column(name = "keystore_password")
	private String KeystorePassword;
	
	@Column(name = "tenant_id")
	private Integer tenantId;
	
	
	public Integer getPkKeyId() {
		return pkKeyId;
	}
	public void setPkKeyId(Integer pkKeyId) {
		this.pkKeyId = pkKeyId;
	}
	public Integer getTenantId() {
		return tenantId;
	}
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
	public String getKeystorePath() {
		return KeystorePath;
	}
	public void setKeystorePath(String keystorePath) {
		KeystorePath = keystorePath;
	}
	public String getKeystorePassword() {
		return KeystorePassword;
	}
	public void setKeystorePassword(String keystorePassword) {
		KeystorePassword = keystorePassword;
	}
	
	
	

	
}
