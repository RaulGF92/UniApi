var PAGE_SITE="views";

var colors = require('colors');
var express=require('express');
var router= express.Router();
var bodyParser = require("body-parser");
var multer = require('multer');
var fs=require('fs');
var domain=require('./domainApp.js');
var http=require('http');
var qs = require('querystring');

router.get('/',function(req,res){
	var text=fs.readFileSync(PAGE_SITE+'/CreateUserForm.html','utf8');
	res.send(text);
});

router.get('/new',function(req,res){
	if(req.query.password != req.query.repassword){
		res.redirect('/createAccount');
		return;
	}
	req.query.birthday=new Date(req.query.birthday).toString().toString();;	
	console.log(colors.blue('CreateAccount')+': we start created a new account ['+new Date()+']');
	var person= new domain.person(req.query.name,req.query.subname,req.query.birthday,req.query.country,req.query.province,req.query.birthplace,req.query.biografy,req.query.profileImageUrl);
	var userLogin= new domain.userSystem(req.query.email,req.query.password,"user",new Date());
	console.log(" ");
	console.log('this are the account, that we are try created:');
	console.log(person);
	console.log(userLogin);
	http.get({
			host:'localhost',
			port:'8080',
			path:'/'+userLogin.email+'/'+userLogin.password
		},function(response) {
        		// Continuously update stream with data
        		var body = '';
        		
			response.on('data', function(d) {
            			body += d; 
			});

			response.on('end', function() {

                        	// Data reception is done, do whatever with it!
                        	var parsed = JSON.parse(body);
                        	if(parsed.state == 2 || parsed.state == 0){
					console.log("");
					console.log("Created the account --- "+colors.red("WRONG"));
					res.redirect('/createAccount');
			                return;
				}else{
					data=qs.stringify(req.query);
					console.log(data);
					http.get({
						host:'localhost',
						port:'8080',
						path:'/createAccount?'+data
					},function(response){
						var body = '';
						response.on('data',function(d){
							body+=d;
						});
						response.on('end',function(){
							msg=JSON.parse(body);
							if(msg.state == 0){
								res.redirect('/loggin');
							}else{
								res.redirect('/createAccount');
							}
						});
					});
				}
			});
        	});
	

});

module.exports=router;
