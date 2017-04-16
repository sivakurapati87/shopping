'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state','$stateParams', function($scope,$http,$rootScope,$state,$stateParams) {
	
	$scope.customerItem = {};
	
	$scope.getItemInfo = function(){
		if($stateParams.id){
		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemById/"+$stateParams.id);
  		response.success(function(data) {
  			$scope.itemObj = data;
  			$scope.customerItem = data;
  			
  			if($scope.itemObj && $scope.itemObj.discount && $scope.itemObj.discount != 0){
  				$scope.itemObj.mrp = $scope.itemObj.mrp - ($scope.itemObj.mrp * $scope.itemObj.discount) / 100;
  				$scope.itemObj.mrp = parseFloat($scope.itemObj.mrp).toFixed(2);
  				$scope.customerItem.mrp =$scope.itemObj.mrp;
  				$scope.customerItem.total = $scope.itemObj.mrp;
  				if($scope.itemObj.minQuantityToPurchase){
  					$scope.itemObj.mrp = parseFloat($scope.itemObj.mrp / $scope.itemObj.minQuantityToPurchase).toFixed(2);//This is for each quantity 
  				}
  					
  				
  				
  				$scope.customerItem.quantity  = $scope.itemObj.minQuantityToPurchase;
  			}
  			$scope.selectedPositions = $scope.itemObj.itemCroppedDimensionJsonList;
  			$scope.specificationList =$scope.itemObj.itemFieldValueJsonList;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	}

	
	$scope.onChangeItemQuantity = function(){
		$scope.errorMsg = false;
		if($scope.customerItem.quantity && $scope.itemObj.minQuantityToPurchase && parseInt($scope.customerItem.quantity) >= parseInt($scope.itemObj.minQuantityToPurchase)){
			$scope.customerItem.total = parseFloat($scope.itemObj.mrp * $scope.customerItem.quantity).toFixed(2);
		}else{
			$scope.errorMsg = true;
		}
	}

	$scope.addToCartPerformAction = function(){
		var isItemExists = false;
		if($rootScope.rsAddedCartItemList){
		angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
			if(obj.id == $scope.customerItem.id)
			{
				isItemExists = true;
			}
		});
		if(!isItemExists){
			$rootScope.rsAddedCartItemList.push($scope.customerItem);
		}
		}
		$('#addToCartPopupId').modal('hide');
	}
	
	
	

	
	$scope.addToCartAction = function(){
		angular.element('#addToCartPopup').trigger('click');
	}
	$scope.closeDialog = function(){
		$('#addToCartPopupId').modal('hide');
	}
	
	$scope.getItemInfo();
}]);
