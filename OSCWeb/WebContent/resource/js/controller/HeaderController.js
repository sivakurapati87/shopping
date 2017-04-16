'use strict';

App.controller('HeaderController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {
	if(!$rootScope.rsAddedCartItemList){
	$rootScope.rsAddedCartItemList = [];
	}
	
	$scope.getAllCategoriesWithSubCategory = function(){
		if(!$rootScope.navigationDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/allCategoriesWithSubCategory");
  		response.success(function(data) {
  			$rootScope.navigationDataList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	 //find the sum of all the items
    $scope.totalCost = function(){
  	  if($rootScope.rsAddedCartItemList){
  		  $scope.totalAmount = 0;
  	  angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
  		$scope.totalAmount = parseFloat($scope.totalAmount)+parseFloat(obj.total);
		}); 
  	$scope.totalAmount = Math.round($scope.totalAmount * 100) / 100;
  	  return $scope.totalAmount;
  	  }
    };
	
    $scope.viewAllItems = function(subCategoryId){
		$state.go("view_all_items",{id:subCategoryId});
	}
    
	$scope.getAllCategoriesWithSubCategory();
}]);
