angular.module('menuApp').service('uniapi', function ($http,$q) {

  	var UNIAPI_URL_API_REST="http://localhost:8080";
	var tokenSession=null;
	var myGroups=null;
	var myProjects=null;
	var myService = this;

	this.init=function(){
		myService.tokenSession=localStorage.getItem("tokenSession");
		if(myService.tokenSession == null || myService.tokenSession == undefined){
			window.location.href="/loggin";
		}
	};
	this.checkState=function(state){
		if(state == 4){
			window.location.href="/loggin";
		}
	};
	this.getMyProjects= function(){
		
		$d=$q.defer();
		var projects=[];
		var promesas=[];			
		myService.init();
		
		myService.upgradeMyProjects().then(function(data){
					
			for(var i=0;i<data.relatedIDs.length;i++){
				promesas.push(myService.getProject(data.relatedIDs[i]));
			}
			$q.all(promesas).then(function (projectsRes) {
				for(var i=0;i<projectsRes.length;i++){
					if(projectsRes[i].state == 0)					
						projects.push(projectsRes[i].projects[0]);
				}
				$d.resolve(projects);
			});		
		});
		return $d.promise;	
	};
	

	this.upgradeMyProjects= function(){
				
		myService.init();
		var urlComplete=UNIAPI_URL_API_REST+"/project/"+this.tokenSession+"/all";

		promise=$http({
				method : "GET",
				url : urlComplete			
			}).then(function(response){	
				myService.checkState(response.data.state);
				console.log("[UniApi_Service]:Upgrating my projects");			
				console.log(response.data);
				return response.data;
		});

		return promise;
	};
	
	this.getProject= function(id){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/project/"+myService.tokenSession+"/"+id;
		promise=$http({
				method:"GET",
				url:urlComplete		
			}).then(function(response){
				myService.checkState(response.data.state);
				console.log("[UniApi_Service]:get a project");			
				console.log(response.data);
				return response.data;
		});

		return promise;	
	};
	this.updateProject= function(id,project){
		var promise;		
		var urlComplete=UNIAPI_URL_API_REST+"/project/"+myService.tokenSession+"/"+id
		promise=$http({
			method:"PATCH",
			url:urlComplete,
			data:JSON.stringify(project)					
		}).then(function(response){
			myService.checkState(response.data.state);			
			return response.data;					
		});

		return promise;
	};
	this.deleteProject= function(id){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/project/"+myService.tokenSession+"/"+id;		
		promise=$http({
			method:"DELETE",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);	
			return response.data;
		
		});
		return promise;
	};
	this.shareProject= function(id,idGroup){
	};
	this.executedProject= function(id,inputParameters){

	};

	this.upgradeMyGroups=function(){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/all";
		promise=$http({
				method : "GET",
				url : urlComplete			
			}).then(function(response){
				myService.checkState(response.data.state);
				console.log("[UniApi_Service]:Upgrating my groups");			
				console.log(response.data);
				return response.data;
		});

		return promise;
	};
	
	this.getGroup=function(groupID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID;
		promise=$http({
				method:"GET",
				url:urlComplete		
			}).then(function(response){
				myService.checkState(response.data.state);
				console.log("[UniApi_Service]:get a group");			
				console.log(response.data);
				return response.data;
		});

		return promise;
	};

	this.getMyGroups= function(){

		$d=$q.defer();
		groups=[];
		promesas=[]			
		myService.init();
		myService.upgradeMyGroups().then(function(data){
								
			for(var i=0;i<data.relatedIDs.length;i++){
				var groupID=data.relatedIDs[i];
				promesas.push(myService.getGroup(groupID));
			}
			$q.all(promesas).then(function (groupsRes) {
				for(var i=0;i<groupsRes.length;i++){
					if(groupsRes[i].state == 0)					
						groups.push(groupsRes[i].groups[0]);
				}
				$d.resolve(groups);
			});
		});
		
		return $d.promise;
	};
	this.updateGroup= function(id,group){
		var promise;		
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id;
		promise=$http({
			method:"PATCH",
			url:urlComplete,
			data:JSON.stringify(group)					
		}).then(function(response){
			myService.checkState(response.data.state);	
			return response.data;					
		});

		return promise;
	};
	this.deleteGroup= function(id){
		var promise;		
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id;
		promise=$http({
			method:"DELETE",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		
		});
		return promise;
	};
	this.inviteToGroup= function(id,email){
				var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id+"/member/create";
		var message={state:0,
			tokkenSession:myService.tokenSession,
			relatedIDs:[id],
			users:[email]
		};
		var data=JSON.stringify(message);
		console.log(data);
		promise=$http({
			method:"POST",
			url:urlComplete,
			data:data
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		
		});

		return promise;
	};
	this.getAllGroupMembers=function(id){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id+"/member";

		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		
		});

		return promise;
	};
	this.removeMemberOfTheGroup=function(id,user){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id+"/member/"+user+"/delete";

		promise=$http({
			method:"DELETE",
			url:urlComplete
		}).then(function(response){
			console.log(response);
			myService.checkState(response.data.state);
			return response.data;
		
		});

		return promise;
	};
});
