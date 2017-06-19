var express   =    require("express");
//var logger    =    require('./UTILS/log.js');

var app       =    express();

// Notifications routes
var notificationsRoutes = require("./routes/users.js")
app.use("/notifications", notificationsRoutes);

var usersRoutes = require("./routes/users.js")
app.use("/users", usersRoutes);


var barsRoutes = require("./routes/bars.js");
app.use("/bars", barsRoutes);

/*
app.get("/",function(req,res){-
        handle_database(req,res);
});
*/
app.listen(80);

console.info("aperto alla porta 80");
