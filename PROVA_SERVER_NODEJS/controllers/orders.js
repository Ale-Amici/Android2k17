// Import to use in this file
var ordersDAOImpl = require('../DAOIMPL/orders.js');
var Order = require('../models/order.js');
var passport  =  require("../passportConfig.js")


//percorso "/orders/create"
function createOrder(request, response) {
    var customer;
    passport.authenticate('json', function (err,user,msg) {
        console.log("err")
        console.log(err)
        console.log("user")
        console.log(user)
        console.log("msg")
        console.log(msg)

        if(err == null && user != false){
            customer = user;
            console.log("BODY");
            console.log(request.body);
            orderCreationPromise = ordersDAOImpl.createOrder(customer, request.body.order)
            orderCreationPromise.then(function(orderId){
                //ordine aggiunto al db
                return ordersDAOImpl.getOrderFromId(orderId);
            }).then(function(order){
                response.status(200).json(order);
            }).catch(function(err){
                console.log(err);
                response.status(500).json("ERRORE NELL'INSERIRE L'ORDINE")
            });
        }else{
            response.status(401).json(msg);
        }
    })(request, response);
}

//percorso /orders/getNext
function getNextOrder(request, response){
    console.log("GET NEXT ORDER");
    passport.authenticate('BARMAN', function (err,user,msg) {
        if(err == null && user != false){
            nextOrderPromise = ordersDAOImpl.getNextOrder()
            nextOrderPromise.then(function(order){
                response.status(200).json(order);
            }).catch(function(err){
                console.log(err);
                response.status(500).json("ERRORE NEL PRENDERE IL NEXT ORDER")
            })
        }
        else{
            response.status(500).json("ERRORE di autenticazione")
        }
    })(request, response);
}

function updateStatus(request, response){
    newStatus = request.body.newStatus;

}

//percorso /orders/:order_id
function getOrderFromId(request, response){

}

//percorso /orders/:order_id/:destroy_code
function deleteOrder(request, response){


}



module.exports.createOrder = createOrder;
module.exports.getOrderFromId = getOrderFromId;
module.exports.deleteOrder = deleteOrder;
module.exports.getNextOrder = getNextOrder;
