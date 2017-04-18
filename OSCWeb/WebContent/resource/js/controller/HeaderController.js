'use strict';

App.controller('HeaderController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {
	if(!$rootScope.rsAddedCartItemList){
	$rootScope.rsAddedCartItemList = [];
	}
	
	$scope.getAllCategoriesWithSubCategory = function(){
		if(!$rootScope.navigationDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/allCategoriesWithSubCategory");
  		response.success(function(data) {
  			if(data){
  				$rootScope.searchDataList = [];
  			$rootScope.navigationDataList = data;
  			$.each(data, function (i, val) {
  			  $.each(val, function(innerKey, innerValue) {
  				  for(var j=0;j<innerValue.length;j++){
  					  var obj = innerValue[j];
  					  obj.name = obj.name+' in '+ obj.categoryName;
  					$rootScope.searchDataList.push(obj);
  				  }
  			  });
  			});
  			}
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	  //This function is to get the selected value from auto population
	  $scope.selectedCategoryDivisionNameAction = function(selected) {
	      if (selected) {
//	    	  $rootScope.transactionData.customerId = selected.title;
//	    	  $scope.getCustomerInfoById();
	      	$scope.subCategoryId = selected.originalObject.id;
	      } else {
	        console.log('cleared');
	      }
	    };

	
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
    	if(subCategoryId){
		$state.go("view_all_items",{id:subCategoryId});
    	}
	}
    
	$scope.getAllCategoriesWithSubCategory();
}]);
