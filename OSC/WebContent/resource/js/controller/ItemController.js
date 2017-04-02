'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.obj = {};
	var itemId = null;
	$scope.itemObj = {};
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
  			$scope.checkBoxes = data;
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

	$scope.getAllItems = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getAllItems");
  		response.success(function(data) {
  			$scope.itemList = data;
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	
	$scope.saveOrUpdateAction = function(){
		$scope.itemObj.itemCroppedDimensionJsonList = $scope.selectedPositions;
		$scope.itemObj.itemFieldValueJsonList = $scope.specificationList;
		$scope.itemObj.subCategoryIds = $scope.subCategoryIds;
		$scope.itemObj.imageSrc = $('#uploadedImgId').attr('src');
//		alert(JSON.stringify($scope.itemObj));
		var response = $http.post(constants.localhost_port+constants.service_context+"/ItemController/saveOrUpdate",$scope.itemObj);
		response.success(function(data) {
//			$state.go($state.current, {}, {reload: true});
			var i=1;
		});
		response.error(function() {
      	  console.error('Could not save or update');
      	  $state.go("login");
        });

	}

	$scope.displayTable = function(){
		$('#itemTableId').bootstrapTable({
			data : $scope.itemList,
			pagination : true,
			paginationVAlign : "top",
			pageSize : 5,
			search : true,
			pageList : [ 5,10, 20],
		    columns: [{
		        field: 'name',
		        title: 'Category Name',
		        sortable : true
		    },{
		        field: 'subcategory',
		        title: 'Sub-Category',
		        sortable : true
		    },{
		        field: 'mrp',
		        title: 'MRP',
		        sortable : true
		    },{
		        field: 'discount',
		        title: 'Discount',
		        sortable : true
		    },{
		        field: 'imageSrc',
		        title: 'Image',
		        formatter : imageFormatter
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
							'<a class="editItem actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
							'&nbsp;<a class="removeCategory actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
							 ]
							.join('');
				}
		
		function imageFormatter(value, row,
				index) {
					return [
							'<img src="'+row.imageSrc+'" style="width:100px;height:100px;">',
							 ]
							.join('');
				}

		
		/* Table button actions functionalities */
		window.operateEvents = {
			
			'click .editItem' : function(e, value,
					row, index) {
				itemId = row.id;
				angular.element("#itemControllerId").scope().editItem();

			},

			'click .removeCategory' : function(e,
					value, row, index) {
				itemId = row.id;
				angular.element("#itemControllerId").scope().deleteCategory();
			}
			
		}
		
		$scope.editItem = function(){
			var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/getItemById/"+itemId);
	  		response.success(function(data) {
	  			$scope.onClickAddNew();
	  			$scope.itemObj = data;
	  			
	  			$scope.selectedPositions = $scope.itemObj.itemCroppedDimensionJsonList;
	  			$scope.specificationList =$scope.itemObj.itemFieldValueJsonList;
	  			$scope.subCategoryIds = $scope.itemObj.subCategoryIds;
//	  			alert(data.imageSrc);
	  			$('#uploadedImgId').attr('src',data.imageSrc);
	  		});
	  		response.error(function() {
	        	  console.error('Could not Perform well');
	        	  $state.go("login");
	          });
		}

	$scope.onClickAddNew = function(){
			$scope.displayGrid = !$scope.displayGrid;
			if($scope.displayGrid){
				$('#gridBlockId').css('display','block');
				$('#displayContentId').css('display','none');
			}else{
				$scope.itemObj = {};
	  			$scope.selectedPositions = [];
	  			$scope.specificationList = [{}];
	  			$scope.subCategoryIds = [];
				$('#gridBlockId').css('display','none');
				$('#displayContentId').css('display','block');
			}
		}
	
	$scope.onload = function(){
		
			$scope.displayGrid = true;
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
	        $scope.getAllItems();
	        
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
