'use strict';

App.controller('CustomerOrdersController', ['$scope','$http','$rootScope','$state','$window', function($scope,$http,$rootScope,$state,$window) {

	
	$rootScope.current_state= $state.current.name;
	
	
	$scope.onLoad = function(){
		var fromDate = new Date();
		fromDate.setDate(fromDate.getDate()-30);
		var todate = new Date();
		$scope.cartObj = {status:'none',fromDate:fromDate,toDate:todate};
		$scope.getAllCustomerOrders();
	}
	
	$scope.getAllCustomerOrders = function(){
		
  		var response = $http.get(constants.localhost_port+constants.service_context+"/CustomerController/getAllCustomerOrders?fromDate="+$scope.convertDateToString($scope.cartObj.fromDate)+
  				"&toDate="+$scope.convertDateToString($scope.cartObj.toDate)+"&status="+(($scope.cartObj.status == 'none')?null:($scope.cartObj.status)));
  		response.success(function(data) {
  			$scope.ordersList = data;
  			/*for(var i=0;i<$scope.ordersList.length;i++){
  				for(var j=0;j<$scope.ordersList[i].custPhotoJsonList.length;j++){
  					if($scope.ordersList[i].custPhotoJsonList[j].imageBlob){
  			      var  blob = new Blob([$scope.ordersList[i].divBlob], { type: 'image/png' }),
  			        url = $window.URL || $window.webkitURL;
  			      $scope.ordersList[i].custPhotoJsonList[j].imageBlob = url.createObjectURL(blob);
  			      alert($scope.ordersList[i].custPhotoJsonList[j].imageBlob);
  					}
//  			      alert($scope.ordersList[i].custPhotoJsonList[j].imageBlob);
  				}
  			}*/
//  			alert('siva');
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });
  	}

	$scope.addToCartAction = function(imageData){
		$scope.customerImage = imageData;
		angular.element('#addToCartPopup').trigger('click');
	}
	$scope.closeDialog = function(){
		$('#addToCartPopupId').modal('hide');
	}
	
	$scope.changeStatus = function(order){
		$scope.order = order;
		$scope.status = order.status?order.status:'none';
		angular.element('#changeStatusPopup').trigger('click');
	}
	$scope.closeStatusDialog = function(){
		$('#changeStatusPopupId').modal('hide');
	}
	
	$scope.changeStatusAction = function(){
		if($scope.status!='none'){
		var response = $http.get(constants.localhost_port+constants.service_context+"/CustomerController/changeCartStatus?cartId="+$scope.order.id+
				'&status='+$scope.status);
  		response.success(function(data) {
  			$scope.order.status = $scope.status;
  			$('#changeStatusPopupId').modal('hide');
  		});
  		response.error(function() {
        	  console.error('Could not Perform well');
        	  $state.go("login");
          });}else{
        	  $('#changeStatusPopupId').modal('hide'); 
          }
	}
	
	$scope.convertDateToString = function(inputDate){
		if(inputDate instanceof Date){
		var expDate = new Date(inputDate);
    	 var month = '' + (expDate.getMonth() + 1);
         var day = '' + expDate.getDate();
        var  year = expDate.getFullYear();
    	  if (month.length < 2) month = '0' + month;
    	    if (day.length < 2) day = '0' + day;
    	   return [day, (month),year].join('-');
		}else{
			return inputDate;
		}
	};
	
	$scope.onLoad();
	
}]);
