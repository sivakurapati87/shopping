'use strict';

App.controller('CreateItemController', ['$scope','$http','$rootScope','$state','$stateParams', function($scope,$http,$rootScope,$state,$stateParams) {

	
	$rootScope.current_state= "item";
	
	$scope.obj = {};
	$scope.uniqueSubCategory = null;
	$scope.isMultipleProduct = false;
	var itemId = null;
	$scope.itemObj = {minQuantityToPurchase:1};
	$scope.selectedPositions = [];
	var i=1;

	$scope.chAction = function(subCatId){
		 if ($scope.subCategoryIds.indexOf(subCatId) === -1) {
	            $scope.subCategoryIds.push(subCatId);
	        } else {
	            $scope.subCategoryIds.splice($scope.subCategoryIds.indexOf(subCatId), 1);
	        }
	}
	
	$scope.getAllSubCategoriesWithCategory = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/getAllSubCategoriesWithCategory");
  		response.success(function(data) {
  			$scope.checkBoxes = data.multiple;
  			$scope.uniqueSubCategories = data.unique;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.addSpecification = function(){
		$scope.specificationList.push({});
	}
	
	$scope.removeSpecification = function(){
		if($scope.specificationList.length != 1){
		$scope.specificationList.splice(($scope.specificationList.length-1),1);
		}
	}
	
	$scope.getAllItemFieldNames = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/FieldNameController/getAllItemFieldNames");
  		response.success(function(data) {
  			$scope.fieldNameList = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	
	$scope.onChangeSingelValue = function(value){
		$scope.uniqueSubCategory = value;
	}
	
	$scope.onClickMultipleProductChk = function(ckValue){
		$scope.isMultipleProduct = ckValue;
	}
	
	$scope.saveOrUpdateAction = function(){
		$scope.errorOccured = false;
		if(!$scope.isMultipleProduct && $scope.uniqueSubCategory){
			$scope.subCategoryIds = [$scope.uniqueSubCategory];
		}
		if($scope.subCategoryIds && $scope.subCategoryIds.length >0){
		$scope.itemObj.itemCroppedDimensionJsonList = $scope.selectedPositions;
		$scope.itemObj.itemFieldValueJsonList = $scope.specificationList;

		$scope.itemObj.subCategoryIds = $scope.subCategoryIds;
		
		$scope.itemObj.imageSrc = $('#uploadedImgId').attr('src');
//		alert(JSON.stringify($scope.itemObj));
		var response = $http.post(constants.localhost_port+constants.service_context+"/ItemController/saveOrUpdate",$scope.itemObj);
		response.success(function(data) {
			$state.go("item");
		});
		response.error(function() {
      	  console.error('Could not save or update');
      	  $state.go("login");
        });
		}else{
			$scope.errorOccured = true;
		}
	}

	
		
		$scope.editItem = function(){
			var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemById/"+itemId);
	  		response.success(function(data) {
	  			$scope.itemObj = data;
	  			
	  			
	  			
	  			$scope.selectedPositions = $scope.itemObj.itemCroppedDimensionJsonList;
	  			$scope.specificationList =$scope.itemObj.itemFieldValueJsonList;
	  			if($scope.itemObj.subCategoryIds){
	  				var flag=false;
	  				if($scope.itemObj.subCategoryIds.length == 1){
	  					for(var i=0;i<$scope.uniqueSubCategories.length;i++){
	  						if($scope.uniqueSubCategories[i].id == $scope.itemObj.subCategoryIds[0]){
	  							flag = true;
	  							$scope.uniqueSubCategory = $scope.itemObj.subCategoryIds[0];
	  						}
	  					}
	  				}
	  				if(!flag){
	  					$scope.isMultipleProduct = true;
	  					$scope.subCategoryIds = $scope.itemObj.subCategoryIds;
	  				}
	  			}
//	  			alert(data.imageSrc);
	  			$('#uploadedImgId').attr('src',data.imageSrc);
	  			$("#imageprew").show();

	  			if($scope.selectedPositions){
	  				for(var i=0;i<$scope.selectedPositions.length;i++){
	  					$scope.obj = $scope.selectedPositions[i];
	  			var newEle = angular.element("<div style='position:absolute;background-color:#ffff00;border:1px solid #000000;opacity:0.5;top:"+$scope.obj.top+"px;left:"+$scope.obj.left+"px;width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;'></div>");
			    var target = document.getElementById('imageprew');
			    angular.element(target).append(newEle);
			    $("#delselect").click();
	  			}}
	  		});
	  		response.error(function() {
	        	  console.error('Could not Perform well');
	        	  $state.go("login");
	          });
		}

	$scope.clear = function(){
		$state.go($state.current, {}, {reload: true});
	}
		
	$scope.onload = function(){
		
	     $scope.bounds = {};
	     $scope.displayGrid = true;
	        $scope.cropper = {};
	        $scope.cropper.sourceImage = null;
	        $scope.cropper.croppedImage   = null;
	        $scope.bounds = {};
	        $scope.bounds.left = 0;
	        $scope.bounds.right = 0;
	        $scope.bounds.top = 0;
	        $scope.bounds.bottom = 0;
	        $scope.specificationList = [];
	        $scope.specificationList.push({});
	        
	        $scope.getAllSubCategoriesWithCategory();
	        $scope.getAllItemFieldNames();
	        
	        if($stateParams.id){
	        	itemId = $stateParams.id;
	        	$scope.editItem();
	        	$scope.isSpecificationDisplay = true;
	        	$scope.isItemInfoBlockDisplay = true;
	        }
	        
	        $scope.subCategoryIds = [];

	        
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
		
//		$scope.uploadedImage = $('#uploadedImgId').attr('src');
		
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
