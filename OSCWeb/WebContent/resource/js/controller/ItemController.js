'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state','$stateParams','$timeout', function($scope,$http,$rootScope,$state,$stateParams,$timeout) {
	
	$scope.customerItem = {};
	
	

	$scope.getallimages = function()
	{
		var ab = $("#imageprew div").children("img").length;
//		alert(ab);
		$scope.custPhotoJsonList = [];
		for(var i=0; i< ab; i++)
		{
			var imgblob = $("#uploadimg" + i).attr("src");
			$scope.custPhotoJson = {itemId : $scope.customerItem.itemId,imageBlob:imgblob,isUploadedFrame:false};
			$scope.custPhotoJsonList.push($scope.custPhotoJson);
		}
		
		
		
		
        html2canvas(document.getElementById("imageprew"), {
            onrendered: function (canvas) {
                var imageData = canvas.toDataURL('image/png'); 
                $("#newimg").attr('src',imageData);
                
                $scope.customerItem.divBlob = imageData;
                $scope.customerItem.custPhotoJsonList =  $scope.custPhotoJsonList;
         }
        });

        
		

	}
	

	
	
	$scope.getItemInfo = function(){
		if($stateParams.id){
		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemById/"+$stateParams.id);
  		response.success(function(data) {
  			$scope.itemObj = data;
  			$scope.customerItem.itemId = data.id;
  			if($scope.itemObj && $scope.itemObj.discount && $scope.itemObj.discount != 0){
  				$scope.itemObj.mrp = $scope.itemObj.mrp - ($scope.itemObj.mrp * $scope.itemObj.discount) / 100;
  				$scope.itemObj.mrp = parseFloat($scope.itemObj.mrp).toFixed(2);
  				$scope.customerItem.mrp =$scope.itemObj.mrp;
  				$scope.customerItem.name =data.name;
  				$scope.customerItem.total = $scope.itemObj.mrp;
  				$scope.customerItem.itemFieldValueJsonList = data.itemFieldValueJsonList;
  				if($scope.itemObj.minQuantityToPurchase){
  					$scope.itemObj.mrp = parseFloat($scope.itemObj.mrp / $scope.itemObj.minQuantityToPurchase).toFixed(2);//This is for each quantity 
  				}
  					
  				
  				$scope.customerItem.minQuantityToPurchase  = $scope.itemObj.minQuantityToPurchase;
  				$scope.customerItem.quantity  = $scope.itemObj.minQuantityToPurchase;
  			}
  			$scope.selectedPositions = $scope.itemObj.itemCroppedDimensionJsonList;
  			$scope.specificationList =$scope.itemObj.itemFieldValueJsonList;
  			/*alert(JSON.stringify($scope.selectedPositions));
  			alert($scope.selectedPositions.length);*/
  			
  			
  			
  			for(var i=0;i<$scope.selectedPositions.length;i++)
  			{
  				$scope.obj = $scope.selectedPositions[i];
  				$scope.obj.left = parseInt($scope.obj.left) - 1;
  				/*var newEle = angular.element("<div style='position:absolute;background-color:#ffff00;border:1px solid #000000;opacity:0.5;top:"+$scope.obj.top+"px;left:"+$scope.obj.left+"px;width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;'></div>");*/
  				var newEle = angular.element("<div class='usrimg' style='top:"+$scope.obj.top+"px;left:"+$scope.obj.left+"px;width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;'><img id='uploadimg"+i+"' style='width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;' class='uploadedImgId' src='' /></div><div class='browsmask' style='top:"+$scope.obj.top+"px;left:"+$scope.obj.left+"px;width:"+$scope.obj.width+"px;height:"+$scope.obj.height+"px;'><input id='tonyupload"+i+"' class='userupimg' type='file'></div>");
  				var target = document.getElementById('imageprew');
  				angular.element(target).append(newEle);
  				/*$("#delselect").click();*/
  			}
  			
  			
  			
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}
	}

	
	$scope.onChangeItemQuantity = function(){
		$scope.errorMsg = false;
		if($scope.customerItem.quantity && $scope.itemObj.minQuantityToPurchase && parseInt($scope.customerItem.quantity) >= parseInt($scope.itemObj.minQuantityToPurchase)){
			$scope.customerItem.total = parseFloat($scope.itemObj.mrp * $scope.customerItem.quantity).toFixed(2);
		}else{
			$scope.errorMsg = true;
		}
	}

	$scope.addToCartPerformAction = function(){
		var isItemExists = false;
		$scope.croppedItemMsg = null;
		if($rootScope.rsAddedCartItemList){
			if($scope.selectedPositions && $scope.selectedPositions.length){
				var ab = $("#imageprew div").children("img").length;
				for(var i=0; i< ab; i++)
				{
					var imgblob = $("#uploadimg" + i).attr("src");
					if(!imgblob)
						{
						$scope.croppedItemMsg = 'please upload the images';
						$('#addToCartPopupId').modal('hide');
						return;
						
						}
				}
			}
		angular.forEach($rootScope.rsAddedCartItemList, function(obj, key) {
			if(obj.id == $scope.customerItem.itemId)
			{
				isItemExists = true;
			}
		});
		if(!isItemExists){
			$scope.getallimages();
			$timeout(function() {//wait for some time to redirect to another page
				alert(JSON.stringify($scope.customerItem.divBlob));
				$rootScope.rsAddedCartItemList.push($scope.customerItem);	
			 }, 400);
	        
		}
		}
		$('#addToCartPopupId').modal('hide');
	}
	
	
	


	
	
	$scope.addToCartAction = function(){
		angular.element('#addToCartPopup').trigger('click');
	}
	$scope.closeDialog = function(){
		$('#addToCartPopupId').modal('hide');
	}
	
	$scope.getItemInfo();


	
	

}]);
