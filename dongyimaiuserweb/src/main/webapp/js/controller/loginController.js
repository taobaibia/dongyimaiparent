app.controller('loginController',function ($scope,loginService) {

    $scope.getLoginName = function () {
        loginService.loginName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        );
    }

})