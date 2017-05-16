'use strict';

App.controller('PromoCodeController', ['$scope','$http','$rootScope','$state', function($scope,$http,$rootScope,$state) {

	
	$rootScope.current_state= $state.current.name;
	
	$scope.promocode = {};

	$scope.getAllSubCategoriesWithCategory = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/SubCategoryController/getAllSubCategoriesWithCategory");
  		response.success(function(data) {
  			$scope.subCategories = data.multiple;
//  			$scope.uniqueSubCategories = data.unique;
  			$scope.subCategories.push.apply($scope.subCategories, data.unique);
  			$scope.getAllPromoCodes();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}
	
	 $scope.imageUpload = function(element){
	        var reader = new FileReader();
	        reader.onload = $scope.imageIsLoaded;
	        reader.readAsDataURL(element.files[0]);
//	        alert( reader.readAsDataURL(element.files[0]));
	    }
	 
	 $scope.imageIsLoaded = function(e){
//	        $scope.$apply(function() {
		 $scope.promocode.promoImageBlob = e.target.result;
//	        });
	    }
	
	$scope.clear = function(){
		$scope.promocode = {};
		var promocodeId = null;
	}	
	
	
	$scope.saveOrUpdateAction = function(){
		var response = $http.post(constants.localhost_port+constants.service_context+"/PromoCodeController/saveOrUpdate",$scope.promocode);
		response.success(function(data) {
			$scope.promocode = {};
			$state.go($state.current, {}, {reload: true});
		});
		response.error(function() {
      	  console.error('Could not save or update jobtitles');
      	$state.go("login");
        });

	}
	
	
	var promocodes = [];
	var promocodeId = null;
	
	$scope.onload = function(){
		$scope.getAllSubCategoriesWithCategory();
	}

	$scope.getAllPromoCodes = function(){
  		var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/getAllPromoCodes");
  		response.success(function(successData) {
  			promocodes = successData;
  			if(promocodes){
  			for(var i=0;i<promocodes.length;i++){
  				if(promocodes[i].subCategoryId && $scope.subCategories){
  					for(var j=0;j<$scope.subCategories.length;j++){
  						if($scope.subCategories[j].id == promocodes[i].subCategoryId){
  							promocodes[i].subCategoryName = $scope.subCategories[j].name; 
  						}
  					}
  				}
  			}}
//  			alert(JSON.stringify(successData));
  			$scope.displayTable();
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.editPromoCode = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/getPromoCodeById/"+promocodeId);
  		response.success(function(data) {
  			$scope.promocode = data;
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	$scope.deletePromoCode = function(){
		var response = $http.get(constants.localhost_port+constants.service_context+"/PromoCodeController/deletePromoCodeById/"+promocodeId);
  		response.success(function(data) {
  			$state.go($state.current, {}, {reload: true});
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
	}

	
	$scope.displayTable = function(){
	$('#promoCodeTableId').bootstrapTable({
		data : promocodes,
		pagination : true,
		paginationVAlign : "top",
		pageSize : 5,
		pageList : [ 5,10, 20],
	    columns: [{
	        field: 'code',
	        title: 'Promo Code',
	        sortable : true
	    },{
	        field: 'applyOnAmount',
	        title: 'Apply On Amount',
	        sortable : true
	    },{
	        field: 'amountToReduce',
	        title: 'Amount To Reduce',
	        sortable : true
	    },{
	        field: 'subCategoryName',
	        title: 'Sub Category',
	        sortable : true
	    },{
	        field: 'promoImageBlob',
	        title: 'Apply On Amount',
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
						'<a class="editPromoCode actionIcons" title="Edit"><i class="fa fa-edit" style="font-size:12px;"></i></a>&nbsp;',
						'&nbsp;<a class="removeCategory actionIcons" title="Remove"><i class="fa fa-trash-o" style="font-size:12px;"></i></a>',
						 ]
						.join('');
			}

	function imageFormatter(value, row,
			index) {
				return [
						'<img src="'+row.promoImageBlob+'" width="100" height="100">',
						 ]
						.join('');
			}

	
	/* Table button actions functionalities */
	window.operateEvents = {
		
		'click .editPromoCode' : function(e, value,
				row, index) {
			promocodeId = row.id;
			angular.element("#promocodeControllerId").scope().editPromoCode();

		},

		'click .removeCategory' : function(e,
				value, row, index) {
			promocodeId = row.id;
			angular.element("#promocodeControllerId").scope().deletePromoCode();
		}
		
	}

	
	$scope.onload();
	
}]);
