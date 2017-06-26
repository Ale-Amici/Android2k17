var Order = require('../models/order.js');
var OrderStatus = require('../models/orderStatus.js');
var httpDAO  = require("./httpDAO.js");


var nextOrder = undefined;
var working = false;

var retrieveNextOrder = function(){
    return new Promise(function(resolve,reject){
        console.log("Chiesto next ORder")
        var nextOdrerPromise = httpDAO.sendGetNextOrder();
        nextOdrerPromise.then(function(order){
            nextOrder = order;
            console.log("ORDINE");
            console.log(order)
            resolve(order);
        }).catch(function(err){
            console.log("AGW$GWRTWRT");
            nextOrder = undefined;
            reject(err);
        })
    })
}

var updateStatus = function(orderId,newStatus){

        return new Promise(function(resolve,reject){
            new Promise(function (resolve, reject) {
                setTimeout(resolve, 1000); // (A)
            }).then(function(res){
                return httpDAO.sendUpdateStatus(orderId,newStatus);
            }).then(function(response){

                console.log( "updateStatus OK")
                nextOrder.status = newStatus;
                console.log( "updateStatus OK")
                resolve("OOOOK");
            }).catch(function(err){
                nextOrder = undefined;
                resolve("err");
            })
        })
    //})
}
var getNewStatus = function(){
    var i;
    for( i = 0; i < OrderStatus.ARRAY.length; i ++){
        if(OrderStatus.ARRAY[i] == nextOrder.status) break;
    }
    if( i  ==  OrderStatus.ARRAY.length -1){
        i --;
    }
    return OrderStatus.ARRAY[i+1];
}
var prepareOrder = function(){
    return new Promise(function(resolve,reject){
        var newStatus = getNewStatus();
        updateStatus(nextOrder.id,newStatus).then(function(response){
            console.log("ARDAMOOOOO")
            if(nextOrder.status != OrderStatus.READY){
                prepareOrder()
                .then(function(res){
                    resolve("res");
                });
            }
            else{
                resolve("END")
            }
        }).catch(function(err){
            nextOrder = undefined;
            reject(err);
        })
    })
}

var getWorkPromise = function(){
    return new Promise(function(resolve,reject){
        console.log("ESEGUITO")
        retrieveNextOrder()
        .then(function(){
            return prepareOrder()
        }).then(function(res){
            resolve(res);
        }).catch(function(err){
            console.log(err);
            working = false;
            reject("errr")
        })
    })
}

var start = function(){
    if(working == false){
        working = true;
        getWorkPromise().then(function(res){
            working = false;
        })
        .catch(function(err){
            working = false;
        })
    }
    setTimeout(start, 1000);
}

start();
