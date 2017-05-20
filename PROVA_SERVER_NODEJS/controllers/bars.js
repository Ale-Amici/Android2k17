// Import to use in this file
var barsDAOImpl = require('../DAOIMPL/bars.js');
var Bars = require('../models/bar.js');

//percorso "/bars"
function index(request, response) {
    var latitude = request.query.latitude;
    var longitude = request.query.longitude;
    var p = request.query.p;

    if(isNaN(latitude) && isNaN(longitude)){
        barsPromise = barsDAOImpl.getBars(latitude, longitude);
        barsPromise.then(function(res){
          response.json(res);
        })
        .catch(function(err){
            console.log("shit");
        });
    }
    else if(!isNaN(latitude) && !isNaN(longitude)){
        barsPromise = barsDAOImpl.getBarsFromPosition(latitude, longitude);
        barsPromise.then(function(res){
          response.json(res);
        })
        .catch(function(err){
            console.log("shit");
        });
    }
    else{
        response.status(500).send("BAD REQUEST");
    }
}


module.exports.index = index;
