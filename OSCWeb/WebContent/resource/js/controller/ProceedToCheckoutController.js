'use strict';

App.controller('ProceedToCheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
//	customerItem = {};
	$rootScope.shippingCharges = constants.DELIVERY_CHARGES;
	
	 //find the sum of all the items
   $scope.totalCost = function(){
 	  if($rootScope.rsAddedCartItemList){
 		 $rootScope.totalAmount = 0;
 	  angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
 		 $rootScope.totalAmount = parseFloat($rootScope.totalAmount)+parseFloat(obj.total)+parseFloat($rootScope.shippingCharges);
		}); 
 	 $rootScope.totalAmount = Math.round($rootScope.totalAmount * 100) / 100;
 	  return $scope.totalAmount;
 	  }
   };
   
   
   $scope.onChangeItemQuantity = function(customerItem){
	   customerItem.errorMsg = false;
		if(customerItem.quantity && customerItem.minQuantityToPurchase && parseInt(customerItem.quantity) >= parseInt(customerItem.minQuantityToPurchase)){
			customerItem.total = parseFloat(customerItem.eachQuantityMrp * customerItem.quantity).toFixed(2);
		}else{
			customerItem.errorMsg = true;
		}
	}
	
   $scope.deleteItem = function(productId){
	   for(var i=0;i<$rootScope.rsAddedCartItemList.length;i++){
		   if(productId == $rootScope.rsAddedCartItemList[i].itemId){
			   $rootScope.rsAddedCartItemList.splice(i,1);   
		   }
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
