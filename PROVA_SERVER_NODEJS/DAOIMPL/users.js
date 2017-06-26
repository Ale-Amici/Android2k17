
// This imports the model of an employee
var User = require('../models/user.js');
var CreditCard = require('../models/creditCard.js');

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
    var pool = dbHelper.getDBPool();
    var user;
    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM CUSTOMER WHERE ID = ? ", userId)
        .then(function(userRows){
            user = getUserFromDbRow(userRows[0]);
            /**************QUERY GET OpeningHour***************/
            return pool.queryAsync( " SELECT * "
                                  + " FROM CREDIT_CARD CC "
                                  + " WHERE CC.CUSTOMER_ID = ?"
                                  + " ORDER BY CC.name ASC ", userId);
          })
          .then(function(creditCardRows){
              creditCardRows.forEach(function(row,index){
                  user.creditCards.push(getCreditCardFromDbRow(row))
              })
              resolve(user);
          })
          .catch(function(err){
              console.log(err);
              reject(err);
          });
    });
    //TODO PRENDI TUTTI I DATI DELL'UTENTE
}

var updateDeviceToken = function(userId, deviceToken){
    var pool = dbHelper.getDBPool();
    return new Promise(function(resolve, reject){
        pool.queryAsync("UPDATE CUSTOMER SET device_token = ? WHERE ID = ? ",[deviceToken, userId])
        .then(function(updateResults){
              resolve(updateResults);
          })
          .catch(function(err){
              console.log(err);
              reject(err);
          });
    });
}


var getUserFromDbRow = function(row){
    return new User()
        .setId(row["ID"])
        .setUsername(row["username"])
        .setDateOfBirth(row["date_of_birth"])
        .setEmail(row["email"])
        .setPassword(row["password"])
        .setDeviceToken(row["device_token"])
        .setCreditCards([])
}

getCreditCardFromDbRow = function(row){
    return new CreditCard()
        .setId(row["ID"])
        .setName(row["name"])
}


module.exports.all           = getAllUsers;
module.exports.getUserFromId = getUserFromId;
module.exports.updateDeviceToken = updateDeviceToken;
