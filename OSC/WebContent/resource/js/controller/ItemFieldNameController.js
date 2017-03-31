'use strict';

App.controller('ItemFieldNameController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.itemFieldNameObj = {};
	
	
	$scope.clear = function(){
		$scope.itemFieldNameObj = {};
		var itemFieldNameId = null;
	}	
	
	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/FieldNameController/saveOrUpdate",$scope.itemFieldNameObj);
		response.success(function(data) {
			$scope.itemFieldNameObj = {};
			$state.go($state.current, {}, {reload: true});
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
      	$state.go("login");
        });

	}
	
	
	var categories = [];
	var itemFieldNameId = null;
	
	$scope.onload = function(){
		$scope.getAllItemFieldNames();
	}

	$scope.getAllItemFieldNames = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/FieldNameController/getAllItemFieldNames");
  		response.success(function(successData) {
  			categories = successData;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editFieldName = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/FieldNameController/getFieldNameById/"+itemFieldNameId);
  		response.success(function(data) {
  			$scope.itemFieldNameObj = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	$scope.deleteItemFieldName = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/FieldNameController/deleteFieldNameById/"+itemFieldNameId);
  		response.success(function(data) {
  			$scope.itemFieldNameObj = data;
  			$state.go($state.current, {}, {reload: true});
//  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#itemFieldNameTableId').bootstrapTable({
		data : categories,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'fieldName',
	        title: 'Field Name',
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
						'<a class="editFieldName actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="deleteItemFieldName actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editFieldName' : function(e, value,
				row, index) {
			itemFieldNameId = row.id;
			angular.element("#itemFieldNameControllerId").scope().editFieldName();

		},

		'click .deleteItemFieldName' : function(e,
				value, row, index) {
			itemFieldNameId = row.id;
			angular.element("#itemFieldNameControllerId").scope().deleteItemFieldName();
		}
		
	}

	
	$scope.onload();
	
}]);
