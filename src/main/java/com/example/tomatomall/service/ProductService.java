package com.example.tomatomall.service;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.po.ProductAmount;
import com.example.tomatomall.po.Specification;
import com.example.tomatomall.repository.ProductAmountRepository;
import com.example.tomatomall.repository.ProductRepository;
import com.example.tomatomall.repository.SpecificationRepository;
import com.example.tomatomall.vo.ProductAmountVO;
import com.example.tomatomall.vo.ProductVO;
import com.example.tomatomall.vo.SpecificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


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

    public ProductAmountVO getAmount(Integer id){//完成了
        ProductAmount p=productAmountRepository.findByProductId(id);
        if(p==null){
            throw TomatoMallException.ProductNotFound();
        }
        return p.toVO();
    }

    public String deleteProduct(Integer id){//完成了
        productRepository.delete(productRepository.findById(id).get());
        productAmountRepository.delete(productAmountRepository.findByProductId(id));

        for(Specification tem:specificationRepository.findByProductId(id))
            specificationRepository.delete(tem);
        return "删除成功";
    }

    public ProductVO createProduct(ProductVO p){//完成了
        if(p.getTitle() ==null || p.getPrice() ==null || p.getRate() ==null)
            throw TomatoMallException.NoEnoughArguments();
        Product tem=p.toPO();
        productRepository.save(tem);//据说这时候id就有了
        System.out.println(tem.getId());
        productAmountRepository.save(new ProductAmount(tem.getId(),0,0));
        if(p.getSpecifications()!=null){
            for(SpecificationVO s:p.getSpecifications()){
                Specification sp=s.toPO();
                sp.setProductId(tem.getId());
                specificationRepository.save(sp);
            }
        }
        p.setId(tem.getId().toString());
        return p;
    }

    public String updateProduct(ProductVO p) {//完成了
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
        if(p.getSpecifications()!=null)
            for(SpecificationVO s:p.getSpecifications()){
                specificationRepository.save(s.toPO());
            }
        return "更新成功";
    }

    public String updateAmount(Integer id,Integer amount){//完成
        if(amount<0)
            throw TomatoMallException.InvaildProductAmount();
        ProductAmount p=productAmountRepository.findByProductId(id);
        if(p==null)
            throw TomatoMallException.ProductNotFound();
        p.setAmount(amount);
        productAmountRepository.save(p);
        return "调整库存成功";
    }

    //锁库存
    public void lockStock(Integer productId, Integer quantity) {
        ProductAmount productAmount = productAmountRepository.findByProductId(productId);
        if (productAmount == null) {
            throw TomatoMallException.ProductNotFound();
        }
        if (quantity < 0) {
            throw new TomatoMallException("锁定库存数量不能为负数");
        }
        if (productAmount.getAmount() < quantity) {
            throw new TomatoMallException("锁定库存数量不能超过可用库存");
        }
        // 锁定库存
        productAmount.setFrozen(productAmount.getFrozen() + quantity);
        // 减少可用库存
        productAmount.setAmount(productAmount.getAmount() - quantity);
        productAmountRepository.save(productAmount);
    }

    //释放库存
    public void releaseStock(Integer productId, Integer quantity) {
        ProductAmount productAmount = productAmountRepository.findByProductId(productId);
        if (productAmount == null) {
            throw TomatoMallException.ProductNotFound();
        }
        if (quantity < 0) {
            throw new TomatoMallException("释放库存数量不能为负数");
        }
        if (productAmount.getFrozen() < quantity) {
            throw new TomatoMallException("释放库存数量不可超过锁定库存");
        }
        // 释放锁定库存
        productAmount.setFrozen(productAmount.getFrozen() - quantity);
        // 增加可用库存
        productAmount.setAmount(productAmount.getAmount() + quantity);
        productAmountRepository.save(productAmount);
    }
    //减少库存
    public void reduceStock(Integer productId, Integer quantity) {
        ProductAmount productAmount = productAmountRepository.findByProductId(productId);
        if (productAmount == null) {
            throw TomatoMallException.ProductNotFound();
        }
        if (quantity < 0) {
            throw new TomatoMallException("减少库存数量不能为负数");
        }
        if (productAmount.getFrozen() < quantity) {
            throw new TomatoMallException("减少库存数量不可超过锁定库存");
        }
        // 减少锁定库存
        productAmount.setFrozen(productAmount.getFrozen() - quantity);
        productAmountRepository.save(productAmount);
    }
}



