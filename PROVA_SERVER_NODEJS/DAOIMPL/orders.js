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
var createOrder = function(customer, order, bar) {
    var pool = dbHelper.getDBPool();

    return new Promise(function(resolve, reject){
        pool.queryAsync(" SELECT BHDP.ID"
        + " FROM BAR_HAS_DELIVERY_PLACE BHDP"
        + " WHERE  BAR_ID = ? AND DELIVERY_PLACE_ID = ?",
        [bar.id, order.choosenDeliveryPlace.id])
        .then(function(rows){
            var choosenDeliveryPlaceId = rows[0]["ID"];
            return pool.queryAsync("INSERT INTO CUSTOMER_ORDER ( "
            + " CUSTOMER_ID, "
            + " BAR_HAS_DELIVERY_PLACE_ID, "
            + " is_paid, "
            + " process_status, "
            + " creation_date, "
            + " destroy_code ) "
            + " VALUES ?,?,?,?,?,? ",
            [
                customer.id,
                choosenDeliveryPlaceId,
                order.isPaid,
                OrderStatus.PAYMENT_IN_PROGRESS, //DEFINIRE I PROCESS STATUS
                order.creationDate,
                order.destroyCode
            ]);
        }).then(function(rows){

        })
        .catch(function(err){
            reject("ERRORE");
        });
    });
}

var getOrderFromId = function(orderId){
    //TODO PRENDI TUTTI I DATI DELL'ORDER
}
 var deleteOrderFromId = function(orderId, destroyCode){
     //Elimino l'ordine con quell'ID se il codice matcha
 }



module.exports.createOrder    = createOrder;
module.exports.getOrderFromId = getOrderFromId;
module.exports.deleteOrderFromId = deleteOrderFromId;
