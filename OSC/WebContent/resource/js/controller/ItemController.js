'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	
	$scope.onload = function(){
		
	     $scope.bounds = {};
	        $scope.cropper = {};
	        $scope.cropper.sourceImage = null;
	        $scope.cropper.croppedImage   = null;
	        $scope.bounds = {};
	        $scope.bounds.left = 0;
	        $scope.bounds.right = 0;
	        $scope.bounds.top = 0;
	        $scope.bounds.bottom = 0;
	        
	    
	        
		
	}
	
	$scope.getPosition = function(){
	    alert($("#ELEMENT").offset().top - $("#container").offset().top);
	}
	
	
	$scope.stepsModel = [];

	    $scope.imageUpload = function(event){
	         var files = event.target.files; //FileList object
	         
	         for (var i = 0; i < files.length; i++) {
	             var file = files[i];
	                 var reader = new FileReader();
	                 reader.onload = $scope.imageIsLoaded; 
	                 reader.readAsDataURL(file);
	         }
	    }

	    $scope.imageIsLoaded = function(e){
	        $scope.$apply(function() {
	            $scope.stepsModel.push(e.target.result);
	        });
	    }
	
	$scope.taskObj = {};
	
	
	
	
	
	$scope.onload();
}]);
