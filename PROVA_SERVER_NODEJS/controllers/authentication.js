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
            userPromise = usersDAOImpl.getUserFromId(user.id);
            userPromise.then(function(newUser){
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
