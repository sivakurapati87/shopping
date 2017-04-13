'use strict';

App.controller('ItemController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	$scope.pageObj = {pageFrom:0,pageTo:constants.records_per_page};

	$scope.getAllItems = function(){
		
  		var response = $http.post(constants.localhost_port+constants.service_context+"/ItemController/getAllItems",$scope.pageObj);
  		response.success(function(data) {
  			$scope.itemList = data;
  			$scope.paginationValue = "Page "+(parseInt($scope.pageObj.pageFrom / constants.records_per_page)+1)+' Of '+(parseInt($scope.maxRecords / constants.records_per_page)+1);
//  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	$scope.searchAction = function(){
		$scope.getAllItems();
		$scope.getNoOfItems();
	}
	
$scope.getNoOfItems = function(){
  		var response = $http.post(constants.localhost_port+constants.service_context+"/ItemController/findNoOfItems",$scope.pageObj);
  		response.success(function(data) {
  			$scope.maxRecords = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	$scope.pagination = function(symbol){
		var presentPage = $scope.pageObj.pageFrom;
		if(symbol == 'start'){
			$scope.pageObj.pageFrom = 0;
		}else if(symbol == 'prev'){
			$scope.pageObj.pageFrom = ($scope.pageObj.pageFrom - constants.records_per_page)<0?0:($scope.pageObj.pageFrom - constants.records_per_page);
		}else if(symbol == 'next'){
			$scope.pageObj.pageFrom = ($scope.pageObj.pageFrom + constants.records_per_page)> $scope.maxRecords ?(parseInt($scope.maxRecords / constants.records_per_page) * constants.records_per_page):($scope.pageObj.pageFrom + constants.records_per_page);
		}else{
			$scope.pageObj.pageFrom = parseInt($scope.maxRecords / constants.records_per_page) * constants.records_per_page ;
		}
		
		if($scope.pageObj.pageFrom == $scope.maxRecords){
			$scope.pageObj.pageFrom = presentPage;
		}
//		alert(JSON.stringify($scope.pageObj));
		
		
		$scope.getAllItems();
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
				angular.element("#createItemControllerId").scope().deleteItem(row.id);
			}
			
		}
		

		$scope.deleteItem = function(itemId){
			var response = $http.get(constants.localhost_port+constants.service_context+"/ItemController/deleteItemById/"+itemId);
	  		response.success(function(data) {
	  			$state.go($state.current, {}, {reload: true});
	  		});
	  		response.error(function() {
	        	  console.error('Could not Perform well');
	        	  $state.go("login");
	          });
		}
		
		
		$scope.editItem = function(itemId){
			$state.go("create_item",{id:itemId});
		}

	$scope.onClickAddNew = function(){
	}
	
	$scope.onload = function(){
			$scope.getNoOfItems();
	        $scope.getAllItems();
	}
	
	$scope.onload();
}]);
