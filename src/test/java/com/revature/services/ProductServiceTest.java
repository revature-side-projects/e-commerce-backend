package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ProductInfo;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepository mockProductRepo;

	@InjectMocks
	ProductService pServ;

	Product dummyProduct;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 50, 9.99, "A dummy product", "tshort.jpg", "T-Shirt", null, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the mock t-shirt product
		this.dummyProduct = null;
	}

	private List<Product> getProductList() {
		List<Product> productList = new LinkedList<>();
		productList.add(this.dummyProduct);
		productList.add(new Product(2, 1, 123.45, "A rare dummy product", "rare.png", "Rare Item", null, null));
		productList.add(new Product(3, 1000, 4.49, "A common dummy product", "product.jpg", "Dummy", null, null));

		return productList;
	}

	@Test
	void testFindAll() {
		List<Product> productList = getProductList();
		given(this.mockProductRepo.findAll()).willReturn(productList);

		List<Product> expected = productList;
		List<Product> actual = this.pServ.findAll();

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockProductRepo, times(1)).findAll();
	}

	@Test
	void testFindById() {
		int id = this.dummyProduct.getId();
		given(this.mockProductRepo.findById(id)).willReturn(Optional.of(this.dummyProduct));

		Product expected = this.dummyProduct;
		Product actual = this.pServ.findById(id).get();

		assertEquals(expected, actual);
		verify(this.mockProductRepo, times(1)).findById(id);
	}
	
	@Test
	void testFindById_Failure_ProductNotFound() {
		int id = 404;
		given(this.mockProductRepo.findById(id)).willReturn(Optional.empty());
		try {
			this.pServ.findById(id);
			fail("Expected ProductNotFoundException to be throw");
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
		}
	}

	@Test
	void testSave() {
		given(this.mockProductRepo.save(this.dummyProduct)).willReturn(this.dummyProduct);

		Product expected = this.dummyProduct;
		Product actual = this.pServ.save(this.dummyProduct);

		assertEquals(expected, actual);
		verify(this.mockProductRepo, times(1)).save(this.dummyProduct);
	}

	@Test
	void testSaveAll() {
		List<Product> products = getProductList();
		given(this.mockProductRepo.saveAll(products)).willReturn(products);

		List<Product> expected = products;
		List<Product> actual = this.pServ.saveAll(products, new LinkedList<ProductInfo>());

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockProductRepo, times(1)).saveAll(products);
	}

	@Test
	void testDelete() {
		given(this.mockProductRepo.findById(this.dummyProduct.getId())).willReturn(Optional.of(this.dummyProduct));
		this.pServ.delete(this.dummyProduct.getId());
		verify(this.mockProductRepo, times(1)).deleteById(this.dummyProduct.getId());
	}
	
	@Test
	void testDelete_Failure_ProductNotFound() {
		int id = 404;
		given(this.mockProductRepo.findById(id)).willReturn(Optional.empty());
		try {
			this.pServ.delete(id);
			fail("Expected ProductNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
		}
	}

	@Test
	void testFindByNameContains() {
		String name = "dummy";

		// Product names must match case insensitive
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);
		given(this.mockProductRepo.findByNameContains(name)).willReturn(results);

		List<Product> expected = results;
		List<Product> actual = this.pServ.findByNameContains(name);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockProductRepo, times(1)).findByNameContains(name);
	}

	@Test
	void testFindByPriceRange() {
		double min = 5.0;
		double max = 10.50;
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);
		given(this.mockProductRepo.findByPriceRange(min, max)).willReturn(results);

		List<Product> expected = results;
		List<Product> actual = this.pServ.findByPriceRange(min, max);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockProductRepo, times(1)).findByPriceRange(min, max);
	}

	@Test
	@Disabled("Not yet implemented")
	void testFilterByRating() {
		fail("Not yet implemented");
	}

}
