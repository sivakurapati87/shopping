'use strict';

App.controller('CheckoutController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout','$log', function($scope,$http,$rootScope,$state,$stateParams,$timeout,$log) {
	$scope.isPromoCodeApplied = false;
	$scope.promoCodeAmt = 0;
	$scope.isReduceAmount = false;
	$scope.addedCartItemList = angular.copy($rootScope.rsAddedCartItemList);
	 //find the sum of all the items
	   $scope.totalCost = function(){
	 	  if($scope.addedCartItemList){
	 		 $rootScope.totalAmount = 0;
	 	  angular.forEach($scope.addedCartItemList, function(obj, key) {
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
			$scope.successfull = 'no';
//			$scope.customerJson = {totalPurchase:210,firstName:$rootScope.rsCustomerJson,emailId:'kssrao87@gmail.com',phoneNumber:9603631480};
			$scope.promoCodeReducedAmount = 0;
			if(!$scope.isPromoCodeApplied){
				$scope.purchasedAmount= $rootScope.totalAmount - parseFloat($rootScope.shippingCharges);
				$scope.purchasedAmount = Math.round($scope.purchasedAmount * 100) / 100;
			var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/applyPromoCode?customerId="+$scope.rsCustomerJson.id+
					'&promoCode='+$scope.promoCode+'&totalAmount='+$scope.purchasedAmount);
	  		response.success(function(data) {
//	  			alert(JSON.stringify(data));
	  			if(data.success){
	  				$scope.successfull = 'yes';
	  				$scope.isPromoCodeApplied = true;
		  			$scope.promoCodeId = data.promoCodeId;
		  			$scope.amountToReduce = data.amountToReduce;
		  			$scope.msg = data.msg;
	  				if(data.subCategoryId && data.subCategoryId != 0){
	  					$scope.isReduceAmount = true;
	  					//Do the caculations on for(var i=0;i<$scope.addedCartItemList.length;i++){
	  					for(var i=0;i<$scope.addedCartItemList.length;i++){
	  						for(var j=0;j<data.itemIds.length;j++){
	  							if($scope.addedCartItemList[i].itemId == data.itemIds[j]){
	  								$scope.addedCartItemList[i].withoutPromocodeValue = angular.copy($scope.addedCartItemList[i].total);
	  								var reducedAmt = parseFloat((Math.round(($scope.addedCartItemList[i].total * $scope.amountToReduce)/100)*100)/100);
	  								$scope.addedCartItemList[i].total = parseFloat($scope.addedCartItemList[i].total) - parseFloat(reducedAmt);
	  								$scope.promoCodeAmt = parseFloat($scope.promoCodeAmt)+ parseFloat(reducedAmt);
	  							}
	  						}
	  					}
	  				}else{
			  			
			  			$scope.promoCodeReducedAmount = ($scope.purchasedAmount * $scope.amountToReduce)/100
			  			$scope.promoCodeReducedAmount = Math.round($scope.promoCodeReducedAmount*100)/100;
			  			$scope.promoCodeAmt =angular.copy($scope.promoCodeReducedAmount);
	  				}
	  			}else{
	  				$scope.msg = data.msg;
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
		
		if($scope.addedCartItemList){
			$scope.listObj =[];
		for(var i=0;i<$scope.addedCartItemList.length;i++){
			$log.info($scope.addedCartItemList[i].total);
			$scope.obj = {itemId:$scope.addedCartItemList[i].itemId,subTotal:$scope.addedCartItemList[i].total,quantity:$scope.addedCartItemList[i].quantity,
					deliveryCharges:constants.DELIVERY_CHARGES,
					providedNames:$scope.addedCartItemList[i].providedNames,
					customerId: $rootScope.rsCustomerJson.id,divBlob:$scope.addedCartItemList[i].divBlob,
					isReduceAmount:$scope.isReduceAmount,
					promoCodeId : $scope.promoCodeId,
					promoCodeReducedAmount : $scope.promoCodeAmt,
					custPhotoJsonList:$scope.addedCartItemList[i].custPhotoJsonList,txnId:$scope.customerJson.txnId};
			$scope.listObj.push($scope.obj);
		}	
		
		var response = $http.post(constants.localhost_port+constants.service_context+"/CustomerController/saveCustomerOrders",$scope.listObj);
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
