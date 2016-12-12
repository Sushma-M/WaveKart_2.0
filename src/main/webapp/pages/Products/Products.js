Application.$controller("ProductsPageController", ["$scope", function($scope) {
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

    };






    $scope.SelectedProductDataonSuccess = function(variable, data) {
        $scope.Variables.BreadCrumb_Var.setItem(1, {
            "PageName": data[0].category,
            "Link": "#/" + data[0].category
        });
        $scope.Variables.BreadCrumb_Var.setItem(2, {
            "PageName": data[0].productName
        });

    };



    $scope.LV_Cart_InfoonSuccess = function(variable, data) {

        if (data.length) {
            $scope.Variables.LV_UpdateCartItems_Quantity.updateRecord();
        } else {
            $scope.Variables.Insert_into_Cart.createRecord();
        }
    };

}]);