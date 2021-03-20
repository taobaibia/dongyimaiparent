app.service('cartService',function ($http) {

    //写订单模块
    this.submit = function (entity) {
        return $http.post('../order/add.do',entity);
    }

    this.findAddrByUserId = function () {
        return $http.get('../address/findAddrByUserId.do');
    }

    this.findCartList = function () {
        return $http.get('../cart/findCartList.do');
    }

    this.addGoodsToCart = function (itemId,num) {
        return $http.get('../cart/addGoodsToCart.do?itemId='+itemId+"&num="+num);
    }

    this.sum = function (cartList) {
        var totalValue = {totalNum:0,totalMoney:0.00};

        for(var i=0;i<cartList.length;i++){
            var cart = cartList[i];
            for(var j=0;j<cart.orderItemList.length;j++){
                var item = cart.orderItemList[j];
                totalValue.totalNum += item.num;
                totalValue.totalMoney += item.totalFee;
            }
        }

        return totalValue;
    }

})