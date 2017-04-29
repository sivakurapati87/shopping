'use strict';

App.controller('CheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	
	
	$scope.checkoutAction = function()
	{
		$scope.customerJson = {totalPurchase:210,firstName:'siva',emailId:'kssrao87@gmail.com',phoneNumber:9603631480};
		
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/getHashKeyWithTransactionNumber",$scope.customerJson);
  		response.success(function(data) {
  			alert(JSON.stringify(data));
  			$scope.customerJson = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	
	$scope.checkoutAction();
}]);
