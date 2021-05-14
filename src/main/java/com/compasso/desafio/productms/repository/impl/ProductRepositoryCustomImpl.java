package com.compasso.desafio.productms.repository.impl;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.compasso.desafio.productms.domain.Product;
import com.compasso.desafio.productms.repository.ProductRepositoryCustom;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Product> findFilteredProducts(String name, Double minPrice, Double maxPrice) {
		StringBuilder jpql = new StringBuilder();
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		jpql.append("from Product where 0=0 ");
		if(StringUtils.hasLength(name)){
			jpql.append("and (name like :nameParam ");
			jpql.append("or description like :nameParam )");
			parameters.put("nameParam", "%"+name+"%");
		}
		
		if(minPrice != null){
			jpql.append("and price >= :minPrice ");
			parameters.put("minPrice", minPrice);
		}
		
		if(maxPrice != null){
			jpql.append("and price <= :maxPrice ");
			parameters.put("maxPrice", maxPrice);
		}
		
		TypedQuery<Product> query = entityManager.createQuery(jpql.toString(), Product.class);
		parameters.forEach((key, value) -> {
			query.setParameter(key, value);
		});
		
		return query.getResultList();
	}	
}
