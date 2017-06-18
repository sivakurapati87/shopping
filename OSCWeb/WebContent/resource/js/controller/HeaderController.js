'use strict';

App.controller('HeaderController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {
	if(!$rootScope.rsAddedCartItemList){
	$rootScope.rsAddedCartItemList = [];
	}
	$rootScope.stateName = $state.current.name;
	$rootScope.year = (new Date()).getFullYear();

	$scope.getAllCategoriesWithSubCategory = function(){
		if(!$rootScope.navigationDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/allCategoriesWithSubCategory");
  		response.success(function(data) {
  			if(data){
  				$scope.getAllSearchableWords();
  			$rootScope.navigationDataList = data;
  			/*$.each(data, function (i, val) {
  			  $.each(val, function(innerKey, innerValue) {
  				  for(var j=0;j<innerValue.length;j++){
  					  var obj = angular.copy(innerValue[j]);
  					  obj.name = obj.name+' in '+ obj.categoryName;
  					$rootScope.searchDataList.push(obj);
  				  }
  			  });
  			});*/
  			}
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
		}
  	}
	
	$scope.getAllSearchableWords = function(){
		if(!$rootScope.searchDataList){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getAllSearchableWords");
  		response.success(function(data) {
  			if(data){
  				$rootScope.searchDataList = data;
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
	      	$scope.searchName = selected.title;
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
    
    $scope.searchAction = function(){
    	if($scope.searchName){
    		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getPageBySearchValue/"+$scope.searchName);
      		response.success(function(data) {
      			if(data){
      				if(data.subCategoryId && data.subCategoryId > 0){
      					$state.go("view_all_items",{id:data.subCategoryId});
      				}
      				if(data.itemId && data.itemId > 0){
      					$state.go("view_item",{id:data.itemId});
      				}
      			}
      		});
      		response.error(function() {
            	  console.error('Could not Perform well');
            	  $state.go("login");
              });
    		
		
    	}
	}
    
	$scope.getAllCategoriesWithSubCategory();
}]);
