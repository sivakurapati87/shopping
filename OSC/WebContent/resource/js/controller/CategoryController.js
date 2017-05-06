'use strict';

App.controller('CategoryController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.categoryObj = {};
	
	
	$scope.clear = function(){
		$scope.categoryObj = {};
		var categoryId = null;
	}	
	
	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/CategoryController/saveOrUpdate",$scope.categoryObj);
		response.success(function(data) {
			$scope.categoryObj = {};
			$state.go($state.current, {}, {reload: true});
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
      	$state.go("login");
        });

	}
	
	
	var categories = [];
	var categoryId = null;
	
	$scope.onload = function(){
		$scope.getAllCategories();
	}

	$scope.getAllCategories = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryController/getAllCategories");
  		response.success(function(successData) {
  			categories = successData;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editCategory = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryController/getCategoryById/"+categoryId);
  		response.success(function(data) {
  			$scope.categoryObj = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	$scope.deleteCategory = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/CategoryController/deleteCategoryById/"+categoryId);
  		response.success(function(data) {
  			$scope.categoryObj = data;
  			$state.go($state.current, {}, {reload: true});
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#categoryTableId').bootstrapTable({
		data : categories,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'name',
	        title: 'Category Name',
	        sortable : true
	    },{
	        field: 'strCreatedBy',
	        title: 'Created By',
	        sortable : true
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
						'<a class="editCategory actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="removeCategory actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editCategory' : function(e, value,
				row, index) {
			categoryId = row.id;
			angular.element("#categoryControllerId").scope().editCategory();

		},

		'click .removeCategory' : function(e,
				value, row, index) {
			categoryId = row.id;
			angular.element("#categoryControllerId").scope().deleteCategory();
		}
		
	}

	
	$scope.onload();
	
}]);
