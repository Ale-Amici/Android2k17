var Order = require('../models/order.js');
var httpDAO  = require("./httpDAO.js");
////////////SLEEP FUNCTIONALITY///////////////
var sleepModule = require('sleep');

var sleep = function(millisec, callback){
    sleepModule.msleep(millisec);
    callback();
}
//////////////////////////////////////////////

var nextOrder = undefined;
var working = false;

var retrieveNextOrder = function(){
    return new Promise(function(resolve,reject){
        var nextOdrerPromise = httpDAO.sendGetNextOrder();
        nextOdrerPromise.then(function(order){
            nextOrder = order;
            resolve();
        }).catch(function(err){
            nextOrder = undefined;
            reject(err);
        })
    })
}


while (true){
    sleep(5000, function() {
        if(working == false){
            working = true;
            console.log("ESEGUITO")
            retrieveNextOrder()
            .then(function(){
                //c'è un nuovo ordine
                //TODO INIZIARE PROCEDURA DI COMPLETAMENTO
                working = false;
            })
            .catch(function(err){
                console.log(err);
                working = false;
            })
        }
    });
}
