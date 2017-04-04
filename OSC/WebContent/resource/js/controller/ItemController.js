'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	

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
//				angular.element("#createItemControllerId").scope().editItem();
				$state.go("create_item",{id:row.id});

			},

			'click .removeCategory' : function(e,
					value, row, index) {
//				itemId = row.id;
				angular.element("#createItemControllerId").scope().deleteCategory();
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
	}
	
	$scope.onload = function(){
		
	        $scope.getAllItems();
	}
	
	$scope.onload();
}]);
