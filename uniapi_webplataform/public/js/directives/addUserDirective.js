angular.module('menuApp').directive('addUsers', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/addUser.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			$scope.userLogin={user:"",pass:"",rol:"user"};
			$scope.clickUser=function(){
				$("#adminChoose").removeClass("active");
				$("#userChoose").addClass("active");
				$scope.userLogin.rol="user";
			};
			$scope.clickAdmin=function(){
				$("#adminChoose").addClass("active");
				$("#userChoose").removeClass("active");
				$scope.userLogin.rol="admin";
			};
			$scope.createAccount=function(){
				if($scope.userLogin.pass==$scope.repass && $scope.repass.length>10){
					uniapi.createAccount($scope.userLogin)
					.then(function(response){
						if(reponse.state==0){
							alert("Se ha realizado la operación");
							$scope.userLogin.user="";
							$scope.userLogin.pass="";
							$scope.rePass="";
							$scope.clickUser();
						}else{
							alert("No se ha realizado la operación");
							$scope.userLogin.user="";
							$scope.userLogin.pass="";
							$scope.rePass="";
							$scope.clickUser();
						}
					});
				}else{
					alert("La contraseña debe coincidir, ademas debe de ser superior a 10 caracteres");
				}
			};
		}],
	}
	
	
});
