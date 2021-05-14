package com.compasso.desafio.productms.service;

import java.util.List;
import java.util.Optional;

import com.compasso.desafio.productms.domain.Product;

public interface IProductService {
	/**
	 * Method used for create a new product
	 * @param product
	 * @return
	 */
	Product createProduct(Product product);
	
	/**
	 * Method used for update a specific product
	 * @param id
	 * @param product
	 * @return
	 */
	Product updateProduct(Long id, Product product);
	
	/**
	 * Method used for search a specific product
	 * @param id
	 * @return
	 */
	Optional<Product> findProduct(Long id);
	
	/**
	 * Method used for search all products
	 * @return
	 */
	List<Product> findAllProducts();
	
	/**
	 * Method used for search products with parameters
	 * @return
	 */
	List<Product> findFilteredProducts(String name, Double minPrice, Double maxPrice);
	
	/**
	 * Method used for delete a specific product
	 * @param id
	 * @return
	 */
	boolean deleteProduct(Long id);
}
