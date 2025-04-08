package com.example.tomatomall.service;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.ProductAmount;
import com.example.tomatomall.po.Specification;
import com.example.tomatomall.repository.ProductAmountRepository;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.repository.SpecificationRepository;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.SpecificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductAmountRepository productAmountRepository;
    @Autowired
    SpecificationRepository specificationRepository;


    public List<ProductVO> getAllProduct(){//完成了
        List<Product> ls=productRepository.findAll();
        List<ProductVO> ans=new ArrayList<>();

        for(Product tem:ls){
            ProductVO t=tem.toVO();
            List<SpecificationVO> sp=new ArrayList<>();
            for(Specification s:specificationRepository.findByProductId(tem.getId())){
                sp.add(s.toVO());
            }
            t.setSpecifications(sp);
            ans.add(t);
        }
        return ans;
    }

    public ProductVO getProduct(Integer id){//完成了
        ProductVO p=productRepository.findById(id).get().toVO();
        List<SpecificationVO> sp=new ArrayList<>();
        for(Specification s: specificationRepository.findByProductId(id)){
            sp.add(s.toVO());
        }
        p.setSpecifications(sp);
        return p;
    }

    public Integer getAmount(Integer id){//完成了
        ProductAmount p=productAmountRepository.findByProductId(id);
        if(p==null){
            throw TomatoMallException.ProductNotFound();
        }
        return p.getAmount();
    }

    public Boolean deleteProduct(Integer id){//完成了
        productRepository.delete(productRepository.findById(id).get());
        productAmountRepository.delete(productAmountRepository.findByProductId(id));

        for(Specification tem:specificationRepository.findByProductId(id))
            specificationRepository.delete(tem);
        return true;
    }

    public Boolean createProduct(ProductVO p){//完成了
        if(p.getTitle() ==null || p.getPrice() ==null || p.getRate() ==null)
            throw TomatoMallException.NoEnoughArguments();
        Product tem=p.toPO();
        productRepository.save(tem);//据说这时候id就有了

        productAmountRepository.save(new ProductAmount(tem.getId(),0,0));

        for(SpecificationVO s:p.getSpecifications()){
            Specification sp=s.toPO();
            sp.setProductId(tem.getId());
            specificationRepository.save(sp);
        }

        return true;
    }

    public Boolean updateProduct(ProductVO p) {//完成了
        if(p.getId()==null)
            throw TomatoMallException.NoEnoughArguments();

        Product tem=productRepository.findById(Integer.parseInt(p.getId())).get();

        if(p.getTitle()!=null)
            tem.setTitle(p.getTitle());
        if(p.getPrice()!=null)
            tem.setPrice(p.getPrice());
        if(p.getRate()!=null)
            tem.setRate(p.getRate());
        if(p.getDescription()!=null)
            tem.setDescription(p.getDescription());
        if(p.getCover()!=null)
            tem.setCover(p.getCover());
        if(p.getDetail()!=null)
            tem.setDetail(p.getDetail());

        productRepository.save(tem);
        for(Specification s:specificationRepository.findByProductId(Integer.parseInt(p.getId()))){
            specificationRepository.delete(s);
        }
        for(SpecificationVO s:p.getSpecifications()){
            specificationRepository.save(s.toPO());
        }
        return true;
    }

    public Boolean updateAmount(Integer id,Integer amount){//完成
        if(amount<0)
            throw TomatoMallException.InvaildProductAmount();
        ProductAmount p=productAmountRepository.findByProductId(id);
        if(p==null)
            throw TomatoMallException.ProductNotFound();
        p.setAmount(amount);
        productAmountRepository.save(p);
        return true;
    }
}



