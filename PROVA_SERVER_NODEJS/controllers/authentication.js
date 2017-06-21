// Import to use in this file
var passport  =  require("../passportConfig.js")
var authenticationDAOImpl = require('../DAOIMPL/authentication.js');
var User = require('../models/user.js');

//percorso "/bars"
function login(request, response) {
    console.log("LOGIN");
    passport.authenticate('local', function (err,user,msg) {
        console.log("err")
        console.log(err)
        console.log("user")
        console.log(user)
        console.log("msg")
        console.log(msg)

        if(err == null && user != false){
            response.status(200).json(user);
        }else{
            response.status(401).json(msg);
        }
    })(request, response);
}


module.exports.login = login;
