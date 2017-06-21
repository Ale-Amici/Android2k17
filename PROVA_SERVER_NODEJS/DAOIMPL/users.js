
// This imports the model of an employee
var User = require('../models/user.js');
var dbHelper = require('../DB/dbhelper.js');

/*
 * This function retrieves all employees
 */
function getAllUsers(controller) {
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




module.exports.all = getAllUsers;
