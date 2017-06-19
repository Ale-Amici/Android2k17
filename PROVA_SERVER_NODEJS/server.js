var express   =    require('express');
//var logger    =    require('./UTILS/log.js');

var app       =    express();

var usersRoutes = require("./routes/users.js")
app.use("/users", usersRoutes);


var barsRoutes = require("./routes/bars.js");
app.use("/bars", barsRoutes);

// Notifications routes
var notificationsRoutes = require("./routes/notifications.js")
app.use("/notifications", notificationsRoutes);


/*
app.get("/",function(req,res){-
        handle_database(req,res);
});
*/
app.listen(8080);

console.info("aperto alla porta 8080");
