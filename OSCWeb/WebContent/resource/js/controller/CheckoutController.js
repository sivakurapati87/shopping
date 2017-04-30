'use strict';

App.controller('CheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	
	
	$scope.getHashCodeAction = function()
	{
//		$scope.customerJson = {totalPurchase:210,firstName:$rootScope.rsCustomerJson,emailId:'kssrao87@gmail.com',phoneNumber:9603631480};
		
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/getHashKeyWithTransactionNumber",$scope.rsCustomerJson);
  		response.success(function(data) {
//  			alert(JSON.stringify(data));
  			$scope.customerJson = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	
	$scope.checkoutAction = function(){
		if($rootScope.rsAddedCartItemList){
		for(var i=0;i<$rootScope.rsAddedCartItemList.length;i++){
			$scope.obj = {itemId:$rootScope.rsAddedCartItemList[i].itemId,subTotal:$rootScope.rsAddedCartItemList[i].total,quantity:$rootScope.rsAddedCartItemList[i].quantity,
					deliveryCharges:constants.DELIVERY_CHARGES,
					customerId: $rootScope.rsCustomerJson.id,divBlob:$rootScope.rsAddedCartItemList[i].divBlob,
					custPhotoJsonList:$rootScope.rsAddedCartItemList[i].custPhotoJsonList,txnId:$scope.customerJson.txnId};
		}	
		
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/saveCustomerOrders",$scope.obj);
  		response.success(function(data) {
//  			alert(JSON.stringify(data));
//  			$scope.customerJson = data;
  			$('#payuFormId').submit();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
	}
	
	$scope.getHashCodeAction();
}]);
