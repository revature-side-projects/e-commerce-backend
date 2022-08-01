package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
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

import com.revature.dtos.PurchaseRequest;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Product;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.repositories.PurchaseRepository;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

	@Mock
	PurchaseRepository mockPurchaseRepo;

	@Mock
	ProductService productServ;

	@Mock
	UserService uServ;

	@InjectMocks
	PurchaseService purchaseServ;

	Purchase dummyPurchase;
	Product dummyProduct;
	User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 50, 9.99, "A dummy product", "tshort.jpg", "T-Shirt", null,
				new LinkedHashSet<Purchase>());
		this.dummyUser = new User(1, "customer@revature.com", "asdf", "A", "Customer", "Customer",
				new LinkedHashSet<Purchase>(), null, null);
		this.dummyPurchase = new Purchase(1, new Timestamp(1234567890000L), this.dummyProduct, this.dummyUser, 1);

		this.dummyProduct.getPurchases().add(this.dummyPurchase);
		this.dummyUser.getPurchases().add(this.dummyPurchase);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.dummyUser = null;
		this.dummyPurchase = null;
		this.dummyProduct = null;
	}

	@Test
	void testFindAll() {
		List<Purchase> purchases = new LinkedList<>();
		purchases.add(this.dummyPurchase);
		given(this.mockPurchaseRepo.findAll()).willReturn(purchases);

		List<Purchase> expected = purchases;
		List<Purchase> actual = this.purchaseServ.findAll();

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockPurchaseRepo, times(1)).findAll();
	}

	@Test
	void testFindByOwner() {
		List<Purchase> purchases = new LinkedList<>();
		purchases.add(this.dummyPurchase);
		given(this.uServ.findById(this.dummyUser.getId())).willReturn(Optional.of(this.dummyUser));
		given(this.mockPurchaseRepo.findByOwnerUser(this.dummyUser)).willReturn(purchases);

		List<Purchase> expected = purchases;
		List<Purchase> actual = this.purchaseServ.findByOwner(this.dummyUser.getId());

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockPurchaseRepo, times(1)).findByOwnerUser(this.dummyUser);
	}
	
	@Test
	void testFindByOwner_Failure_UserNotFound() {
		int id = 2;
		given(this.uServ.findById(id)).willReturn(Optional.empty());
		
		try {
			this.purchaseServ.findByOwner(id);
			fail("Expected UserNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}

	@Test
	void testAdd() {
		int productId = this.dummyProduct.getId();
		int buyerId = this.dummyUser.getId();
		PurchaseRequest createRequest = new PurchaseRequest(productId, buyerId, this.dummyPurchase.getQuantity());
		given(this.uServ.findById(buyerId)).willReturn(Optional.of(this.dummyUser));
		given(this.productServ.findById(createRequest.getId())).willReturn(Optional.of(this.dummyProduct));
		Purchase newPurchase = new Purchase();
		newPurchase.setProduct(this.dummyProduct);
		newPurchase.setOwnerUser(this.dummyUser);
		newPurchase.setQuantity(createRequest.getQuantity());
		given(this.mockPurchaseRepo.save(newPurchase)).willReturn(this.dummyPurchase);

		Purchase expected = this.dummyPurchase;
		Purchase actual = this.purchaseServ.add(createRequest, buyerId);

		assertEquals(expected, actual);
		verify(this.productServ, times(1)).findById(productId);
		verify(this.mockPurchaseRepo, times(1)).save(newPurchase);
	}
	
	@Test
	void testAdd_Failure_ProductNotFound() {
		int productId = 6;
		PurchaseRequest createRequest = new PurchaseRequest(productId, this.dummyUser.getId(), 1);
		given(this.productServ.findById(productId)).willReturn(Optional.empty());
		try {
			this.purchaseServ.add(createRequest, this.dummyUser.getId());
			fail("Expected ProductNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
		}
	}

}
