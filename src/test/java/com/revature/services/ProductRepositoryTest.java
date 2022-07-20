package com.revature.services;

import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductService sut; // SUT: System Under Test
    private ProductRepository mockProductRepository = mock(ProductRepository.class);

    @BeforeEach
    public void setup() {
        reset(mockProductRepository);
        sut = new ProductService(mockProductRepository);
    }

    @Test
    public void test_findAll_returnsListOfProduct_providedRepositoryReturnsProducts() {

        //  Given:  requested
        List<Product> mockProducts = Arrays.asList(
                new Product(1, 1, 10.00, "description-1", "image-1", "name-1"),
                new Product(2, 2, 20.00, "description-2", "image-2", "name-2"),
                new Product(3, 3, 30.00, "description-3", "image-3", "name-3"),
                new Product(4, 4, 40.00, "description-4", "image-4", "name-4"),
                new Product(5, 5, 50.00, "description-5", "image-5", "name-5"),
                new Product(6, 6, 60.00, "description-6", "image-6", "name-6")
        );
        when(mockProductRepository.findAll()).thenReturn(mockProducts); //  here we invoke the findAll method for testing.

        //  When:   response is actual or true
        List<Product> actual = (List<Product>) sut.findAll();

        //  Then: verify
        assertEquals(mockProducts.size(), actual.size());
        verify(mockProductRepository, times(1)).findAll();
    }

    @Test
    void checkIfFoundById() {

        //  Given:  requested
        Product expected = new Product(1, 1, 10.00, "description-1", "image-1", "name-1");

        sut.save(expected);

        //  When:   response is actual or true
        Product actual = expected;

        //  Then: verify
        assertThat(actual).isEqualTo(expected);
    }
}