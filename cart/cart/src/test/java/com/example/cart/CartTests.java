package com.example.cart;

import com.example.cart.controller.ShoppingCartController;
import com.example.cart.model.Product;
import com.example.cart.repository.ProductRepository;
import com.example.cart.repository.UserRepository;
import com.example.cart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import sun.security.acl.PrincipalImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class CartTests {

    private ProductRepository mockProductRepository;
    private MockMvc mockMvc;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        //如果视图名与路径名一致，需要做以下配置
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        //mock ProductRepository
        mockProductRepository = mock(ProductRepository.class);
        when(mockProductRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Product()
                        .setId(1L)
                        .setDescription("该商品用于测试")
                        .setName("测试商品")
                        .setPrice(BigDecimal.TEN)
                        .setQuantity(1)
                ));
        mockMvc = MockMvcBuilders.standaloneSetup(
                new ShoppingCartController(shoppingCartService, userRepository, mockProductRepository))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    @WithMockUser(username = "arnold")
    void testShoppingCart() throws Exception {
        //测试展示控制器
        mockMvc.perform(get("/shoppingCart"))
                .andExpect(status().isOk())
                .andExpect(view().name("/shoppingCart"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "arnold")
    void testAddProduct() throws Exception {
        //测试新增商品控制器
        mockMvc.perform(get("/shoppingCart/addProduct/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/shoppingCart"))
                .andExpect(model().attribute("total", "10"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "arnold")
    void testCheckout() throws Exception {
        //测试结算控制器
        mockMvc.perform(get("/shoppingCart/addProduct/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(get("/shoppingCart/checkout")
                .principal(new PrincipalImpl("arnold")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
