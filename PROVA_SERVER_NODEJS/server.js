var express   =  require("express");
var passport  =  require("./passportConfig.js");
var bodyParser = require("body-parser");
//var logger    =    require('./UTILS/log.js');
//var session = require('express-session')

var app       =    express();
//app.use(session({ secret: 'xxxxxxx' }));

app.use(bodyParser.json())          // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
}));
app.use(passport.initialize());
//app.use(passport.session());



var usersRoutes = require("./routes/users.js")
app.use("/users", usersRoutes);


var barsRoutes = require("./routes/bars.js");
app.use("/bars", barsRoutes);

var authenticationRoutes = require("./routes/authentication.js");
app.use("/authentication", authenticationRoutes);

var ordersRoutes = require("./routes/orders.js");
app.use("/orders", ordersRoutes);

/*
app.get("/",function(req,res){-
        handle_database(req,res);
});
*/
app.listen(80);

console.info("aperto alla porta 80");
