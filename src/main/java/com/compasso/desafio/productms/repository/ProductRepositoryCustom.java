package com.compasso.desafio.productms.repository;

import java.util.List;

import com.compasso.desafio.productms.domain.Product;

public interface ProductRepositoryCustom {

	public List<Product> findFilteredProducts(String name, Double minPrice, Double maxPrice);
}
