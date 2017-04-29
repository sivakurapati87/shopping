'use strict';

App.controller('ProceedToCheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	$scope.customerItem = {};
	
	

	/*$scope.checkoutAction = function()
	{
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/getHashKeyWithTransactionNumber",$stateParams.id);
  		response.success(function(data) {
  			alert(JSON.stringify(data));
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}*/

}]);
