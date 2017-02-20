angular.module('menuApp').directive('gestiongroup', function () {
	
	return{
		restrict:"E",   
		templateUrl:"pages/gestionGroup.htm",
		scope: {
      			ngGroup: '@'
    		},
		controller: ['$scope', 'uniapi', function($scope, uniapi) {
			
     			uniapi.getGroup($scope.ngGroup).then(function(data){
				$scope.group=data.groups[0];
				console.log($scope.group);
				$scope.sharingGroupPermissions=$scope.parseVector($scope.group.sharingGroup);
				$scope.projectPropertiesPermissions=$scope.parseVector($scope.group.projectProperties);
				$scope.memberGestionPermissions=$scope.parseVector($scope.group.memberGestion);
				$scope.groupCreationPermissions=$scope.parseVector($scope.group.groupCreation);
				$scope.reworkCheckBox();			
			});
			$scope.modifyCheckBox=function(check,checkBox){
				console.log(check);
				console.log(checkBox);
				if(check){
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).removeAttr("disabled");
					}
				}else{
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).attr("disabled","disabled");
					}
				}

			};
			$scope.reworkCheckBox=function(){
				var check=$scope.sharingGroupPermissions[0]
				var checkBox="sharingGroupPermissions";
				$scope.modifyCheckBox(check,checkBox);
				
				var check=$scope.projectPropertiesPermissions[0]
				var checkBox="projectPropertiesPermissions";
				$scope.modifyCheckBox(check,checkBox);
			
				var check=$scope.memberGestionPermissions[0]
				var checkBox="memberGestionPermissions";
				$scope.modifyCheckBox(check,checkBox);
		
				var check=$scope.groupCreationPermissions[0]
				var checkBox="groupCreationPermissions";
				$scope.modifyCheckBox(check,checkBox);
			};
			$scope.parseVector= function (vector){
				var response=[];
				for(var i=0;i<vector.length;i++){
					if(vector[i] == "YES"){
						response.push(true);
					}
					if(vector[i] == "NO"){
						response.push(false);
					}
				}
				return response;
			};
			$scope.checkBox=function(checkBox){
				var check=$("#"+checkBox).prop("checked");
				if(!check){
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).removeProp("checked");
						$("#"+checkBox+i).attr("disabled","disabled");
						$("#"+checkBox+i).val("false");
					}
					if(checkBox=="sharingGroupPermissions"){
						$scope.sharingGroupPermissions[1]=false;
						$scope.sharingGroupPermissions[2]=false;
						$scope.sharingGroupPermissions[3]=false;						
					}
					if(checkBox=="projectPropertiesPermissions"){
						$scope.projectPropertiesPermissions[1]=false;
						$scope.projectPropertiesPermissions[2]=false;					
					}
					if(checkBox=="memberGestionPermissions"){
						$scope.memberGestionPermissions[1]=false;
						$scope.memberGestionPermissions[2]=false;
						$scope.memberGestionPermissions[3]=false;				
					}
					if(checkBox=="groupCreationPermissions"){
						$scope.groupCreationPermissions[1]=false;
						$scope.groupCreationPermissions[2]=false;
						$scope.groupCreationPermissions[3]=false;
					}
				}else{
					for(var i=1;i<=3;i++){
						$("#"+checkBox+i).prop("checked","true");
						$("#"+checkBox+i).removeAttr("disabled");
						$("#"+checkBox+i).val("true");
						if(checkBox=="sharingGroupPermissions"){
							$scope.sharingGroupPermissions[1]=true;
							$scope.sharingGroupPermissions[2]=true;
							$scope.sharingGroupPermissions[3]=true;						
						}
						if(checkBox=="projectPropertiesPermissions"){
							$scope.projectPropertiesPermissions[1]=true;
							$scope.projectPropertiesPermissions[2]=true;					
						}
						if(checkBox=="memberGestionPermissions"){
							$scope.memberGestionPermissions[1]=true;
							$scope.memberGestionPermissions[2]=true;
							$scope.memberGestionPermissions[3]=true;				
						}
						if(checkBox=="groupCreationPermissions"){
							$scope.groupCreationPermissions[1]=true;
							$scope.groupCreationPermissions[2]=true;
							$scope.groupCreationPermissions[3]=true;
						}
					}
				}
			};
			$scope.clickTypeGroup=function(id){
				switch(id){
					case "privateButton":
						$("#privateButton").addClass("active");
						$("#mainButton").removeClass("active");
						$("#publicButton").removeClass("active");
						$scope.type="RESTRICTED_GROUP";
					break;
					case "publicButton":
						$("#publicButton").addClass("active");
						$("#privateButton").removeClass("active");
						$("#mainButton").removeClass("active");
						$scope.type="PUBLIC_GROUP";
					break;
					case "mainButton":
						$("#publicButton").removeClass("active");
						$("#privateButton").removeClass("active");
						$("#mainButton").addClass("active");
						$scope.type="MAIN_GROUP";
					break;
				};
			};
   		 }],
		link: function(scope, iElement, iAttrs, ctrl) {
     			 scope.id=iAttrs.ngGroup;
			
   		 }
	}
	
	
});
