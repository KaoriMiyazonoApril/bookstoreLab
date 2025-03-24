package com.example.tomatomall.controller;

import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping()
    public Response getAllProducts(){
        return Response.buildSuccess(productService.getAllProduct());
    }

    @GetMapping("/{id}")
    public Response getProduct(@PathVariable(value = "id") Integer id){
        return Response.buildSuccess(productService.getProduct(id));
    }

    @PutMapping()
    public Response updateProduct(@RequestParam(required = true, name = "title") String title, @RequestParam(required = true, name = "price") Double price, @RequestParam(required = true, name = "rate") Float rate, @RequestParam(required = false, name = "description") String description, @RequestParam(required = false, name = "cover") String cover, @RequestParam(required = false, name = "detail") String detail, @RequestParam(required = false, name = "item") String item, @RequestParam(required = false, name = "value") String value){
        return Response.buildSuccess(productService.updateProduct(title, price,rate, description, cover, detail,item, value));
    }

    @PostMapping()
    public Response createProduct(@RequestParam(required = true, name = "title") String title, @RequestParam(required = true, name = "price") Double price, @RequestParam(required = true, name = "rate") Float rate, @RequestParam(required = false, name = "description") String description, @RequestParam(required = false, name = "cover") String cover, @RequestParam(required = false, name = "detail") String detail, @RequestParam(required = false, name = "item") String item, @RequestParam(required = false, name = "value") String value){
        return Response.buildSuccess(productService.createProduct(title, price,rate, description, cover, detail,item, value));
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable(value = "id") Integer id){
        return Response.buildSuccess(productService.deleteProduct(id));
    }

    @PatchMapping("/stockpile/{productId}")
    public Response updateProductAmount(@PathVariable(value = "productId") Integer id){
        return;
    }

    @GetMapping("/stockpile/{productId}")
    public Response getProductAmount(@PathVariable(value = "productId") Integer id){
        return Response.buildSuccess(productService.getAmount(id));
    }
}
