package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.po.Carts;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.vo.AddCartResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tomatomall.repository.CartsRepository;
import com.example.tomatomall.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartsServiceImpl implements CartsService {

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public AddCartResultVO addCart(Integer productId, Integer quantity) {

        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        System.out.println("11111111111111111111111111111111111");
        Product product = productRepository.findById(productId)
                .orElseThrow(TomatoMallException::ProductNotFound);
        System.out.println("2222222222222222222222222222222222222");
        System.out.println(currentUser.getId() + " " + productId + " " + quantity);
        Carts existingCartItem = cartsRepository.
                findByUserIdAndProductId(currentUser.getId(), productId);

        System.out.println("333333333333333333333333333333333333333333333");
        if (existingCartItem != null) {
            System.out.println(111);
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartsRepository.save(existingCartItem);
        }else{
            System.out.println(222);
            Carts carts = new Carts();
            carts.setProductId(productId);
            carts.setQuantity(quantity);
            carts.setUserId(currentUser.getId());
            System.out.println("55555555555555555555555555555555555555555555");
            cartsRepository.save(carts);
            System.out.println("6666666666666666666666666666666666666666666666");
            existingCartItem = carts;
        }

        System.out.println("44444444444444444444444444444444444444444444");
        AddCartResultVO resultVO = new AddCartResultVO();
        resultVO.setCartItemId(existingCartItem.getCartItemId());
        resultVO.setProductId(productId);
        resultVO.setTitle(product.getTitle());
        resultVO.setPrice(product.getPrice());
        resultVO.setDescription(product.getDescription());
        resultVO.setCover(product.getCover());
        resultVO.setQuantity(existingCartItem.getQuantity());
        return resultVO;
    }

    @Override
    public Boolean deleteCart(Integer productId) {
        return true;
    }

    @Override
    public Boolean updateCart(Integer productId, Integer quantity) {
        return true;
    }

    @Override
    public List<AddCartResultVO> getCart() {
        return null;
    }
}
