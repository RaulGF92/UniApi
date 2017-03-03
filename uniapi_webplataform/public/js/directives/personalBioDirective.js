angular.module('menuApp').directive('personalBio', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/changeBio.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.whoami()
			.then(function(response){
				$scope.whoami=response;
				$scope.dateParse=new Date($scope.whoami.person.birthday);
			});
		}],
	}
	
	
});
