var authenticationDAOImpl = require("./DAOIMPL/authentication.js");
var crypto = require('crypto')
var passport = require('passport')
  , LocalStrategy = require('passport-local').Strategy
  , JsonStrategy = require('passport-json').Strategy;

var salt = "iDaBere2k17";

var passportAuthFunction = function(username, password, done) {

    const hash = crypto.createHash('sha256');
    console.log("LocalStrategy");
    authenticationDAOImpl.findOne( username)
    .then( function (user) {
        console.log(user);
        var passHash = hash.update(salt + password).digest('hex');
        if (!user) {
            return done(null, false, { message: 'Incorrect username.' });
        }
        console.log("PASSS: "+ passHash + "\n = " + user.password);
        if (user.password != passHash) {
            return done(null, false, { message: 'Incorrect password.' });
        }
        return done(null, user);
    })
    .catch(function(err){
        return done(err);
    });
}


//LE DUE STRATEGIE DI AUTENTICAZIONE
passport.use(new LocalStrategy( passportAuthFunction ));

passport.use(new JsonStrategy( passportAuthFunction ));
///////////////////////////////////


passport.serializeUser(function(user, done) {
  done(null, user);
});

passport.deserializeUser(function(user, done) {
  done(null, user);
});

module.exports = passport;
