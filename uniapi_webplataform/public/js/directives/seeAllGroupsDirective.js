angular.module('menuApp').directive('seeAllgroups', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/seeAllGroups.htm",
		scope: {
      			
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			uniapi.getAllGroups().then(function(response){
				$scope.groups=response.groups;
				for(var i=0;i<$scope.groups.length;i++){
					$scope.groups[i].creationDate=new Date($scope.groups[i].creationDate).toLocaleDateString();
				}
			});
		}],
	}
	
	
});
