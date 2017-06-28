var User = require('../models/user.js');
var CreditCard = require('../models/creditCard.js');
var BarMenuItem = require("../models/barMenuItem.js");

var dbHelper = require('../DB/dbhelper.js');

var all = function(userId){
    var pool = dbHelper.getDBPool();

    var preferredList = [];
    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM CUSTOMER_PREFER_GLOBAL_MENU_ITEM WHERE CUSTOMER_ID = ?", userId)
        .then(function(preferredRows){
            preferredRows.forEach(function(row,index){
                preferredList.push(new BarMenuItem().setId(row["GLOBAL_MENU_ITEM_ID"]))
            })
            return pool.queryAsync("SELECT * FROM CUSTOMER_PREFER_MENU_ITEM WHERE CUSTOMER_ID = ?", userId)
        }).then(function(preferredRows){
            preferredRows.forEach(function(row,index){
                preferredList.push(new BarMenuItem().setId(row["MENU_ITEM_ID"]))
            })
            resolve(preferredList);
        })
        .catch(function(err){
            console.log(err);
            reject(err);
        });
    });
}

var add = function(userId, itemId){
    var pool = dbHelper.getDBPool();

    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM MENU_ITEM WHERE ID = ?", itemId)
        .then(function(itemRows){
            globalItemId = itemRows[0]["GLOBAL_MENU_ITEM_ID"];
            var query;
            var prefferedItemId;
            if(globalItemId == undefined){
                query = "INSERT INTO CUSTOMER_PREFER_GLOBAL_MENU_ITEM (GLOBAL_MENU_ITEM_ID,CUSTOMER_ID) VALUES (?,?) ";
                prefferedItemId = globalItemId;
            }
            else{
                query = "INSERT INTO CUSTOMER_PREFER_MENU_ITEM (MENU_ITEM_ID,CUSTOMER_ID) VALUES (?,?) ";
                prefferedItemId = itemId;
            }
            return pool.queryAsync(query, [prefferedItemId, userId])
        }).then(function(results){
            resolve(results);
        })
        .catch(function(err){
            console.log(err);
            reject(err);
        });
    });
}

var remove = function(userId, itemId){
    var pool = dbHelper.getDBPool();

    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM MENU_ITEM WHERE ID = ?", itemId)
        .then(function(itemRows){
            globalItemId = itemRows[0]["GLOBAL_MENU_ITEM_ID"];
            var query;
            var prefferedItemId;
            if(globalItemId == undefined){
                query = "DELETE FROM CUSTOMER_PREFER_GLOBAL_MENU_ITEM WHERE GLOBAL_MENU_ITEM_ID=? AND CUSTOMER_ID=?";
                prefferedItemId = globalItemId;
            }
            else{
                query = "DELETE FROM CUSTOMER_PREFER_MENU_ITEM WHERE MENU_ITEM_ID=? AND CUSTOMER_ID=?";
                prefferedItemId = itemId;
            }
            return pool.queryAsync(query, [prefferedItemId, userId])
        }).then(function(results){
            resolve(results);
        })
        .catch(function(err){
            console.log(err);
            reject(err);
        });
    });
}


module.exports.add    = add;
module.exports.remove = remove;
module.exports.all = all;
