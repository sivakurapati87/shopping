'use strict';

App.controller('ProceedToCheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
//	customerItem = {};
	$rootScope.shippingCharges = 75.00;
	
	 //find the sum of all the items
   $scope.totalCost = function(){
 	  if($rootScope.rsAddedCartItemList){
 		  $scope.totalAmount = 0;
 	  angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
 		$scope.totalAmount = parseFloat($scope.totalAmount)+parseFloat(obj.total)+parseFloat($rootScope.shippingCharges);
		}); 
 	$scope.totalAmount = Math.round($scope.totalAmount * 100) / 100;
 	  return $scope.totalAmount;
 	  }
   };
   
   
   $scope.onChangeItemQuantity = function(customerItem){
	   customerItem.errorMsg = false;
		if(customerItem.quantity && customerItem.minQuantityToPurchase && parseInt(customerItem.quantity) >= parseInt(customerItem.minQuantityToPurchase)){
			customerItem.total = parseFloat(customerItem.mrp * customerItem.quantity).toFixed(2);
		}else{
			customerItem.errorMsg = true;
		}
	}
	

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
