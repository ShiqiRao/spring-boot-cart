package com.example.cart.controller;

import com.example.cart.model.Product;
import com.example.cart.repository.ProductRepository;
import com.example.cart.util.Pager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping(value = {"/home", "/"})
    public ModelAndView home(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        //实际分页的参数与页面显示的页数不同，需要在页数的基础上减1
        int evalPage = page - 1;
        //分页查询
        Page<Product> products = productRepository.findAll(PageRequest.of(evalPage, 5));
        //使用工具类从分页查询中提取分页组件所需的数据
        Pager pager = new Pager(products);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
