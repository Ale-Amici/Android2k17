
var dbHelper = require('../DB/dbhelper.js');
var User = require('../models/user.js');


var findOne = function(username){
    return new Promise(function(resolve, reject){
        var pool = dbHelper.getDBPool();
        var user = undefined;

        pool.queryAsync(" SELECT * "
                      + " FROM CUSTOMER WHERE username = ? "
                      + " UNION "
                      + " SELECT * "
                      + " FROM CUSTOMER WHERE email = ?", [username, username])
        .then(function(userRows){
            if(userRows.length > 0){
                user = getUserFromDbRow(userRows[0]);
            }
            resolve(user);

        })
        .catch(function(err){
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

module.exports.findOne = findOne;
