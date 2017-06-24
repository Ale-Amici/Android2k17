 // Import to use in this file
var passport  =  require("../passportConfig.js")
var authenticationDAOImpl = require('../DAOIMPL/authentication.js');
var usersDAOImpl = require('../DAOIMPL/users.js');
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
            usersDAOImpl.updateDeviceToken(user.id, request.body.deviceToken)
            .then(function(updateResults){
                console.log("RISULTATI UPDATE")
                console.log(updateResults)
                return usersDAOImpl.getUserFromId(user.id);
            }).then(function(newUser){
                response.status(200).json(newUser);
            })
            .catch(function(){
                response.status(500).json("ERRORE NEL RECUPERARE LE INFORMAZIONI DELL'UTENTE")
            });
        }else{
            response.status(401).json(msg);
        }
    })(request, response);
}


module.exports.login = login;
