angular.module('menuApp').directive('seeAllusers', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/seeAllUsers.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.getAllUsers().then(function(response){
				$scope.users=response.users;
				for(var i=0;i<$scope.users.length;i++){
					$scope.users[i].creationDate=new Date($scope.users[i].creationDate).toLocaleDateString();
				}
			});
		}],
	}
	
	
});
