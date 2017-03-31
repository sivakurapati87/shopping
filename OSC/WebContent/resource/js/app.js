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
	})
	.state('create_item', {
		url: "/create_item",
			views: {
				'leftMenu':{
					templateUrl: 'views/leftmenu.html',
	        		controller : "LeftMenuController"
				},
	            'content': {
	            	templateUrl: 'views/create_item.html',
	        		controller : "ItemController"
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
	})
}]);

