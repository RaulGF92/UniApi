
var PORT=3000;
var PAGE_SITE='views';

//-------------------------------------------------------

var express=require('express');
var app=express();
var fs=require('fs');

var createAccount=require('./routes/createAccount.js');


//-----------------Direccionamiento---------------------
app.get('/',function(req,res){
	var text=fs.readFileSync(PAGE_SITE+'/dashboard.html','utf8');
        res.send(text);
});

app.get('/loggin',function(req,res){
	var text=fs.readFileSync(PAGE_SITE+'/loggin.html','utf8');
        res.send(text);
});

app.get('/pageBlank',function(req,res){
	var text=fs.readFileSync(PAGE_SITE+'/PageBlank.html','utf8');
	res.send(text);
});


app.use('/createAccount',createAccount);

app.listen(3000,function(){
	console.log("---------------------------------------------");
	console.log("                                             ");
	console.log("		UniApi-WebPlataform		  ");
	console.log("                                             ");
	console.log("                raulgf92                     ");
	console.log("                                             ");
	console.log("---------------------------------------------");
	console.log("                                             ");
	console.log('WebPlataform listenning on port '+PORT);
        console.log("                                             ");
	console.log("Let's start the party!!!");
});

