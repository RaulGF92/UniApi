angular.module('menuApp').directive('changePassword', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/changePassword.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.init();
			$scope.changePassword=function(){
				if($scope.checkNotPassFortitude()){
					alert("La contraseña debera ser mayor de 10 caracteres, conterner mayusculas e minusculas. Ademas seria recomendable el uso de caracteres y no usar palabras concretas o nombres.");					
					return;
				}
				if($scope.newPass!=$scope.rePass){
					alert("La contraseña debe ser igual.");
					return;
				}
				uniapi.changePass($scope.newPass).then(function(response){
					if(response.state == 0){	
						alert("Se ha realizado la modificación");
						$scope.newPass="";
						$scope.rePass="";
					}
				});
			};
			$scope.checkNotPassFortitude=function(){
				if($scope.newPass.length<10){
					return true;
				}
			};
		}],
	}
	
	
});
