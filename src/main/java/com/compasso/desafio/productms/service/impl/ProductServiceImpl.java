package com.compasso.desafio.productms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.desafio.productms.domain.Product;
import com.compasso.desafio.productms.repository.ProductRepository;
import com.compasso.desafio.productms.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, Product newProduct) {
		Optional<Product> productDomain = productRepository.findById(id);
		
		if(productDomain.isPresent()) {
			Product oldProduct = productDomain.get();
			
			oldProduct.setName(newProduct.getName());
			oldProduct.setDescription(newProduct.getDescription());
			oldProduct.setPrice(newProduct.getPrice());
			
			final Product updatedProduct = productRepository.save(oldProduct);			
			return updatedProduct;
		}
		
		return null;
	}

	@Override
	public Optional<Product> findProduct(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findFilteredProducts(String name, Double minPrice, Double maxPrice) {
		return productRepository.findFilteredProducts(name, minPrice, maxPrice);
	}

	@Override
	public boolean deleteProduct(Long id) {
		Optional<Product> productDomain = productRepository.findById(id);
		
		if(productDomain.isPresent()) {
			productRepository.delete(productDomain.get());
			return true;
		}
		
		return false;
	}
}
