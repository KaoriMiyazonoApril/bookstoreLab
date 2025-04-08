package com.example.tomatomall.controller;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



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
    public Response updateProduct(@RequestBody ProductVO p){
        return Response.buildSuccess(productService.updateProduct(p));
    }

    @PostMapping()
    public Response createProduct(@RequestBody ProductVO p){
        return Response.buildSuccess(productService.createProduct(p));
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable(value = "id") Integer id){
        return Response.buildSuccess(productService.deleteProduct(id));
    }

    @PatchMapping("/stockpile/{productId}")
    public Response updateProductAmount(@PathVariable(value = "productId") Integer id,@RequestBody Map<String, Integer> updates){
        if(!updates.containsKey("amount")) {
            throw TomatoMallException.NoEnoughArguments();
        }
        return Response.buildSuccess(productService.updateAmount(id,updates.get("amount")));
    }

    @GetMapping("/stockpile/{productId}")
    public Response getProductAmount(@PathVariable(value = "productId") Integer id){
        return Response.buildSuccess(productService.getAmount(id));
    }
}
