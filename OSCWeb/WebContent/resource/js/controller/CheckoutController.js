'use strict';

App.controller('CheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	$scope.isPromoCodeApplied = false;
	
	 //find the sum of all the items
	   $scope.totalCost = function(){
	 	  if($rootScope.rsAddedCartItemList){
	 		 $rootScope.totalAmount = 0;
	 	  angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
	 		 $rootScope.totalAmount = parseFloat($rootScope.totalAmount)+parseFloat(obj.total);
			}); 
	 	 $rootScope.totalAmount = $rootScope.totalAmount+parseFloat($rootScope.shippingCharges);
	 	 if($scope.promoCodeReducedAmount){
	 		$rootScope.totalAmount = $rootScope.totalAmount - parseFloat($scope.promoCodeReducedAmount);
	 	 }
	 	 $rootScope.totalAmount = Math.round($rootScope.totalAmount * 100) / 100;
	 	 
	 	$rootScope.rsCustomerJson.totalPurchase = $rootScope.totalAmount;
	 	  return $scope.totalAmount;
	 	  }
	   };
	
	   
		$scope.applyPromoCode = function()
		{
			$scope.msg = null;
			$scope.success = false;
//			$scope.customerJson = {totalPurchase:210,firstName:$rootScope.rsCustomerJson,emailId:'kssrao87@gmail.com',phoneNumber:9603631480};
			if(!$scope.isPromoCodeApplied){
				$scope.purchasedAmount= $rootScope.totalAmount - parseFloat($rootScope.shippingCharges);
				$scope.purchasedAmount = Math.round($scope.purchasedAmount * 100) / 100;
			var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/applyPromoCode?customerId="+$scope.rsCustomerJson.id+
					'&promoCode='+$scope.promoCode+'&totalAmount='+$scope.purchasedAmount);
	  		response.success(function(data) {
//	  			alert(JSON.stringify(data));
	  			$scope.msg = data.msg;
	  			$scope.success = data.success;
	  			if(data.success){
	  			$scope.isPromoCodeApplied = true;
	  			$scope.promoCodeId = data.promoCodeId;
	  			$scope.amountToReduce = data.amountToReduce;
	  			$scope.promoCodeReducedAmount = ($scope.purchasedAmount * $scope.amountToReduce)/100
	  			$scope.promoCodeReducedAmount = Math.rount($scope.promoCodeReducedAmount*100)/100;
	  			}
	  		});
	  		response.error(function() {
	        	  console.error('Could not Perform well');
	        	  $state.go("login");
	          });
			}else{
				$scope.msg = 'You have already applied one Promo Code';
			}
		}

		
		
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
		if($scope.isPromoCodeApplied){
			$scope.getHashCodeAction();
		}
		
		if($rootScope.rsAddedCartItemList){
		for(var i=0;i<$rootScope.rsAddedCartItemList.length;i++){
			$scope.obj = {itemId:$rootScope.rsAddedCartItemList[i].itemId,subTotal:$rootScope.rsAddedCartItemList[i].total,quantity:$rootScope.rsAddedCartItemList[i].quantity,
					deliveryCharges:constants.DELIVERY_CHARGES,
					providedNames:$rootScope.rsAddedCartItemList[i].providedNames,
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
