'use strict';

App.controller('HomeController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	$scope.getAllHomeProducts = function(){
		if(!$rootScope.homeProductDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getAllHomeProducts");
  		response.success(function(data) {
  			$rootScope.homeProductDataList = data;
  			$scope.getAllPromoCodeImages();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.getAllPromoCodeImages = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/getAllPromoCodeImages");
  		response.success(function(data) {
  			$rootScope.promoCodeImages = data;
  			if($rootScope.promoCodeImages){
  				$rootScope.slideToList = [];
  				for(var i=4;i<$rootScope.promoCodeImages.length;i++){
  					$rootScope.slideToList.push(i);
  				}
  				
  			}
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	$scope.viewProduct = function(itemId){
		$state.go("view_item",{id:itemId});
	}
	
	$scope.viewAllItems = function(subCategoryId){
		$state.go("view_all_items",{id:subCategoryId});
	}
	
	$scope.getAllHomeProducts();
}]);
