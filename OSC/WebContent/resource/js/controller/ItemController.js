'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.obj = {};
	$scope.selectedPositions = [];
	var i=1;
	
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
	        
	    
	        
	        

	        $('#uploadmainimg').change( function(event) {
	    		var tmppath = URL.createObjectURL(event.target.files[0]);
	    		    $("#target").fadeIn("fast").attr('src',URL.createObjectURL(event.target.files[0]));
	    		});
	        
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
	
	
	$scope.reloadRoute = function() {
	    $state.reload();
	};
	
	
	$scope.getmaskdtls = function() {
		$scope.selection_err = "";
		$scope.obj = {left: $('#x').val(), top: $('#y').val(), width: $('#w').val(), height: $('#h').val(),name:"Image "+(i++)}
		if(!$scope.obj.left)
		{
		$scope.selection_err = "Please select mask Area";
		return;
		}
		
		$scope.ltpos = parseInt($('#x').val()) + 15;
		$scope.obj.left = $scope.ltpos;
		
		
		var flag = false;
		for(var j=0;j<$scope.selectedPositions.length;j++){
			if(!flag && $scope.obj.left == $scope.selectedPositions[j].left && $scope.obj.top == $scope.selectedPositions[j].top
					&& $scope.obj.width == $scope.selectedPositions[j].width && $scope.obj.height == $scope.selectedPositions[j].height){
				flag = true;
			}
		}
		
		if(!flag)
			{
			
			var newEle = angular.element("<div style='position:absolute;background-color:#ffff00;border:1px solid #000000;opacity:0.5;top:"+$scope.obj.top+"px;left:"+$scope.ltpos+"px;width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;'></div>");
		    var target = document.getElementById('imageprew');
		    angular.element(target).append(newEle);
		    $("#delselect").click();
		    $scope.selectedPositions.push($scope.obj);
			}else{
				$scope.selection_err = "Selected Area is already Masked";
			}
		
		
	    

	};
	
	
	
	
	

	
	
	
	
	$scope.onload();
}]);
