'use strict';

App.controller('HomeController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	$scope.getAllHomeProducts = function(){
		if(!$rootScope.homeProductDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getAllHomeProducts");
  		response.success(function(data) {
  			$rootScope.homeProductDataList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.viewProduct = function(itemId){
		$state.go("view_item",{id:itemId});
	}
	
	$scope.viewAllItems = function(subCategoryId){
		$state.go("view_all_items",{id:subCategoryId});
	}
	
	$scope.getAllHomeProducts();
}]);
