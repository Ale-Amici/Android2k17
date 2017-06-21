var express   =  require("express");
var passport  =  require("./passportConfig.js")
//var logger    =    require('./UTILS/log.js');
//var session = require('express-session')

var app       =    express();
//app.use(session({ secret: 'xxxxxxx' }));

app.use(passport.initialize());
//app.use(passport.session());



var usersRoutes = require("./routes/users.js")
app.use("/users", usersRoutes);


var barsRoutes = require("./routes/bars.js");
app.use("/bars", barsRoutes);

var authenticationRoutes = require("./routes/authentication.js");
app.use("/authentication", authenticationRoutes);

/*
app.get("/",function(req,res){-
        handle_database(req,res);
});
*/
app.listen(80);

console.info("aperto alla porta 80");
