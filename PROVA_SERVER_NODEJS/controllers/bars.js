// Import to use in this file
var barsDAOImpl = require('../DAOIMPL/bars.js');
var Bars = require('../models/bar.js');

//percorso "/bars"
function index(request, response) {
    var latitude = request.query.latitude;
    var longitude = request.query.longitude;

    if(isNaN(latitude) && isNaN(longitude)){
        barsPromise = barsDAOImpl.getBars(latitude, longitude);
        barsPromise.then(function(res){
          response.json(res);
        })
        .catch(function(err){
            response.status(500).send("server error " + err);
            console.log("shit");
        });
    }
    else if(!isNaN(latitude) && !isNaN(longitude)){
        barsPromise = barsDAOImpl.getBarsFromPosition(latitude, longitude);
        barsPromise.then(function(res){
          response.json(res);
        })
        .catch(function(err){
            response.status(500).send("server error " + err);
            console.log("shit");
        });
    }
    else{
        response.status(500).send("BAD REQUEST");
    }
}

//percorso /bars/:bar_id
function getBar(request, response){
    var barId = parseInt(request.params.bar_id);
    if(isNaN(barId)){
        response.status(500).send("BAD REQUEST");
    }
    else{
        barPromise = barsDAOImpl.getBarFromId(barId);
        barPromise.then(function(bar){
            response.json(bar);
        })
        .catch(function(err){
            response.status(500).send("server error " + err);
        });
    }

}

module.exports.index = index;
module.exports.getBar = getBar;
