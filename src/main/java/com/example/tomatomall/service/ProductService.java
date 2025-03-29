package com.example.tomatomall.service;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.util.ProductSet;
import com.example.tomatomall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductVO> getAllProduct(){
        List<Product> ls=productRepository.findAll();
        List<ProductVO> ans=new ArrayList<>();

        for(Product tem:ls){
            if(tem.specifications!=null){
                tem.specifications.productId= tem.getId();
            }
            ans.add(tem.toVO());
        }
        return ans;
    }

    public ProductVO getProduct(Integer id){
        Product p=productRepository.findById(id).get();
        if(p.getSpecifications()!=null){
            p.specifications.productId=p.getId();
        }
        return p.toVO();
    }

    public Integer getAmount(Integer id){
        Product p=productRepository.findById(id).get();
        return p.getAmount();
    }

    public Boolean deleteProduct(Integer id){
        productRepository.delete(productRepository.findById(id).get());
        return true;
    }

    public Boolean createProduct(String title, Double price, Float rate, String description, String cover, String detail, String item, String value
    ){
        Product tem=productRepository.findByTitle(title);
        if(tem!=null)
            throw TomatoMallException.DuplicateProduct();
        Product p=new Product();
        p.setTitle(title);
        p.setPrice(price);
        p.setRate(rate);
        if(description!=null)
            p.setDescription(description);
        if(cover!=null)
            p.setCover(cover);
        if(detail!=null)
            p.setDetail(detail);
        if(item !=null && value !=null){
            ProductSet t=new ProductSet(item,value,0);//不能得知插入数据库时候的编号
            p.setSpecifications(t);
        }
        productRepository.save(p);
        return true;
    }

    public Boolean updateProduct(String title, Double price, Float rate, String description, String cover, String detail, String item, String value){
        Product p=productRepository.findByTitle(title);
        if(p==null) {
            throw TomatoMallException.ProductNotFound();
        }

        p.setPrice(price);
        p.setRate(rate);
        if(description!=null)
            p.setDescription(description);
        if(cover!=null)
            p.setCover(cover);
        if(detail!=null)
            p.setDetail(detail);
        if(item !=null && value !=null){
            ProductSet t=new ProductSet(item,value,0);//不能得知插入数据库时候的编号
            p.setSpecifications(t);
        }
        productRepository.save(p);
        return true;
    }

    public Boolean updateAmount(Integer id,Integer amount){
        Product p=productRepository.findById(id).get();
        p.setAmount(amount);
        return true;
    }
}



