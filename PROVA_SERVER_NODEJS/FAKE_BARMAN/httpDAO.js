var http = require('http');
var querystring = require('querystring');


// Build the post string from an object
var post_data = querystring.stringify({
  'username' : "barman",
  'password' : "barman"
});

// An object of options to indicate where to post to
var post_options = {
  host: '151.80.152.226',
  port: '80',
  path: '/orders/getNext',
  method: 'POST',
  headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Content-Length': Buffer.byteLength(post_data)
  }
};

var sendGetNextOrder = function(){
    return new Promise(function(resolve, reject){
        var post_req = http.request(post_options, function(res) {
            res.setEncoding('utf8');
            res.on('data', function (data) {
                console.log('Response: ' + data);
                var jsonObject = JSON.parse(data);
                var order = jsonObject.order;
                console.log(order);
                if(order == undefined){ //CONTROLLO CHE L'ORDINE NON SIA VUOTO
                    resolve(order);
                }
                else{
                    reject("NO ORDER");
                }
            });
        });
    });

}

module.exports.sendGetNextOrder = sendGetNextOrder;
