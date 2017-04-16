'use strict';

App.controller('AllItemsController', ['$scope','$http','$rootScope','$state','$stateParams', function($scope,$http,$rootScope,$state,$stateParams) {

	$scope.firstResult = 0;
	
	$scope.getAllProductsBySubCategory = function(){
		if($stateParams.id){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemsBySubCategoryId?subCategoryId="+$stateParams.id+"&firstResult="+$scope.firstResult);
  		response.success(function(data) {
  			$rootScope.itemList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.viewMoreProducts = function(){
		if($stateParams.id){
	  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemsBySubCategoryId?subCategoryId="+$stateParams.id+"&firstResult="+(($scope.firstResult)+20));
	  		response.success(function(data) {
	  			$rootScope.itemList = data;
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
	
	
	$scope.getAllProductsBySubCategory();
}]);
