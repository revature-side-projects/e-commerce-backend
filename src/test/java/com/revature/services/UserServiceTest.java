package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository mockUserRepo;

	@InjectMocks
	UserService uServ;

	User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyUser = new User(1, "dummy.admin@revature.com", "asdf", "Dummy", "User", "Admin", null, null, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the dummy user
		this.dummyUser = null;
	}

	@Test
	void testFindByCredentials() {
		String email = this.dummyUser.getEmail();
		String password = this.dummyUser.getPassword();
		given(this.mockUserRepo.findByEmailAndPassword(email, password)).willReturn(Optional.of(this.dummyUser));

		User expected = this.dummyUser;
		User actual = this.uServ.findByCredentials(email, password).get();

		assertEquals(expected, actual);
		verify(this.mockUserRepo, times(1)).findByEmailAndPassword(email, password);
	}
	
	@Test
	void testFindByCredentials_Failure_UserNotFound() {
		String email = "user@coolmail.edu";
		String password = "Alligator3";
		given(this.mockUserRepo.findByEmailAndPassword(email, password)).willReturn(Optional.empty());
		try {
			this.uServ.findByCredentials(email, password);
			fail("Expected UserNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}

	@Test
	void testFindById() {
		int id = this.dummyUser.getId();
		given(this.mockUserRepo.findById(id)).willReturn(Optional.of(this.dummyUser));

		User expected = this.dummyUser;
		User actual = this.uServ.findById(id).get();

		assertEquals(expected, actual);
		verify(this.mockUserRepo, times(1)).findById(id);
	}
	
	@Test
	void testFindById_Failure_UserNotFound() {
		int id = 1337;
		given(this.mockUserRepo.findById(id)).willReturn(Optional.empty());
		try {
			this.uServ.findById(id);
			fail("Expected UserNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}

	@Test
	void testSave() {
		given(this.mockUserRepo.save(this.dummyUser)).willReturn(this.dummyUser);

		User expected = this.dummyUser;
		User actual = this.uServ.save(this.dummyUser);

		assertEquals(expected, actual);
		verify(this.mockUserRepo, times(1)).save(this.dummyUser);
	}

	@Test
	void testFindByEmail() {
		String email = this.dummyUser.getEmail();
		given(this.mockUserRepo.findByEmail(email)).willReturn(Optional.of(this.dummyUser));

		User expected = this.dummyUser;
		User actual = this.uServ.findByEmail(email).get();

		assertEquals(expected, actual);
		verify(this.mockUserRepo, times(1)).findByEmail(email);
	}
	
	@Test
	void testFindByEmail_Failure_UserNotFound() {
		String email = "user@coolmail.gov";
		given(this.mockUserRepo.findByEmail(email)).willReturn(Optional.empty());
		try {
			this.uServ.findByEmail(email);
			fail("Expected UserNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}

}
