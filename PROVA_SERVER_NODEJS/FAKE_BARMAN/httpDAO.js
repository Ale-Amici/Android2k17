var requestp = require('request-promise-native');

// Build the post string from an object
var post_data = {
  'username' : "barman",
  'password' : "barman"
};


var post_getNext_options = {
    method: 'POST',
    uri: 'http://151.80.152.226/orders/getNext',
    body: {
        'username' : "barman",
        'password' : "barman"
    },
    resolveWithFullResponse: true ,
    json: true // Automatically stringifies the body to JSON
};
var sendGetNextOrder = function(){
    return new Promise(function(resolve, reject){
        requestp(post_getNext_options)
            .then(function (response) {
                console.log(response.body.id);
                var order = {};
                order.id = response.body.id;
                order.status = response.body.status;
                console.log("MMMMMMMMMMMMMMM")
                resolve(order);
            })
            .catch(function (err) {
                reject("BAAAAH");
            });
    });
}


var sendUpdateStatus = function(orderId, newStatus){
    return new Promise(function(resolve, reject){

        var myPostData = JSON.parse(JSON.stringify(post_data))
        myPostData.newStatus = newStatus;
        var post_updateStatus_options = JSON.parse(JSON.stringify(post_getNext_options))
        post_updateStatus_options.uri = 'http://151.80.152.226/orders/updateStatus/' + orderId;
        post_updateStatus_options.body = myPostData;
        requestp(post_updateStatus_options)
            .then(function (response) {
                console.log(response.body);
                console.log("AAAA  " + response.body.affectedRows);
                if(response.body.affectedRows == 1){ //CONTROLLO CHE L'ORDINE NON SIA VUOTO
                    console.log("AAAAA")
                    resolve("GOOD");
                }
            })
            .catch(function (err) {
                reject("NO ORDER");
            });
    });
}


module.exports.sendGetNextOrder = sendGetNextOrder;
module.exports.sendUpdateStatus = sendUpdateStatus;
