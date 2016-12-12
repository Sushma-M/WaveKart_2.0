Application.$controller("MyCartPageController", ["$scope", function($scope) {
    "use strict";

    /* perform any action on widgets/variables within this block */
    $scope.onPageReady = function() {
        /*
         * variables can be accessed through '$scope.Variables' property here
         * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
         * $scope.Variables.loggedInUser.getData()
         *
         * widgets can be accessed through '$scope.Widgets' property here
         * e.g. to get value of text widget named 'username' use following script
         * '$scope.Widgets.username.datavalue'
         */
        $scope.productItems;
    };

    $scope.CheckoutClick = function($event, $isolateScope) {
        $scope.Variables.LV_InsertOrders.setInput('orderDate', Date.now());
    };





    $scope.Cart_DataonSuccess = function(variable, data) {
        $scope.productItems = data;
    };



    function successFn(orderId, i) {
        var dataObj = $scope.Variables.Cart_Data.dataSet.data[i];
        if (i < $scope.Variables.Cart_Data.dataSet.data.length) {
            $scope.Variables.LV_InsertOrderLineItems.setInput("productId", dataObj.productId);
            $scope.Variables.LV_InsertOrderLineItems.setInput("quantity", dataObj.productQuantity);
            $scope.Variables.LV_InsertOrderLineItems.setInput("pricePerUnit", dataObj.currentPrice);
            $scope.Variables.LV_InsertOrderLineItems.setInput("productLineAmount", dataObj.totalPrice);
            $scope.Variables.LV_InsertOrderLineItems.setInput("orderId", orderId);
            $scope.Variables.LV_InsertOrderLineItems.createRecord({}, function() {
                successFn(orderId, ++i);
            });
        } else {
            $scope.Variables.goToPage_Orders.invoke();
        }
    }

    $scope.LV_InsertOrdersonSuccess = function(variable, data) {
        successFn(data.orderId, 0);
    };


}]);