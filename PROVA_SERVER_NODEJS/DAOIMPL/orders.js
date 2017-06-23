var Order = require('../models/order.js');

var OpeningHour = require("../models/openingHour.js")
var BarTable = require("../models/barTable.js")
var BarCounter = require("../models/barCounter.js")
var BarMenuItem = require("../models/barMenuItem.js")
var Ingredient = require("../models/ingredient.js")
var Addition = require("../models/addition.js")
var Size = require("../models/size.js")

var dbHelper = require('../DB/dbhelper.js');

/*
 * This function retrieves all employees
 */
var createOrder = function() {
    var pool = dbHelper.getDBPool();
    pool.query("SELECT * from CUSTOMER",function(err,rows){
        if(!err) {
            var users = [];
            console.log(rows);
            rows.forEach(function(row, index){
              users.push(
                new User()
                  .setName(row["username"])
                  .setId(row["ID"])
              );
            });
            console.log(users);
            controller(users);
        }
        else{
          controller(null);
        }
    });
}

var getUserFromId = function(userId){
    //TODO PRENDI TUTTI I DATI DELL'UTENTE
}




module.exports.createOrder = createOrder;
