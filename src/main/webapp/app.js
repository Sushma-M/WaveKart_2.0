Application.run(function($rootScope) {
    "use strict";
    /* perform any action on the variables within this block(on-page-load) */
    $rootScope.onAppVariablesReady = function() {
        /*
         * variables can be accessed through '$rootScope.Variables' property here
         * e.g. $rootScope.Variables.staticVariable1.getData()
         */
    };

    $rootScope.updateBread = function(pageName) {
        $rootScope.Variables.BreadCrumb_Var.setItem(1, {
            "PageName": pageName,
            "Link": "#/" + pageName
        });
        $rootScope.Variables.BreadCrumb_Var.removeItem(2);
    }
    $rootScope.setStatusData = function(data, value) {
        var stat = value ? '' : 'Available'
        data['productInventory.status'] = {
            'value': stat
        };
    }

    $rootScope.loginVariableonSuccess = function(variable, data) {
        if (_.includes($rootScope.Variables.loggedInUser.dataSet.roles, 'admin')) {
            $rootScope.Variables.goToPage_Admin.invoke();
        } else if (_.includes($rootScope.Variables.loggedInUser.dataSet.roles, 'user')) {
            location.reload();
        }
    };

});