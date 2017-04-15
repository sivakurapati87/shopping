'use strict';

App.controller('HeaderController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

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
	
	
	$scope.getAllCategoriesWithSubCategory();
}]);
