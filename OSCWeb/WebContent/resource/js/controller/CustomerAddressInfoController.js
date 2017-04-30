'use strict';

App.controller('CustomerAddressInfoController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	$rootScope.rsCustomerJson = {};
	
	$scope.checkoutAction = function()
	{
		$scope.rsCustomerJson = {totalPurchase:210,firstName:'siva',emailId:'kssrao87@gmail.com',phoneNumber:9603631480};
		
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/getHashKeyWithTransactionNumber",$scope.rsCustomerJson);
  		response.success(function(data) {
  			alert(JSON.stringify(data));
  			$scope.rsCustomerJson = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
//        	  $state.go("login");
          });
	}

	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/saveOrUpdate",$rootScope.rsCustomerJson);
		response.success(function(data) {
			$scope.categoryObj = {};
			$state.go("checkout");
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
//      	$state.go("login");
        });
	}

	
	$scope.onChangeEmail = function(){
//		alert($rootScope.rsCustomerJson.emailId);
		var obj = {email:$rootScope.rsCustomerJson.emailId};
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/getCustomerInfoByEmail",obj);
  		response.success(function(data) {
  			alert(JSON.stringify(data));
  			if(data){
  			$rootScope.rsCustomerJson = data;
  			alert(JSON.stringify($rootScope.rsCustomerJson));
  			}
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
//        	  $state.go("login");
          });
	}
}]);
