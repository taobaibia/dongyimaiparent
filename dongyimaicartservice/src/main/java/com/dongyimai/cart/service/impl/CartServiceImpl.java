package com.dongyimai.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dongyimai.cart.service.CartService;
import com.dongyimai.entity.Cart;
import com.dongyimai.mapper.TbItemMapper;
import com.dongyimai.pojo.TbItem;
import com.dongyimai.pojo.TbOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(timeout = 60000)
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public Cart searchCartBySellerId(List<Cart> cartList,String sellerId){
        for (Cart cart : cartList) {
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    public TbOrderItem createOrderItem(TbItem item,Integer num){

        if(num<=0){
            throw new RuntimeException("数量非法");
        }

        TbOrderItem orderItem = new TbOrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        return orderItem;
    }

    public TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList,TbItem item){
            for (TbOrderItem orderItem : orderItemList) {
                System.out.println("order Item boolean : ");
                System.out.println("orderItem Id : " + orderItem.getItemId().longValue());
                System.out.println("Item Id : " + item.getId().longValue());
                System.out.println( orderItem.getItemId().longValue() == item.getId().longValue());

                if(orderItem.getItemId().longValue() == item.getId().longValue()){
                    return orderItem;
                }
            }
        return null;
    }

    @Override
    public List<Cart> addGoodsToCart(List<Cart> cartList, Long itemId, Integer num) {

        //1、获取该商品
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品状态无效");
        }

        //2、获取该商家
        String sellerId = item.getSellerId();

        //3、循环遍历 cartList 判断该用户购物车是否含有该商家购物车
        Cart cart = searchCartBySellerId(cartList,sellerId);

        if(cart != null){
         System.out.println("cart order list size : " + cart.getOrderItemList().size());
        }
        //3.1 该用户的购物车 没有该商家的购物车
        if(cart == null){
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());

            TbOrderItem orderItem = createOrderItem(item,num);

            List<TbOrderItem> orderItemList = new ArrayList<TbOrderItem>();
            orderItemList.add(orderItem);
            cart.setOrderItemList(orderItemList);

            System.out.println("cart order Item list size : " + cart.getOrderItemList().size());


            //将新建的购物车 装入到 用户购物车列表中
            cartList.add(cart);

            //3.2 如果cart不为空   取出该商家列表
        }else{

            System.out.println("cart order Item list size : " + cart.getOrderItemList().size());

            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(),item);

            if(orderItem == null){
                TbOrderItem orderItem1 = createOrderItem(item,num);
                cart.getOrderItemList().add(orderItem1);
            }else{
                //修改原购物车的商品数量
                orderItem.setNum(orderItem.getNum().intValue()+num.intValue());
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum()).multiply(orderItem.getPrice()));

                //如果该商品数量小于0 将该商品从购物车中移除
                if(orderItem.getNum()<=0){
                    cart.getOrderItemList().remove(orderItem);
                }
                //如果该商家购物车的商品数量都 小于0 那么将该商家购物车从用户购物车中移除
                if(cart.getOrderItemList().size()<=0){
                    cartList.remove(cart);
                }
            }
        }
        return cartList;
    }

    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>)redisTemplate.boundHashOps("cartList").get(username);
        if(cartList == null){
            cartList = new ArrayList<>();
        }
        System.out.println("从redis中取购物车...");
        return cartList;
    }

    @Override
    public void saveCartToRedis(String username, List<Cart> cartList) {
        System.out.println("存入redis购物车...");
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {

        for (Cart cart : cartList2) {
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                cartList1 = addGoodsToCart(cartList1,orderItem.getItemId(),orderItem.getNum());
            }
        }
        
        return cartList1;
    }
}
