var express   =    require("express");
var logger    =    require('.UTILS/log.js');

var app       =    express();




var usersRoutes = require("./routes/users.js")
app.use("/users", usersRoutes);
/*
app.get("/",function(req,res){-
        handle_database(req,res);
});
*/
app.listen(80);

logger.info("aperto alla porta 80");
