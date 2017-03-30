'use strict';

App.controller('LoginController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.userObj = {};
	$scope.displayError = false;
	
	
	
	$scope.loginAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/LoginController/loginAction",$scope.userObj);
		response.success(function(data) {
			if(data){
			$rootScope.loginUserObj = data;
			$state.go("create_user");
			}else{
				$scope.displayError = true;	
			}
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
        });

	}
	
	
}]);
