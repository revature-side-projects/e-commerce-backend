package com.revature;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repositories.ProductRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.ProductService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ProductServiceTest {

    @BeforeEach
    public void registrationBeforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    static ProductRepository pr;

    @Autowired
    static ProductService ps;

    @Test
    public void findAllTest(){
        ps = new ProductService(pr);
        List<Product> lp = new ArrayList<>();
        Product u = new Product(0,1,1.0,"a","b","c", "d", false, false);
        lp.add(u);
        when((pr).findAll()).thenReturn(lp);
        List<Product> test = ps.findAll();
        assertEquals(0,test.get(0).getId(), "pass");
        assertEquals(1,test.get(0).getQuantity(), "pass");
        assertEquals(1.0,test.get(0).getPrice(), "pass");
        assertEquals("a",test.get(0).getDescription(), "pass");
        assertEquals("b",test.get(0).getImage(), "pass");
        assertEquals("c",test.get(0).getName(), "pass");
    }

    @Test
    public void findByIdTest(){
        ps = new ProductService(pr);
        Optional<Product> u = Optional.of(new Product(0,1,1.0,"a","b","c", "d",false, false));
        when((pr).findById(anyInt())).thenReturn(u);
        Optional<Product> test = ps.findById(0);
        //verify(pr).findById(anyInt());
        assertEquals(0,test.get().getId(), "pass");
        assertEquals(1,test.get().getQuantity(), "pass");
        assertEquals(1.0,test.get().getPrice(), "pass");
        assertEquals("a",test.get().getDescription(), "pass");
        assertEquals("b",test.get().getImage(), "pass");
        assertEquals("c",test.get().getName(), "pass");
    }

//    @Test
//    public void saveProductTest(){
//        ps = new ProductService(pr);
//        Product u = new Product(0,1,1.0,"a","b","c", false, false);
//        when((pr).save(any())).thenReturn(u);
//        Product test = ps.save(u);
//        //verify(pr).saveAndFlush(any());
//        assertEquals(0,test.getId(), "pass");
//        assertEquals(1,test.getQuantity(), "pass");
//        assertEquals(1.0,test.getPrice(), "pass");
//        assertEquals("a",test.getDescription(), "pass");
//        assertEquals("b",test.getImage(), "pass");
//        assertEquals("c",test.getName(), "pass");
//    }

    @Test
    public void saveAllProductTest(){
        ps = new ProductService(pr);
        List<ProductInfo> pl = new ArrayList<>();
        List<Product> lp = new ArrayList<>();
        Product u = new Product(0,1,1.0,"a","b","c", "d", false, false);
        lp.add(u);
        when((pr).saveAll(any())).thenReturn(lp);
        List<Product> test = ps.saveAll(lp,pl);
        verify(pr).saveAll(any());
        assertEquals(0,test.get(0).getId(), "pass");
        assertEquals(1,test.get(0).getQuantity(), "pass");
        assertEquals(1.0,test.get(0).getPrice(), "pass");
        assertEquals("a",test.get(0).getDescription(), "pass");
        assertEquals("b",test.get(0).getImage(), "pass");
        assertEquals("c",test.get(0).getName(), "pass");
    }

    @Test
    public void deleteTest(){
        ps = new ProductService(pr);
        doNothing().when(pr).deleteById(anyInt());
        ps.delete(0);
        verify(pr).deleteById(anyInt());
    }

    @Test
    public void saveAndFlushProductTest(){
        ps = new ProductService(pr);
        Product u = new Product(1,0,1.0,"a","b","c", "d", false, false);
        Product test = new Product(1,1,2.0,"d","e","f", "g", false, false);
        when((pr).saveAndFlush(any())).thenReturn(u);
        test = ps.update(u);
        //verify(pr).saveAndFlush(any());
        assertEquals(1,test.getId(), "pass");
        assertEquals(0,test.getQuantity(), "pass");
        assertEquals(1.0,test.getPrice(), "pass");
        assertEquals("a",test.getDescription(), "pass");
        assertEquals("b",test.getImage(), "pass");
        assertEquals("c",test.getName(), "pass");
    }




}
