'use strict';

var App = angular.module('myApp',['ui.router','ui.bootstrap',"angucomplete-alt","ngGrid","ImageCropper","nvd3","jkuri.timepicker","angular-img-cropper"]);

App.run(['$rootScope','$state','$http', function ($rootScope,$state,$http) {
   /* $rootScope.$on('$stateChangeStart', function (event) {
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
    });*/
}]);

App.directive('scrollTrigger', function($window) {
    return {
        link : function(scope, element, attrs) {
            var offset = parseInt(attrs.threshold) || 0;
            var e = jQuery(element[0]);
            var doc = jQuery(document);
            angular.element(document).bind('scroll', function() {
                if (doc.scrollTop() + $window.innerHeight + offset > e.offset().top) {
                    scope.$apply(attrs.scrollTrigger);
                }
            });
        }
    };
});

App.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise("/home")
	
	$stateProvider
	.state('home', {
		url: "/home",
			views: {
				'header':{
					templateUrl: 'views/header.html',
	        		controller : "HeaderController"
				},
	            'content': {
	            	templateUrl: 'views/home.html',
	        		controller : "HomeController"
	            }
	        }
	}).state('view_item', {
		url: "/view_item/:id",
		views: {
			'header':{
				templateUrl: 'views/header.html',
        		controller : "HeaderController"
			},
            'content': {
            	templateUrl: 'views/view_item.html',
        		controller : "ItemController"
            }
        }
	}).state('view_all_items', {
		url: "/view_all_items/:id",
		views: {
			'header':{
				templateUrl: 'views/header.html',
        		controller : "HeaderController"
			},
            'content': {
            	templateUrl: 'views/view_all_items.html',
        		controller : "AllItemsController"
            }
        }
	}).state('checkout', {
		url: "/checkout",
		views: {
			'header':{
				templateUrl: 'views/header.html',
        		controller : "HeaderController"
			},
            'content': {
            	templateUrl: 'views/checkout.html',
        		controller : "CheckoutController"
            }
        }
	}).state('proceedToCheckout', {
		url: "/proceedToCheckout",
		views: {
			'header':{
				templateUrl: 'views/header.html',
        		controller : "HeaderController"
			},
            'content': {
            	templateUrl: 'views/proceedToCheckout.html',
        		controller : "ProceedToCheckoutController"
            }
        }
	})
	
}]);

