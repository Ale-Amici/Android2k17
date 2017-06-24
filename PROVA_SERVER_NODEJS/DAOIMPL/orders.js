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
            + " destroy_code, "
            + " customer_session_token) "
            + " VALUES (?,?,?,?,?,?,?,?,?,?)",
            [
                customer.id,
                chosenDeliveryPlaceId,
                order.chosenCreditCard.id,
                order.usingCreditCard,
                order.totalPrice,
                order.isPaid,
                OrderStatus.PAYMENT_IN_PROGRESS, //DEFINIRE I PROCESS STATUS
                order.creationDate,
                generateDestroyCode(),
                order.customerSessionToken
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
        pool.queryAsync("SELECT * FROM CUSTOMER_ORDER WHERE ID = ? ", orderId)
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
 var deleteOrderFromId = function(orderId, destroyCode){
     //Elimino l'ordine con quell'ID se il codice matcha
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
         .setChosenCreditCard({})
         .setChosenDeliveryPlace({})
         .setCustomerSessionToken(row["customer_session_token"])
         .setDestroyCode(row["destroy_code"])
 }


module.exports.createOrder    = createOrder;
module.exports.getOrderFromId = getOrderFromId;
module.exports.deleteOrderFromId = deleteOrderFromId;
