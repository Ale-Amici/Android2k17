

var Bar = require('../models/bar.js');
var OpeningHour = require("../models/openingHour.js")
var dbHelper = require('../DB/dbhelper.js');
//var logger    =    require('../UTILS/log.js');


/*********************** LAT-LON DISTANCE **************************************/
function deg2rad(deg) {
  return deg * (Math.PI/180)
}
function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
  var R = 6371; // Radius of the earth in km
  var dLat = deg2rad(lat2-lat1);  // deg2rad below
  var dLon = deg2rad(lon2-lon1);
  var a =
    Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
    Math.sin(dLon/2) * Math.sin(dLon/2)
    ;
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
  var d = R * c; // Distance in km
  return d;
}
/****************************************************************************/
var getBarsFromPosition = function(latitude, longitude){
    return new Promise(function(resolve, reject){
        getBars().then(function(bars){

            //imposto la distanza di ogni bar
            bars.forEach(function(bar,index){
                bar.setDistance(getDistanceFromLatLonInKm(bar.latitude, bar.longitude, latitude, longitude));
            });

            // ALGORITMO PER ORDINARE I BAR A SECONDA DELLA LORO DISTANZA DAL CUSTOMER
            function compare(a,b) {
              if (a.distance < b.distance)
                return -1;
              if (a.distance > b.distance)
                return 1;
              return 0;
            }
            bars.sort(compare);

            resolve(bars);
        })
        .catch(function(err){
            reject("ERRORE");
        });
    });
}

/*
 * This function retrieves all bars orde
 */
var getBars = function(){
    var pool = dbHelper.getDBPool();
    var bars = [];
    return new Promise(function(resolve, reject){

      pool.queryAsync("SELECT ID,name,description, address, latitude, longitude "
        + " FROM BAR ORDER BY ID ")
      .then(function(rows1){
        console.log(rows1);//RIEMPIO L'ARRAY DEI BAR
        rows1.forEach(function(row, index){
          bars.push(new Bar()
              .setName(row["name"])
              .setId(row["ID"])
              .setDescription(row["description"])
              .setAddress(row["address"])
              .setLatitude(row["latitude"])
              .setLongitude(row["longitude"])
              .setOpeningHours([])
          );
        });
        /**************SECONDA QUERY***************/
        return pool.queryAsync("SELECT * FROM OPENING_HOUR ORDER BY BAR_ID");
      }).then(function(rows2){
        // ALGORITMO PER ASSEGNARE GLI ORARI AI BAR GIUSTI
        barsIndex = 0;
        rows2.forEach(function(row,index){
          console.log(row)
          while(bars[barsIndex].id != row["BAR_ID"]){
            barsIndex ++;
            console.log("Indice dei bar" + barsIndex);
            if(barsIndex >= bars.length){
                console.log("PROBLEMA CON I BOUND DELLA FUNZIONE 1");
                reject("errore");
            }
          }
          bars[barsIndex].openingHours.push(
            new OpeningHour()
              .setDayOfWeek(row["day_of_week"])
              .setTimeOpen(row["time_open"])
              .setWorkingTime(row["working_time"])
          );

        });

        resolve(bars);

      }).catch(function(err){
          reject("erroe");
      });
    });
};



module.exports.getBarsFromPosition = getBarsFromPosition;
module.exports.getBars = getBars;
