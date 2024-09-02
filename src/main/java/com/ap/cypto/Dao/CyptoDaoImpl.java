package com.ap.cypto.Dao;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.ap.cypto.model.AesLocalKey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CyptoDaoImpl {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public boolean save(AesLocalKey aesLocalKey) {
		try {
			entityManager.merge(aesLocalKey);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	@ReadOnlyProperty
	public AesLocalKey getAesDetails(int tenantId) {
		try {
			Query query=entityManager.createQuery("FROM AesLocalKey WHERE tenantId= :tenantId");
			query.setParameter("tenantId", tenantId);
			return (AesLocalKey) query.getResultList().get(0); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
