var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');
const e = require('express');

//connect to mysql
var con = mysql.createConnection({
	 host:'localhost',
	 user: 'root',
	 password:null
});

con.connect(function(err) {
	if (err) {
		console.log(err.message);
	}
	else {
			console.log("Connected!")
			//con.query("DROP DATABASE IF EXISTS demonodejs")
			con.query("CREATE DATABASE IF NOT EXISTS demonodejs", function (err, result) {
				if (err) {
					console.log(err.message);
				}
				else {
				console.log("Database Created!")
				}
			});

			con.query("USE demonodejs",function (err, result) {
				if (err) {
					console.log(err.message);
				}
				else {
					console.log("USING demonodejs")
				}
			});

			let USER_TABLE = `CREATE TABLE IF NOT EXISTS user(
				unique_id varchar(255) not null,
				name varchar(255) not null,
				email varchar(255) not null,
				encrypted_password varchar(255) not null,
				salt varchar(255) not null
			)`;
		
			con.query(USER_TABLE, function(err, results, fields) {
				if (err) {
					console.log(err.message);
				}
				else {
					console.log("Table Created!")
				}
			});
	  }
});

var genRandomString=function(length){
	return crypto.randomBytes(Math.ceil(length/2))
	.toString('hex')
	.slice(0,length);
};
 
var sha512 = function(password,salt){
	var hash=crypto.createHmac('sha512',salt);
	hash.update(password);
	var value = hash.digest('hex');
	return{
		salt:salt,
		passwordHash:value
	};
};
 
function saltHashPassword(userPassword){
	var salt=genRandomString(16);
	var passwordData = sha512(userPassword,salt);
	return passwordData;
}

function checkHashPassword(userPassword,salt)
{
	var passwordData = sha512(userPassword,salt);
	return passwordData;
}
 
 
var app=express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({exteded: true}));

app.post('/register/',(req,res,next)=>{
	
	var post_data = req.body;
	var uid = uuid.v4();
	var plain_password=post_data.password;
	var hash_data = saltHashPassword(plain_password);
	var password = hash_data.passwordHash;	
	var salt=hash_data.salt;
	var name=post_data.name;
	var email=post_data.email;

	con.query('SELECT * FROM user where email=?',[email], function (err, result) {
		if (err) {
			res.json(err.message)
		}
		else {
			if(result && result.length) {
				res.json('User Already Exists !!!');
			}	
			else
			{
				con.query('INSERT INTO `user`(`unique_id`, `name`, `email`, `encrypted_password`,`salt`) VALUES (?,?,?,?,?)',[uid,name,email,password,salt], function (err, result) {
				if (err) {
					res.json(err.message)
				}
				else {
					res.json('Register Successful!')
				}
				});
			}	
		}
	});
});

app.post('/login/',(req,res,next)=>{
	
	var post_data = req.body;
	var plain_password=post_data.password;
	var email = post_data.email;
	con.query('SELECT * FROM user WHERE email=?',[email], function (err, result) {
		if (err) {
			res.json(err.message)
		}
		else {
			if(result && result.length)
			{
				var salt = result[0].salt;
    			var encrypted_password = result[0].encrypted_password;
    			var hashed_password = checkHashPassword(plain_password,salt).passwordHash;
    			if(encrypted_password == hashed_password){
					res.send(result)
				}
    			else{
					res.json("Wrong Password!")
				}
			}
			else {
				res.json("User Not Found!")
			}
		}
	});	
});

app.post('/checkUser/',(req,res,next)=>{
	
	var post_data = req.body;
	var email = post_data.email;
	con.query('SELECT * FROM user WHERE email=?',[email], function (err, result) {
		if (err) {
			res.json(err.message)
		}
		else {
			if(result && result.length)
			{
				res.json("User Found!")
			}
			else {
				res.json("User Not Found!")
			}
		}
	});	
});

app.post('/updatePSWD/',(req,res,next)=>{
	
	var post_data = req.body;
	var plain_password=post_data.password;
	var hash_data = saltHashPassword(plain_password);
	var password = hash_data.passwordHash;	
	var salt=hash_data.salt;
	var email = post_data.email;
	con.query('SELECT * FROM user WHERE email=?',[email], function (err, result) {
		if (err) {
			res.json(err.message)
		}
		else {
			if(result && result.length)
			{
				con.query('UPDATE user SET encrypted_password=?, salt=? WHERE email=?',[password,salt,email], function (err, result) {
					if (err) {
						res.json(err.message)
					}
					else {
						res.json('Reset Password Successful!')
					}
					});
			}
			else {
				res.json("User Not Found!")
			}
		}
	});	
});

app.post('/updateUser/',(req,res,next)=>{
	
	var post_data = req.body;
	var plain_password=post_data.password;
	var hash_data = saltHashPassword(plain_password);
	var password = hash_data.passwordHash;	
	var salt=hash_data.salt;
	var email = post_data.email;
	var name = post_data.name;
	con.query('SELECT * FROM user WHERE email=?',[email], function (err, result) {
		if (err) {
			res.json(err.message)
		}
		else {
			if(result && result.length)
			{
				con.query('UPDATE user SET name=?, encrypted_password=?, salt=? WHERE email=?',[name,password,salt,email], function (err, result) {
					if (err) {
						res.json(err.message)
					}
					else {
						res.json('Update Profile Successful!')
					}
					});
			}
			else {
				res.json("User Not Found!")
			}
		}
	});	
});
 
 
 //app.get("/",(req,res,next)=>{
//	 console.log('Password:123456');
//	 var encrypt = saltHashPassword("123456");
//	 console.log('Encrypt: '+encrypt.passwordHash);
//	 console.log('salt: '+encrypt.salt);
// })
 
 app.listen(3000,()=>{
	 console.log('EDMTDev Restful running on port 3000');
 })
	 