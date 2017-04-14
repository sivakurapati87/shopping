'use strict';

App.controller('CategoryDivisionController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.categoryDivisionObj = {};
	
	$scope.clear = function(){
		$scope.categoryDivisionObj = {};
		var categoryDivisionId = null;
		$scope.$broadcast('angucomplete-alt:changeInput', 'categoryNameId', {});
	}	
	
	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/CategoryDivisionController/saveOrUpdate",$scope.categoryDivisionObj);
		response.success(function(data) {
			$state.go($state.current, {}, {reload: true});
		});
		response.error(function() {
      	  console.error('Could not save or update');
        });

	}
	
	
	var categoryDivisions = [];
	var categoryDivisionId = null;
	
	$scope.onload = function(){
		$scope.getAllCategoryDivisions();
		$scope.getAllCategories();
	}

	$scope.getAllCategoryDivisions = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryDivisionController/getAllCategoryDivisions");
  		response.success(function(data) {
  			categoryDivisions = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editDivision = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryDivisionController/getCategoryDivisionById/"+categoryDivisionId);
  		response.success(function(data) {
  			$scope.categoryDivisionObj = data;
  			if($scope.categoryList){
  			for(var i=0;i<$scope.categoryList.length;i++){
  				if($scope.categoryList[i].id == data.categoryId){
  					$scope.categoryObj = $scope.categoryList[i];
  					 $scope.$broadcast('angucomplete-alt:changeInput', 'categoryNameId', $scope.categoryObj);
  				}
  				}
  			}
   		 
//  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	
	$scope.getAllCategories = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryController/getAllCategories");
  		response.success(function(data) {
  			$scope.categoryList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	

	  //This function is to get the selected value from auto population
  $scope.selectedCategoryNameAction = function(selected) {
      if (selected) {
//    	  $rootScope.transactionData.customerId = selected.title;
//    	  $scope.getCustomerInfoById();
      	$scope.categoryDivisionObj.categoryId = selected.originalObject.id;
      } else {
        console.log('cleared');
      }
    };

	$scope.deleteCategoryDivision = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryDivisionController/deleteCategoryDivisionById/"+categoryDivisionId);
  		response.success(function(data) {
  			$state.go($state.current, {}, {reload: true});
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#categoryDivisionTableId').bootstrapTable({
		data : categoryDivisions,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'name',
	        sortable:true,
	        title: 'Category Divison Name'
	    }, {
	        field: 'categoryName',
	        sortable:true,
	        title: 'Category Name'
	    }, {
	        field: 'strCreatedBy',
	        sortable:true,
	        title: 'createdBy'
	    },
		{
			field : 'actions',
			title : 'Actions',
			align : 'left',
			sortable : false,
			events : window.operateEvents,
			formatter : actionFormatter
		}],
	});
	}
	
	function actionFormatter(value, row,
			index) {
				return [
						'<a class="editDivision actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="removeDivision actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editDivision' : function(e, value,
				row, index) {
			categoryDivisionId = row.id;
			angular.element("#categoryDivisionControllerId").scope().editDivision();

		},

		'click .removeDivision' : function(e,
				value, row, index) {
			categoryDivisionId = row.id;
			angular.element("#categoryDivisionControllerId").scope().deleteCategoryDivision();
		}
		
	}

	
	$scope.onload();
	
	
	
	
     /* $scope.obj={};
      
      $scope.init = function(){
    	$scope.obj.phone = "9603631480";
    	$scope.obj.email = "kssrao87@gmail.com";
    	$scope.obj.strTotalAmount = "100";
    	$scope.obj.firstname = "siva";
    	
			$http.get(constants.localhost_port+"/"+constants.service_context+'/'+constants.LookUpController+'/hashCode').success(function(data) {
				$scope.hashJson = data;
			}).error(function() {
	      	  console.error('Could not get All Manufacturer List');
	        });
      };
      $scope.init();*/
}]);
