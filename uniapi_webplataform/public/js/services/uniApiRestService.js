angular.module('menuApp').service('uniapi', function ($http,$q) {

  	var UNIAPI_URL_API_REST="http://localhost:8080";
	var tokenSession=null;
	var myProfile=null;
	var myService = this;
	
	this.init=function(){
		myService.tokenSession=localStorage.getItem("tokenSession");
		if(myService.tokenSession == null || myService.tokenSession == undefined){
			window.location.href="/loggin";
		}
	};
	this.getPublicID=function(){
		this.init();
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/path/"+myService.tokenSession+"/public";
		promise=$http({
				method:"GET",
				url:urlComplete
			}).then(function(response){
				myService.checkState(response.data.state);
				return response.data.relatedIDs[0];
			});
		return promise;
	};
	//-----------------------------------PERSONAL GESTION-----------------------------------------------------
	this.whoami=function(){
		this.init();
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/"+myService.tokenSession+"/whoami";
		promise=$http({
				method:"GET",
				url:urlComplete
			}).then(function(response){
				myService.checkState(response.data.state);
				myService.myProfile=response.data;
				console.log(response);
				return response.data;
			});
		return promise;
	};
	this.checkState=function(state){
		if(state == 4){
			window.location.href="/loggin";
		}
	};
	this.changePass=function(pass){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/identity/"+myService.tokenSession+"/password/"+pass;
		promise=$http({
			method:"PATCH",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			console.log(response.data);
			return response.data;
		});
		return promise;
	};
	this.changeBio=function(whoami){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/identity/"+myService.tokenSession+"/bio";
		var data=JSON.stringify(whoami);
		$http({
			method:"PATCH",
			url:urlComplete,
			data:data
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
	};
	//------------------------------------------------------ADMIN GESTION-------------------------------------------------
	this.createAccount=function(userLogin){
		var promise;
		var data=JSON.stringify(userLogin);
		urlComplete=UNIAPI_URL_API_REST+"/admin/"+myService.tokenSession+"/createAccount";
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
	this.getAllUsers=function(){
		var urlComplete=UNIAPI_URL_API_REST+"/admin/"+myService.tokenSession+"/allUsers";
		var promise;
		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
		
	};
	this.getAllProjects=function(){
		var urlComplete=UNIAPI_URL_API_REST+"/admin/"+myService.tokenSession+"/allProjects";
		var promise;
		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
		
	};
	this.getAllGroups=function(){
		var urlComplete=UNIAPI_URL_API_REST+"/admin/"+myService.tokenSession+"/allGroups";
		var promise;
		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
		
	};
	//-----------------------------------------------------PROJECT GESTION-----------------------------------------------	
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
	this.createProject=function(projectToCreate){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/project/"+myService.tokenSession+"/create";
		var data=JSON.stringify(projectToCreate);
		promise=$http({
				method : "POST",
				url : urlComplete,
				data:data			
			}).then(function(response){
				myService.checkState(response.data.state);			
				return response.data;
		});
		return promise;
	};

	//----------------------------------------GROUP GESTION----------------------------------------------------
	this.createGroup=function(groupToCreate){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/create";
		var data=JSON.stringify(groupToCreate);
		promise=$http({
				method : "POST",
				url : urlComplete,
				data:data			
			}).then(function(response){
				myService.checkState(response.data.state);			
				return response.data;
		});
		return promise;
	};
	this.upgradeMyGroups=function(){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/all";
		promise=$http({
				method : "GET",
				url : urlComplete			
			}).then(function(response){
				myService.checkState(response.data.state);
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
	//--------------------------------------------------MEMBER GROUP GESTION----------------------------------------
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
			myService.checkState(response.data.state);
			return response.data;
		
		});

		return promise;
	};
	//------------------------------------------------SUBGROUPS GESTION---------------------------------------------
	this.getSubgroups=function(id){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+id+"/subgroups";

		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});

		return promise;
	};
	this.deleteSubgroup=function(groupID,subgroupID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID+"/subgroups/"+subgroupID;
		promise=$http({
			method:"DELETE",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
	};
	this.createSubgroup=function(groupID,subgroupID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID+"/subgroups/"+subgroupID;
		promise=$http({
			method:"POST",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
	};
	//--------------------------------------------------PATH SECTION--------------------------------------
	this.getMainNotProcessePath=function(){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/path/"+myService.tokenSession+"/main";
		promise=$http({
				method:"GET",
				url:urlComplete
			}).then(function(response){
				myService.checkState(response.data.state);
				return response.data;
			});
		return promise;
	};
	this.getNotProcessePath=function(groupID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/path/"+myService.tokenSession+"/enter/"+groupID;
		promise=$http({
				method:"GET",
				url:urlComplete
			}).then(function(response){
				myService.checkState(response.data.state);
				return response.data;
			});
		return promise;
	};
	this.getMainPathGroups=function(){
		$dGroup=$q.defer();		
		myService.init();
		promesasGroup=[];
		groups=[];
		
		myService.getMainNotProcessePath().then(function(res){
			console.log(res);
			for(var i=0;i<res.groupIDs.length;i++){
				var groupID=res.groupIDs[i];
				promesasGroup.push(myService.getGroup(groupID));
			}
			$q.all(promesasGroup).then(function (groupsRes) {
				for(var i=0;i<groupsRes.length;i++){
					if(groupsRes[i].state == 0)
						groups.push(groupsRes[i].groups[0]);
				}
				console.log(groups);
				$dGroup.resolve(groups);
			});
		});

		return $dGroup.promise;
	};

	this.getMainPathProjects=function(){
		myService.init();
		$dProject=$q.defer();
		promesasProjects=[];
		projects=[];
		myService.getMainNotProcessePath()
		.then(function(res){
			for(var i=0;i<res.projectIDs.length;i++){
				var projectID=res.projectIDs[i];
				promesasProjects.push(myService.getProject(projectID));
			}
			$q.all(promesasProjects).then(function (projectsRes) {
				console.log(projectsRes.length);
				for(var i=0;i<projectsRes.length;i++){
					if(projectsRes[i].state == 0)					
						projects.push(projectsRes[i].projects[0]);
				}
				$dProject.resolve(projects);
			});
		});

		return $dProject.promise;
	};
	this.getPathGroups=function(groupID){
		$dGroupPath=$q.defer();		
		myService.init();
		promesasGroup=[];
		groups=[];
		
		myService.getNotProcessePath(groupID).then(function(res){
			console.log(res);
			for(var i=0;i<res.groupIDs.length;i++){
				var groupID=res.groupIDs[i];
				promesasGroup.push(myService.getGroup(groupID));
			}
			$q.all(promesasGroup).then(function (groupsRes) {
				for(var i=0;i<groupsRes.length;i++){
					if(groupsRes[i].state == 0)
						groups.push(groupsRes[i].groups[0]);
				}
				console.log(groups);
				$dGroupPath.resolve(groups);
			});
		});

		return $dGroupPath.promise;
	};

	this.getPathProjects=function(groupID){
		myService.init();
		$dProjectPath=$q.defer();
		promesasProjects=[];
		projects=[];
		myService.getNotProcessePath(groupID)
		.then(function(res){
			for(var i=0;i<res.projectIDs.length;i++){
				var projectID=res.projectIDs[i];
				promesasProjects.push(myService.getProject(projectID));
			}
			$q.all(promesasProjects).then(function (projectsRes) {
				console.log(projectsRes.length);
				for(var i=0;i<projectsRes.length;i++){
					if(projectsRes[i].state == 0)					
						projects.push(projectsRes[i].projects[0]);
				}
				$dProjectPath.resolve(projects);
			});
		});

		return $dProjectPath.promise;
	};	

	//------------------------------PROJECT INSIDE GROUP GESTION--------------------------------------
	this.projectsInsideGroup=function(groupID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID+"/contain/project";
		promise=$http({
			method:"GET",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise
	};
	this.putGroupProject=function(groupID,projectID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID+"/contain/project/"+projectID;
		promise=$http({
			method:"POST",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;			
		});
		return promise;
	};
	this.getProjectsInsideGroup=function(groupID){
		$d=$q.defer();
		projects=[];
		promesas=[];
		myService.projectsInsideGroup(groupID).then(function(data){		
			for(var i=0;i<data.relatedIDs.length;i++){
				var projectID=data.relatedIDs[i];
				promesas.push(myService.getProject(projectID));
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

	}
	this.deleteProjectInsideGroup=function(groupID,projectID){
		var promise;
		var urlComplete=UNIAPI_URL_API_REST+"/group/"+myService.tokenSession+"/"+groupID+"/contain/project/"+projectID;
		promise=$http({
			method:"DELETE",
			url:urlComplete
		}).then(function(response){
			myService.checkState(response.data.state);
			return response.data;
		});
		return promise;
	};

	//-----------------------------------EXECUTION SECTION-----------------------------------------------------
});
