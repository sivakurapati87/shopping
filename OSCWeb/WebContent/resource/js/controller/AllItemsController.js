'use strict';

App.controller('AllItemsController', ['$scope','$http','$rootScope','$state','$stateParams', function($scope,$http,$rootScope,$state,$stateParams) {

	$scope.firstResult = 0;
	 
	
	$scope.getAllProductsBySubCategory = function(){
		if($stateParams.id){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemsBySubCategoryId?subCategoryId="+$stateParams.id+"&firstResult="+$scope.firstResult);
  		response.success(function(data) {
  			$scope.itemList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.getNoOfProductsBySubCategory = function(){
		if($stateParams.id){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getNoOfProductsBySubCategory?subCategoryId="+$stateParams.id);
  		response.success(function(data) {
  			$scope.noOfProducts = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.viewMoreProducts = function(){
		if($stateParams.id){
			$scope.firstResult = ($scope.firstResult)+12;
	  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemsBySubCategoryId?subCategoryId="+$stateParams.id+"&firstResult="+$scope.firstResult);
	  		response.success(function(data) {
//	  			$scope.itemList.concat(data);
	  			$scope.itemList.push.apply($scope.itemList, data);
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
	$scope.getNoOfProductsBySubCategory();
}]);
