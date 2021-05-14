package com.compasso.desafio.productms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.compasso.desafio.productms.domain.Product;
import com.compasso.desafio.productms.repository.ProductRepository;
import com.compasso.desafio.productms.repository.impl.ProductRepositoryCustomImpl;
import com.compasso.desafio.productms.service.impl.ProductServiceImpl;
import com.compasso.desafio.productms.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductMsApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	@InjectMocks
	private ProductRepositoryCustomImpl productRepositoryCustomImpl;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private EntityManager entityManager;

	@Test
	public void createProductTest() throws Exception {
		Product product = new Product();
		product.setName("test");
		product.setDescription("descTest");
		product.setPrice(10.0);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(JsonUtils.objectToJson(product)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void createProductTest_Error() throws Exception {
		Product product = new Product();
		product.setName(null);
		product.setDescription("descTest");
		product.setPrice(10.0);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(JsonUtils.objectToJson(product)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void updateProductTest_Found() throws Exception {
		Product productUpdated = new Product();
		productUpdated.setName("test2");
		productUpdated.setDescription("descTest2");
		productUpdated.setPrice(20.0);
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(JsonUtils.objectToJson(productUpdated)))
				.andExpect(status().isOk());
	}
	
	@Test
	void updateProductTest_NotFound() throws Exception{
		Product productUpdated = new Product();
		productUpdated.setName("test2");
		productUpdated.setDescription("descTest2");
		productUpdated.setPrice(20.0);
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/products/1999")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(JsonUtils.objectToJson(productUpdated)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void findProduct_Found() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/1"))
				.andExpect(status().isOk());
	}
	
	@Test
	void findProduct_NotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/1999"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void findAllProducts_Found() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/products"))
				.andExpect(status().isOk());
	}
	
	@Test
	void findFilteredProducts_Found() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/products/search")
				.param("q", "um")
				.param("min_price", "5.0")
				.param("max_price", "15.0"))
			.andExpect(status().isOk());
	}
	
	@Test
	void deleteProductTest_Found() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/products/98"))
		.andExpect(status().isOk());
	}
	
	@Test
	void deleteProductTest_NotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/products/1999"))
		.andExpect(status().isNotFound());
	}
}
