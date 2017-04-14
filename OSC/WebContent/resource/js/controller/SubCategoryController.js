'use strict';

App.controller('SubCategoryController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.subCategoryObj = {};
	
	
	$scope.clear = function(){
		$scope.subCategoryObj = {};
		var subCategoryId = null;
		$scope.$broadcast('angucomplete-alt:changeInput', 'categoryDivisionNameId', {});
	}
	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/SubCategoryController/saveOrUpdate",$scope.subCategoryObj);
		response.success(function(data) {
			$state.go($state.current, {}, {reload: true});
		});
		response.error(function() {
      	  console.error('Could not save or update');
      	  $state.go("login");
        });

	}
	
	
	var subCategories = [];
	var subCategoryId = null;
	
	$scope.onload = function(){
		$scope.getAllSubCategories();
		$scope.getAllCategoryDivisions();
	}

	$scope.getAllSubCategories = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/getAllSubCategories");
  		response.success(function(data) {
  			subCategories = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editSubCategory = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/getSubCategoryById/"+subCategoryId);
  		response.success(function(data) {
  			$scope.subCategoryObj = data;
  			if($scope.categoryDivisionList){
  			for(var i=0;i<$scope.categoryDivisionList.length;i++){
  				if($scope.categoryDivisionList[i].id == data.categoryDivisionId){
  					$scope.categoryDivisionObj = $scope.categoryDivisionList[i];
  					 $scope.$broadcast('angucomplete-alt:changeInput', 'categoryDivisionNameId', $scope.categoryDivisionObj);
  				}
  				}
  			}
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	
	$scope.getAllCategoryDivisions = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryDivisionController/getAllCategoryDivisions");
  		response.success(function(data) {
  			$scope.categoryDivisionList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	

	  //This function is to get the selected value from auto population
  $scope.selectedCategoryDivisionNameAction = function(selected) {
      if (selected) {
//    	  $rootScope.transactionData.customerId = selected.title;
//    	  $scope.getCustomerInfoById();
      	$scope.subCategoryObj.categoryDivisionId = selected.originalObject.id;
      } else {
        console.log('cleared');
      }
    };

	$scope.deleteSubCategory = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/deleteSubCategoryById/"+subCategoryId);
  		response.success(function(data) {
  			$state.go($state.current, {}, {reload: true});
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#subCategoryTableId').bootstrapTable({
		data : subCategories,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'name',
	        sortable:true,
	        title: 'SubCategory Name'
	    }, {
	        field: 'categoryDivisionName',
	        sortable:true,
	        title: 'Category Division Name'
	    }, {
	        field: 'isUniqueProduct',
	        title: 'Is Unique Product',
	        sortable:true,
	        formatter : uniqueProductFormatter
	    }, {
	        field: 'showItemsInHomePage',
	        title: 'Show Items In Home Page',
	        sortable:true,
	        formatter : showHomePageFormatter
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
						'<a class="editSubCategory actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="removeSubCategory actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}
	
	function showHomePageFormatter(value, row,
			index) {
				return [
						'<label>'+(row.showItemsInHomePage?"Yes":"No")+'</label>',
						 ]
						.join('');
			}

	
	function uniqueProductFormatter(value, row,
			index) {
				return [
						'<label>'+(row.isUniqueProduct?"Yes":"No")+'</label>',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editSubCategory' : function(e, value,
				row, index) {
			subCategoryId = row.id;
			angular.element("#subCategoryControllerId").scope().editSubCategory();

		},

		'click .removeSubCategory' : function(e,
				value, row, index) {
			subCategoryId = row.id;
			angular.element("#subCategoryControllerId").scope().deleteSubCategory();
		}
		
	}

	
	$scope.onload();
}]);
