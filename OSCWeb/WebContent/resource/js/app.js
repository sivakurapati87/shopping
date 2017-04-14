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


App.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
	
	$urlRouterProvider.otherwise("/home")
	
	$stateProvider
	.state('home', {
		url: "/home",
			views: {
	            'content': {
	            	templateUrl: 'views/home.html',
	        		controller : "HomeController"
	            }
	        }
	})
	
}]);

