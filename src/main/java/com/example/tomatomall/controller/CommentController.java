package com.example.tomatomall.controller;

import com.example.tomatomall.service.ProductService;
import com.example.tomatomall.vo.CommentVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public Response addComment(@RequestBody CommentVO c){
        return Response.buildSuccess(productService.addComment(c));
    }

    @GetMapping("/{productId}")
    public Response getById(@PathVariable(value = "productId") Integer productId){
        return Response.buildSuccess(productService.findByProductId(productId));
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestBody CommentVO c){
        return Response.buildSuccess(productService.deleteComment(c));
    }
}
