angular.module('menuApp').directive('removeData', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/removeAll.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			$scope.clickBorrar=function(){
				$("#preguntaModal").css("display","block");			
			};
			$scope.closeModal=function(){
				$("#preguntaModal").css("display","none");
				$scope.confirmacion="";
			};
			$scope.borrarBaseDeDatos=function(){
				if($scope.confirmacion == "BORRAR BASE DE DATOS"){
					uniapi.deleteDB().then(function(response){
						if(response.state==0){
							alert("se ha eliminado la DB. Logueate con admin");
							window.location.href="/loggin";
						}else{
							alert("Ha pasado algo : (");
						}
					});
				}else{
					alert("Has introducido mal la frase");
				}
			};
		}],
	}
	
	
});
