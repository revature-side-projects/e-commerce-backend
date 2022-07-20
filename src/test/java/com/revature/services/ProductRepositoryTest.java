package com.revature.services;

import com.revature.models.Category;
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
    private Category category;


    @BeforeEach
    public void setup() {
        reset(mockProductRepository);
        sut = new ProductService(mockProductRepository);
    }

    @Test
    public void test_findAll_returnsListOfProduct_providedRepositoryReturnsProducts() {

        //  Given:  requested
        Category category = new Category("Sun");
        List<Product> mockProducts = Arrays.asList(
                new Product(category,"name 1", "description-1", 10.00, "image-1", "image-11"),
                new Product(category,"name 2", "description-2", 20.00, "image-2", "image-22"),
                new Product(category,"name 3", "description-3", 30.00, "image-3", "image-33"),
                new Product(category,"name 4", "description-4", 40.00, "image-4", "image-44"),
                new Product(category,"name 5", "description-5", 50.00, "image-5", "image-55"),
                new Product(category,"name 6", "description-6", 60.00, "image-6", "image-66")
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
        Product expected = new Product(category, "name 1", "description-1", 10.00, "image-1", "name-11");

        sut.save(expected);

        //  When:   response is actual or true
        Product actual = expected;

        //  Then: verify
        assertThat(actual).isEqualTo(expected);
    }
}