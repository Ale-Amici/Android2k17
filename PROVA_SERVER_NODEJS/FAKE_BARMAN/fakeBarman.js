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
                setTimeout(resolve, 3000); // (A)
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
                    resolve(res);
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

var deliverOrder = function(){
    return new Promise(function(resolve,reject){
        new Promise(function (resolve, reject) {
            setTimeout(resolve, 3000); // (A)
        }).then(function(res){
            return httpDAO.sendGetOrderFromId(nextOrder.id)
        }).then(function(order){
            if(order == undefined) order = {};
            if(order.status != OrderStatus.COMPLETED){
                deliverOrder()
                .then(function(res){
                    resolve(res);
                })
            }
            else{
                nextOrder.status = order.status ;
                resolve(order.status);
            }
        }).catch(function(err){
            console.log("ERRORE DELIVERY:" + err)
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
            return deliverOrder()
        }).then(function(res){
            resolve(res);
        }).catch(function(err){
            console.log(err);
            reject("errr")
        })
    })
}

var start = function(){
    if(working == false){
        working = true;
        new Promise(function (resolve, reject) {
            setTimeout(resolve, 3000); // (A)
        }).then(function(res){
            return getWorkPromise()
        }).then(function(res){
            working = false;
            nextOrder = undefined;
        })
        .catch(function(err){
            working = false;
            nextOrder = undefined;
        })
    }
    setTimeout(start, 1000);
}

start();
