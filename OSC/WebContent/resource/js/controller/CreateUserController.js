'use strict';

App.controller('CreateUserController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.userObj = {};
	

	$scope.clear = function(){
		$scope.userObj = {};
		var userId = null;
	}		
	
	
	$scope.saveOrUpdateAction = function(){
		$scope.userObj.effectiveFrom = $('#effectiveFromId').val();
		var response = $http.post(constants.localhost_port+constants.service_context+"/UserController/saveOrUpdateUser",$scope.userObj);
		response.success(function(data) {
			$scope.userObj = {};
			$scope.getAllUsers();
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
        });

	}
	
	
	var users = [];
	var userId = null;
	
	$scope.onload = function(){
		$scope.getAllUsers();
	}

	$scope.getAllUsers = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/UserController/getAllUsers");
  		response.success(function(data) {
  			users = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editUser = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/UserController/getUserById/"+userId);
  		response.success(function(data) {
  			$scope.userObj = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	$scope.deleteUser = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/UserController/deleteUserById/"+userId);
  		response.success(function(data) {
  			$scope.userObj = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#userTableId').bootstrapTable({
		data : users,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'userName',
	        title: 'User Name'
	    }, {
	        field: 'fullName',
	        title: 'Full Name'
	    }, {
	        field: 'email',
	        title: 'Email'
	    }, {
	        field: 'empType',
	        title: 'Emp Type'
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
						'<a class="editUser actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="removeUser actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editUser' : function(e, value,
				row, index) {
			userId = row.id;
			angular.element("#userControllerId").scope().editUser();

		},

		'click .removeUser' : function(e,
				value, row, index) {
			userId = row.id;
			angular.element("#userControllerId").scope().deleteUser();
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
