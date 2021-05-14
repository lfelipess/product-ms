package com.compasso.desafio.productms.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.desafio.productms.domain.Product;
import com.compasso.desafio.productms.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="API REST Product")
@CrossOrigin(origins="*")
@RequestMapping("/v1")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@PostMapping("/products")
	@ApiOperation(value = "Create new product")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		Product newProduct = this.productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
	}
	
	@PutMapping("/products/{id}")
	@ApiOperation(value = "Update a specific product")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product product) {
		Product updatedProduct = this.productService.updateProduct(id, product);
		
		if(updatedProduct != null) {
			return ResponseEntity.ok().body(updatedProduct);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/products/{id}")
	@ApiOperation(value = "Find a specific product")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id) {		
		Optional<Product> product = this.productService.findProduct(id);
		
		if(product.isPresent()) {
			return ResponseEntity.ok().body(product.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("/products")
	@ApiOperation(value = "Find all products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = this.productService.findAllProducts();		
		return ResponseEntity.ok().body(products);		
	}
	
	@GetMapping("/products/search")
	@ApiOperation(value = "Find products with parameters")
	public ResponseEntity<List<Product>> getFilteredProducts(@RequestParam(value = "q", required = false) String name,
			@RequestParam(value = "min_price", required = false) Double minPrice, 
			@RequestParam(value = "max_price", required = false) Double maxPrice) {
		List<Product> products = this.productService.findFilteredProducts(name, minPrice, maxPrice);		
		return ResponseEntity.ok().body(products);		
	}
	
	
	@DeleteMapping("/products/{id}")
	@ApiOperation(value = "Delete a specific product")
	public ResponseEntity<Void> deleteNotice(@PathVariable(value = "id") Long id) {
		boolean productDeleted = this.productService.deleteProduct(id);
		
		if(productDeleted) {        
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleBadRequestExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    errors.put("status_code", "400");
	    errors.put("message", "O produto possui informações incorretas.");
	    return errors;
	}
}
