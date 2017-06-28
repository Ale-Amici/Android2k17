var notificationsCtrl = require("../controllers/notifications.js");

var Order = require('../models/order.js');
var OrderStatus = require('../models/orderStatus.js');

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
var createOrder = function(customer, order) {
    var pool = dbHelper.getDBPool();

    return new Promise(function(resolve, reject){
        var p = pool.queryAsync(" SELECT BHDP.ID"
        + " FROM BAR_HAS_DELIVERY_PLACE BHDP"
        + " WHERE  BAR_ID = ? AND DELIVERY_PLACE_ID = ?",
        [order.chosenBarId, order.chosenDeliveryPlace.id])
        .then(function(rows){
            var chosenDeliveryPlaceId = rows[0]["ID"];
            if(order.chosenCreditCard == undefined){
                order.chosenCreditCard = {}
            }
            return pool.queryAsync("INSERT INTO CUSTOMER_ORDER ( "
            + " CUSTOMER_ID, "
            + " BAR_HAS_DELIVERY_PLACE_ID, "
            + " CREDIT_CARD_ID, "
            + " using_credit_card, "
            + " total_price, "
            + " is_paid, "
            + " status, "
            + " creation_date, "
            + " destroy_code) "
            + " VALUES (?,?,?,?,?,?,?,?,?)",
            [
                customer.id,
                chosenDeliveryPlaceId,
                order.chosenCreditCard.id,
                order.usingCreditCard,
                order.totalPrice,
                order.isPaid,
                OrderStatus.PAYMENT_IN_PROGRESS, //DEFINIRE I PROCESS STATUS
                new Date(),
                generateDestroyCode()
            ]);
        }).then(function(results){
            console.log("INSERIMENTO");
            console.log(results);
            resolve(results.insertId);
        })
        .catch(function(err){
            console.log(err);
            reject("ERRORE");
        });
    });
}

var getOrderFromId = function(orderId){
    var pool = dbHelper.getDBPool();
    var order;
    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM CUSTOMER_ORDER WHERE ID = ?", orderId)
        .then(function(orderRows){
            order = getOrderFromDbRow(orderRows[0]);
            resolve(order);
        })
        .catch(function(err){
            console.log(err);
            reject(err);
        });
    });
}

var getNextOrder = function(){
    var pool = dbHelper.getDBPool();
    var order;
    return new Promise(function(resolve, reject){
        pool.queryAsync(
            " SELECT * FROM CUSTOMER_ORDER WHERE status = ? "
            + " ORDER BY creation_date ASC "
            + " LIMIT 1",OrderStatus.PAYMENT_IN_PROGRESS )
            .then(function(orderRows){
                if(orderRows.length > 0 ){
                    order = getOrderFromDbRow(orderRows[0]);
                    console.log(order);
                    return pool.queryAsync(" UPDATE CUSTOMER_ORDER SET status=? WHERE status=? AND ID=? ",
                        [ OrderStatus.IN_QUEUE, OrderStatus.PAYMENT_IN_PROGRESS, order.id ])
                    }
                    else{
                        reject("NO ORDER")
                    }
                }).then(function(results){
                    console.log(results)
                    if(results.affectedRows == 1){
                        resolve(order);
                    }
                    else{
                        reject("ERROR")
                    }
                })
                .catch(function(err){
                    console.log(err)
                })
    });
}

var updateOrderStatus = function(orderId, status){
    var pool = dbHelper.getDBPool();
    var response;
    return new Promise(function(resolve, reject){
        pool.queryAsync("UPDATE CUSTOMER_ORDER SET status = ? WHERE ID = ?",
         [status, orderId])
        .then(function(results){
            response = results;
            return pool.queryAsync("SELECT * FROM CUSTOMER CC JOIN CUSTOMER_ORDER CO ON(CC.ID = CO.CUSTOMER_ID) WHERE CO.ID = ?",orderId);
        }).then(function(userRows){
            if(userRows.length > 0){
                if(userRows[0]["device_token"] != undefined) {
                    return notificationsCtrl.updateStatusPushPromise(status, userRows[0]["device_token"]);
                }
                else{
                    resolve(response)
                }
            }
            else{
                resolve(response)
            }
        }).then(function(userRows){
            resolve(response);
        }).catch(function(err){
            console.log(err);
            reject(err);
        })
    });
}

var checkOrderReady = function(orderId, destroyCode){
    var pool = dbHelper.getDBPool();
    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT status FROM CUSTOMER_ORDER "
        + " WHERE ID = ?", orderId)
        .then(function(orderRows){
            if(orderRows.length > 0){
                if(orderRows[0].destroy_code != destroyCode){
                    reject("INCORRECT DESTROY CODE")
                }
                var result = orderRows[0].status == OrderStatus.READY;
                resolve(result);
            }
            reject("NESSUN ORDINE CON QUESTO ID")
        }).catch(function(err){
            console.log(err);
            reject(err);
        })
    });
}


var generateDestroyCode = function(){
    //GENERARE UNA STRINGA CASUALE
    return "DESTROY_124234";
}


var getOrderFromDbRow = function(row){
    return new Order()
    .setId(row["ID"])
    .setStatus(row["status"])
    .setCreationDate(row["creation_date"])
    .setIsPaid(row["is_paid"])
    .setTotalPrice(row["total_price"])
    .setOrderItems([])
    .setUsingCreditCard(row["using_credit_card"])
    //.setChosenCreditCard({})
    //.setChosenDeliveryPlace({})
    .setDestroyCode(row["destroy_code"])
}


module.exports.createOrder    = createOrder;
module.exports.getOrderFromId = getOrderFromId;
module.exports.getNextOrder = getNextOrder;
module.exports.updateOrderStatus = updateOrderStatus;
module.exports.checkOrderReady = checkOrderReady;
