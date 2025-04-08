package com.example.tomatomall.serviceImpl;

import com.example.tomatomall.exception.TomatoMallException;
import com.example.tomatomall.po.Account;
import com.example.tomatomall.po.Carts;
import com.example.tomatomall.po.Product;
import com.example.tomatomall.service.CartsService;
import com.example.tomatomall.util.SecurityUtil;
import com.example.tomatomall.RRVO.AddCartResultVO;
import com.example.tomatomall.RRVO.CartResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.tomatomall.repository.CartsRepository;
import com.example.tomatomall.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartsServiceImpl implements CartsService {

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SecurityUtil securityUtil;

    // 新增商品到当前用户购物车
    @Override
    public AddCartResultVO addCart(String productId, Integer quantity) {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw  TomatoMallException.invalidUserId();
        }
        Product product = productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(TomatoMallException::ProductNotFound);
        Carts existingCartItem = cartsRepository.findByAccountAndProduct(
                currentUser,
                product
        );
        Integer stock=product.getAmount();
        if(stock==null||stock==0){
            throw TomatoMallException.productSoldOut();
        }
        if(stock < quantity) {
            throw TomatoMallException.notEnoughStock();
        }

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartsRepository.save(existingCartItem);
        }else{
            Carts carts = new Carts();
            carts.setAccount(currentUser);
            carts.setProduct(product);
            carts.setQuantity(quantity);
            cartsRepository.save(carts);
            existingCartItem = carts;
        }
        AddCartResultVO resultVO = new AddCartResultVO();
        resultVO.setCartItemId(String.valueOf(existingCartItem.getCartItemId()));
        resultVO.setProductId(productId);
        resultVO.setTitle(product.getTitle());
        resultVO.setPrice(product.getPrice());
        resultVO.setDescription(product.getDescription());
        resultVO.setCover(product.getCover());
        resultVO.setQuantity(existingCartItem.getQuantity());
        return resultVO;
    }

    // 从当前用户购物车删除商品
    @Override
    public Boolean deleteCart(String productId) {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw TomatoMallException.invalidUserId();
        }
        Product product=productRepository.findById(Integer.valueOf(productId))
                .orElseThrow(TomatoMallException::ProductNotFound);
        Carts cartItem=cartsRepository.findByAccountAndProduct(
                currentUser,
                product
        );
        if(cartItem==null){
            throw TomatoMallException.cartItemNotFound();
        }
        cartsRepository.delete(cartItem);
        return true;
    }

    // 更新购物车商品数量
    @Override
    public Boolean updateCart(String cartItemId, Integer quantity) {
        Account currentUser=securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        if (currentUser.getId() == null) {
            throw TomatoMallException.invalidUserId();
        }
        Product product=productRepository.findById(Integer.valueOf(cartItemId))
                .orElseThrow(TomatoMallException::ProductNotFound);
        Carts cartItem=cartsRepository.findByAccountAndProduct(
                currentUser,
                product
        );
        if(cartItem==null){
            throw TomatoMallException.cartItemNotFound();
        }
        Integer stock=product.getAmount();
        if(stock==null||stock==0){
            throw TomatoMallException.productSoldOut();
        }
        if(stock<quantity){
            throw TomatoMallException.notEnoughStock();
        }
        cartItem.setQuantity(quantity);
        cartsRepository.save(cartItem);
        return true;
    }

    // 获取当前用户购物车
    @Override
    public CartResultVO getCart() {
        Account currentUser = securityUtil.getCurrentUser();
        if (currentUser == null) {
            throw TomatoMallException.notLogin();
        }
        List<Carts> cartItems = cartsRepository.findByAccount(currentUser);
        if (cartItems == null) {
            throw TomatoMallException.getItemListFailed();
        }
        List<AddCartResultVO> result = new ArrayList<>();
        double totalAmount = 0;
        int total = 0;
        for (Carts cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(TomatoMallException::ProductNotFound);
            AddCartResultVO item = new AddCartResultVO();
            item.setCartItemId(String.valueOf(cartItem.getCartItemId()));
            item.setProductId(String.valueOf(cartItem.getProduct().getId()));
            item.setTitle(product.getTitle());
            item.setPrice(product.getPrice());
            item.setDescription(product.getDescription());
            item.setQuantity(cartItem.getQuantity());
            item.setCover(product.getCover());

            result.add(item);
            totalAmount += cartItem.getQuantity()*product.getPrice();
            total ++;
        }

        CartResultVO cartResult = new CartResultVO();
        cartResult.setItems(result);
        cartResult.setTotalAmount(totalAmount);
        cartResult.setTotal(total);
        return cartResult;
    }
}
