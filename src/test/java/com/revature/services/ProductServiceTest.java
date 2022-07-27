package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepository mockProductRepo;

	@InjectMocks
	ProductService pServ;

	Product mockProduct;

	@BeforeEach
	void setUp() throws Exception {
		this.mockProduct = new Product(1, 50, 9.99, "A dummy product", "tshort.jpg", "T-Shirt", null, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the mock t-shirt product
		this.mockProduct = null;
	}

	private List<Product> getProductList() {
		List<Product> productList = new LinkedList<>();
		productList.add(this.mockProduct);
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
		int id = this.mockProduct.getId();
		given(this.mockProductRepo.findById(id)).willReturn(Optional.of(this.mockProduct));

		Product expected = this.mockProduct;
		Product actual = this.pServ.findById(id).get();

		assertEquals(expected, actual);
		verify(this.mockProductRepo, times(1)).findById(id);
	}

	@Test
	void testSave() {
		given(this.mockProductRepo.save(this.mockProduct)).willReturn(this.mockProduct);

		Product expected = this.mockProduct;
		Product actual = this.pServ.save(this.mockProduct);

		assertEquals(expected, actual);
		verify(this.mockProductRepo, times(1)).save(this.mockProduct);
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
		this.pServ.delete(this.mockProduct.getId());
		verify(this.mockProductRepo, times(1)).deleteById(this.mockProduct.getId());
	}

}
