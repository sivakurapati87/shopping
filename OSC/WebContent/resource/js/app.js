'use strict';

var App = angular.module('myApp',['ui.router','ui.bootstrap',"angucomplete-alt","ngGrid","ImageCropper","nvd3","jkuri.timepicker","angular-img-cropper"]);

App.run(['$rootScope','$state','$http', function ($rootScope,$state,$http) {
    $rootScope.$on('$stateChangeStart', function (event) {
        if(!$rootScope.loginUserObj){
        	var response = $http.get(constants.localhost_port+constants.service_context+"/LoginController/getLoggedInPersonInfo");
      		response.success(function(data) {
      			$rootScope.loginUserObj = data;
      		});
      		response.error(function() {
            	  console.error('Could not Perform well');
            	  $state.go("login");
              });
        }
    });
}]);

App.config(['$compileProvider', function ($compileProvider) {
    $compileProvider.aHrefSanitizationWhitelist(/^\s*(|blob|):/);
}]);

App.directive('validNumber', function() {
    return {
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
        if(!ngModelCtrl) {
          return; 
        }

        ngModelCtrl.$parsers.push(function(val) {
          if (angular.isUndefined(val)) {
              var val = '';
          }
          
          var clean = val.replace(/[^-0-9\.]/g, '');
          var negativeCheck = clean.split('-');
			var decimalCheck = clean.split('.');
          if(!angular.isUndefined(negativeCheck[1])) {
              negativeCheck[1] = negativeCheck[1].slice(0, negativeCheck[1].length);
              clean =negativeCheck[0] + '-' + negativeCheck[1];
              if(negativeCheck[0].length > 0) {
              	clean =negativeCheck[0];
              }
              
          }
            
          if(!angular.isUndefined(decimalCheck[1])) {
              decimalCheck[1] = decimalCheck[1].slice(0,2);
              clean =decimalCheck[0] + '.' + decimalCheck[1];
          }

          if (val !== clean) {
            ngModelCtrl.$setViewValue(clean);
            ngModelCtrl.$render();
          }
          return clean;
        });

        element.bind('keypress', function(event) {
          if(event.keyCode === 32) {
            event.preventDefault();
          }
        });
      }
    };
  });

App.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
});

App.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise("/login")
	
	$stateProvider
	.state('login', {
		url: "/login",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/login.html',
	        		controller : "LoginController"
	            }
	        }
	}).state('item', {
		url: "/item",
		views: {
			'leftMenu':{
				templateUrl: 'views/leftmenu.html',
        		controller : "LeftMenuController"
			},
            'content': {
            	templateUrl: 'views/item.html',
        		controller : "ItemController"
            }
        }
	})
	.state('create_item', {
		url: "/create_item/:id",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_item.html',
	        		controller : "CreateItemController"
	            }
	        }
	})
	.state('item_field_name', {
		url: "/item_field_name",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/itemFieldName.html',
	        		controller : "ItemFieldNameController"
	            }
	        }
	})
	.state('create_user', {
		url: "/create_user",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_user.html',
	        		controller : "CreateUserController"
	            }
	        }
	})
	
	.state('create_category', {
		url: "/create_category",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_category.html',
	        		controller : "CategoryController"
	            }
	        }
	})
	.state('create_category_division', {
		url: "/create_category_division",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_category_division.html',
	        		controller : "CategoryDivisionController"
	            }
	        }
	})
	.state('create_subcategory', {
		url: "/create_subcategory",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_subcategory.html',
	        		controller : "SubCategoryController"
	            }
	        }
	}).state('customer_orders', {
		url: "/customer_orders",
		views: {
			'leftMenu':{
				templateUrl: 'views/leftmenu.html',
        		controller : "LeftMenuController"
			},
            'content': {
            	templateUrl: 'views/customer_orders.html',
        		controller : "CustomerOrdersController"
            }
        }
	})
}]);

