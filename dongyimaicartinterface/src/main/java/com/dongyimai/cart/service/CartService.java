package com.dongyimai.cart.service;

import com.dongyimai.entity.Cart;
import com.dongyimai.pojo.TbOrderItem;

import java.util.List;

public interface CartService {

    public List<Cart> addGoodsToCart(List<Cart> cartList, Long itemId, Integer num);

    public List<Cart> findCartListFromRedis(String username);

    public void saveCartToRedis(String username, List<Cart> cartList);

    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2);

}
