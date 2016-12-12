Application.$controller("TotalProd_PartialPageController", ["$scope", function($scope) {
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


    $scope.LV_ProductDataonBeforeUpdate = function(variable, data) {
        var cat = $scope.activePageName == 'AllProducts' ? '' : $scope.activePageName;
        data.category = {
            'value': cat
        }
        $scope.$root.setStatusData(data, _.get($scope, ['pageParams', 'GetTotalStock']));
        $scope.Variables.LV_ProductData.orderBy = _.get($scope, ['pageParams', 'Sorting']);

    };

}]);