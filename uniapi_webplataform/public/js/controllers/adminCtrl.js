angular.module('menuApp').controller("adminCtrl", function ($scope,$http,uniapi) {
	$scope.changeTab=function(tab){
		if(tab =='applicationTAB'){
			$("#personalTABTAB").removeClass("active");
			$("#applicationTABTAB").addClass("active");
			
			$("#perTab").css("display","none");
			$("#appTab").css("display","block");
					
		}		
		if(tab =='personalTAB'){
			$("#applicationTABTAB").removeClass("active");
			$("#personalTABTAB").addClass("active");
			
			$("#appTab").css("display","none");
			$("#perTab").css("display","block");
					
		}
	};
});
