app.controller('cartController',function ($scope,cartService) {

    $scope.submitOrder = function(){
        //讲js选择的地址信息赋予 传值对象
        $scope.order.receiverAreaName = $scope.addr.address;
        $scope.order.receiverMobile = $scope.addr.mobile;
        $scope.order.receiver = $scope.addr.contact;

        cartService.submit($scope.order).success(
            function (response) {
                if(response.success){
                    if($scope.order.paymentType == "1"){
                        location.href = "pay.html";
                    }else{
                        location.href = "paysuccess.html";
                    }

                }else{
                    alert(response.message);
                }
            }
        );
    }
    
    
    $scope.order = {paymentType:'1'};

    $scope.selectPayType = function(type){
        $scope.order.paymentType = type;
    }


    $scope.selectAddr = function(address){
        $scope.addr = address;
    }

    $scope.isSelected = function(address){
        if($scope.addr == address){
            return true;
        }else{
            return false;
        }
    }

    $scope.findAddrByUserId = function(){
        cartService.findAddrByUserId().success(
            function (response) {
                $scope.addrList = response;
                //默认地址选择
                for(var i=0;i<$scope.addrList.length;i++){
                    if($scope.addrList[i].isDefault == '1'){
                        $scope.addr = $scope.addrList[i];
                        break;
                    }
                }
            }
        );
    }
    
    $scope.findCartList = function () {
        cartService.findCartList().success(
            function (response) {
                $scope.cartList = response;
                $scope.totalValue=cartService.sum($scope.cartList);
            }
        );
    }
    
    $scope.addGoodsToCart = function (itemId,num) {
        cartService.addGoodsToCart(itemId,num).success(
            function (response) {
                if(response.success){
                    $scope.findCartList();
                }else{
                    alert(response.message);
                }
            }
        );
    }
    
})