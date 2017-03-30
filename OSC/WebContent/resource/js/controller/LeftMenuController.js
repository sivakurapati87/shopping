'use strict';

App.controller('LeftMenuController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	
	
	$scope.logout = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/LoginController/logout");
		response.success(function(data) {
			$state.go("login");
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
        });

	}
	
	
}]);
