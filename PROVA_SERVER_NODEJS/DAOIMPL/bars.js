

var Bar = require('../models/bar.js');
var OpeningHour = require("../models/openingHour.js")
var BarTable = require("../models/barTable.js")
var BarCounter = require("../models/barCounter.js")
var BarMenu = require("../models/barMenu.js")
var BarMenuItem = require("../models/barMenuItem.js")
var Ingredient = require("../models/ingredient.js")
var Addition = require("../models/addition.js")
var Size = require("../models/size.js")
var dbHelper = require('../DB/dbhelper.js');
//var logger    =    require('../UTILS/log.js');


/**** JOIN ARRAYS FUNCTION ***/
/**
 * Funzione potentissima che fa il join tra un array e un array di rows prese dal db!
 * @param  {objects[]} array               ogni oggetto dell'array DEVE avere la proprietà id
 * @param  {objects[]} rows                ogni row DEVE avere l'elemento di chaive key -> row[key]
 * @param  {String} key                    la chiave della row che matcha l'id dell'elemento -> "BAR_ID"
 * @param  {function} getObjectFromDbRow   la funziona che crea l'oggetto da inserire nei risultati dell'array
 * @param  {String} propertyName           il nome della proprietà in cui inserire gli elementi
 * @return {bool}                          false se vado fuori dai bound, true altrimenti
 */
var joinArrayWithRows = function(array, rows, key, getObjectFromDbRow, propertyName){
    arrayIndex = 0;
    //console.log("AAAAAAAAAAAAAAAA" + key +" " + propertyName +"\n" +array);
    rows.forEach(function(row,index){
        //console.log("BBBBBBBBBBBBBBBBB " + array[arrayIndex].id + " =? " +row[key] +  "\n");
        while(array[arrayIndex].id != row[key]){

            arrayIndex++;

        }
        if(arrayIndex >= array.length){
            console.log("PROBLEMA CON I BOUND DELLA FUNZIONE 1 sulla key=" + key);
            return false;
            // reject("errore");
        }
        if(array[arrayIndex][propertyName] == undefined){
            array[arrayIndex][propertyName] = [];
        }
        array[arrayIndex][propertyName].push(getObjectFromDbRow(row));
        //console.log("AGGIUNTO " + index +" " + row);
    });

    return true;

}

/*********************** LAT-LON DISTANCE **************************************/
function deg2rad(deg) {
  return deg * (Math.PI/180)
}
/**
 * Function that compute the distance from a pair of coordinates on the world
 * @param  {float} lat1
 * @param  {float} lon1
 * @param  {float} lat2
 * @param  {float} lon2
 * @return {int} the distance in meter
 */
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

/**
 * Function that retrieve all the bars from the DB ordered by distance from the coordinate of the user
 * @param  {float} latitude
 * @param  {float} longitude
 * @return {Promise}  a promise that returns an Array of Bar ordered by distance
 */
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

/**
 * function that get all the bar from the DB
 * @return {Promise}  a promise that returns an array of Bar
 */
var getBars = function(){
    var pool = dbHelper.getDBPool();
    var bars = [];
    return new Promise(function(resolve, reject){

    pool.queryAsync("SELECT ID,name,description, address, latitude, longitude "
    + " FROM BAR ORDER BY ID ")
    .then(function(rows1){
        rows1.forEach(function(row, index){
            bars.push(getBarFromDbRow(row));
    });

    /**************SECONDA QUERY***************/
    return pool.queryAsync("SELECT * FROM OPENING_HOUR ORDER BY BAR_ID");
    }).then(function(rows2){

        joinArrayWithRows(bars, rows2, "BAR_ID", getOpeningHourFromDbRow, "openingHours");

        resolve(bars);

      }).catch(function(err){
          reject("erroe");
      });
    });
};


/**
 * Function that get a Bar from its bar_id
 * @param  {int} barId
 * @return {Promise}  a promise that returns a Bar
 */
var getBarFromId = function(barId){
    var pool = dbHelper.getDBPool();
    var bar = [];
    return new Promise(function(resolve, reject){
        pool.queryAsync("SELECT * FROM BAR WHERE ID = ? ", barId)
        .then(function(barRows){
            bar = getBarFromDbRow(barRows[0]);
            /**************QUERY GET OpeningHour***************/
            return pool.queryAsync("SELECT * FROM OPENING_HOUR WHERE BAR_ID = ?", barId);
          })
          .then(function(openingRows){
              openingRows.forEach(function(row,index){
                  bar.openingHours.push(
                      getOpeningHourFromDbRow(row)
                  )
              });
              /**************QUERY GET BarMenuItem***************/
              return pool.queryAsync( " SELECT MI.ID, BAR_ID, ITEM_CATEGORY_ID, GLOBAL_MENU_ITEM_ID,menu_item_name, description, category_name  "
                                    + " FROM MENU_ITEM MI JOIN ITEM_CATEGORY IC ON(MI.ITEM_CATEGORY_ID = IC.ID) "
                                    + " WHERE BAR_ID = ?"
                                    + " ORDER BY MI.ID ASC", barId);
          })
          .then(function(menuItemRows){
              menuItemRows.forEach(function(row,index){
                  bar.barMenu.barMenuItemList.push( getBarMenuItemFromDbRow(row) );
              });
              return pool.queryAsync( " SELECT  MIHI.MENU_ITEM_ID, MIHI.INGREDIENT_ID, quantity, ingredient_name FROM  "
                                    + " MENU_ITEM_HAS_INGREDIENT MIHI JOIN INGREDIENT I ON(MIHI.INGREDIENT_ID = I.ID) "
                                    + " JOIN MENU_ITEM MI ON(MI.ID = MIHI.MENU_ITEM_ID)"
                                    + " WHERE MI.BAR_ID = ?"
                                    + " ORDER BY MIHI.MENU_ITEM_ID ASC ", barId);
          })
          .then(function(itemIngredientsRows){
              //console.log(itemIngredientsRows);
              joinArrayWithRows(bar.barMenu.barMenuItemList, itemIngredientsRows, "MENU_ITEM_ID", getIngredientFromDbRow, "ingredients");
              /*****QUERY PER LE ADDITION****/
              return pool.queryAsync( " SELECT  MIHA.MENU_ITEM_ID, MIHA.ITEM_ADDITION_ID, price, addition_name  "
                                    + " FROM MENU_ITEM_HAS_ADDITION MIHA JOIN ITEM_ADDITION IA ON(MIHA.ITEM_ADDITION_ID = IA.ID) "
                                    + " JOIN MENU_ITEM MI ON(MI.ID = MIHA.MENU_ITEM_ID)"
                                    + " WHERE MI.BAR_ID = ?"
                                    + " ORDER BY MIHA.MENU_ITEM_ID ASC ", barId);
          })
          .then(function(itemAdditionsRows){

              joinArrayWithRows(bar.barMenu.barMenuItemList,itemAdditionsRows, "MENU_ITEM_ID", getAdditionFromDbRow, "additions" );

              /*****QUERY PER LE SIZE****/
              return pool.queryAsync( " SELECT  MIHS.MENU_ITEM_ID, MIHS.ITEM_SIZE_ID, price, size_description "
                                    + " FROM MENU_ITEM_HAS_SIZE MIHS JOIN ITEM_SIZE  ON(MIHS.ITEM_SIZE_ID = ITEM_SIZE.ID) "
                                    + " JOIN MENU_ITEM MI ON(MI.ID = MIHS.MENU_ITEM_ID)"
                                    + " WHERE MI.BAR_ID = ?"
                                    + " ORDER BY MIHS.MENU_ITEM_ID ASC ", barId);

          })
          .then(function(itemSizesRows){
            joinArrayWithRows(bar.barMenu.barMenuItemList,itemSizesRows, "MENU_ITEM_ID", getSizeFromDbRow, "sizes");
            /***********QUERY PER I TABLES*************/
            return pool.queryAsync( " SELECT BT.DELIVERY_PLACE_ID, BHDP.floor, BT.table_number"
                                  + " FROM BAR_HAS_DELIVERY_PLACE BHDP JOIN DELIVERY_PLACE DP  ON(BHDP.DELIVERY_PLACE_ID = DP.ID) "
                                  + " JOIN BAR_TABLE BT ON(BT.DELIVERY_PLACE_ID = DP.ID)"
                                  + " WHERE BHDP.BAR_ID = ?"
                                  + " ORDER BY BHDP.floor ASC, BT.table_number ASC ", barId);
          })
          .then(function(tableRows) {
              tableRows.forEach(function(row,index){
                  bar.deliveryPlaces.push(getBarTableFromDbRow(row));
              })
              return pool.queryAsync( " SELECT BC.DELIVERY_PLACE_ID, BHDP.floor, BC.counter_name"
                                    + " FROM BAR_HAS_DELIVERY_PLACE BHDP JOIN DELIVERY_PLACE DP  ON(BHDP.DELIVERY_PLACE_ID = DP.ID) "
                                    + " JOIN BAR_COUNTER BC ON(BC.DELIVERY_PLACE_ID = DP.ID)"
                                    + " WHERE BHDP.BAR_ID = ?"
                                    + " ORDER BY BHDP.floor ASC, BC.counter_name ASC ", barId);

          })
          .then(function(counterRows){
              counterRows.forEach(function(row,index){
                  bar.deliveryPlaces.push(getBarCounterFromDbRow(row));
              })
              //console.log(bar.barMenu.barMenuItemList);
              resolve(bar);
          })
          .catch(function(err){
              reject(err);
          });


    });
}

/************************************************* GET OBJECT FROM DB ROW **********************************************/
var getBarFromDbRow = function(row){
    //console.log(row)
    bar = new Bar()
        .setName(row["name"])
        .setId(row["ID"])
        .setDescription(row["description"])
        .setAddress(row["address"])
        .setLatitude(row["latitude"])
        .setLongitude(row["longitude"])
        .setOpeningHours([])//li prendo nella seconda query
        .setDeliveryPlaces([])
        .setBarMenu(
            new BarMenu().setBarMenuItemList([])
        )


    return bar
}

var getOpeningHourFromDbRow = function(row){
    return new OpeningHour()
        .setDayOfWeek(row["day_of_week"])
        .setTimeOpen(row["time_open"])
        .setWorkingTime(row["working_time"])
}

var getBarMenuItemFromDbRow = function(row){
    return new BarMenuItem()
        .setId(row["ID"])
        .setName(row["menu_item_name"])
        .setDescription(row["description"])
        .setCategory(row["category_name"])
        .setIngredients(undefined)
        .setAdditions(undefined)
        .setSizes(undefined)
}

var getIngredientFromDbRow = function(row){
    return new Ingredient()
        .setId(row["INGREDIENT_ID"])
        .setName(row["ingredient_name"])
        .setQuantity(row["quantity"])
}

var getAdditionFromDbRow = function(row){
    return new Addition()
        .setId(row["ITEM_ADDITION_ID"])
        .setName(row["addition_name"])
        .setPrice(row["price"])
}

var getSizeFromDbRow = function(row){
    return new Size()
        .setId(row["ITEM_SIZE_ID"])
        .setName(row["size_description"])
        .setPrice(row["price"])
}

var getBarTableFromDbRow = function(row){
    return new BarTable()
        .setId(row["DELIVERY_PLACE_ID"])
        .setFloor(row["floor"])
        .setTableNumber(row["table_number"])
}

var getBarCounterFromDbRow = function(row){
    return new BarCounter()
        .setId(row["DELIVERY_PLACE_ID"])
        .setFloor(row["floor"])
        .setCounterName(row["counter_name"])
}

module.exports.getBarsFromPosition = getBarsFromPosition;
module.exports.getBars = getBars;
module.exports.getBarFromId = getBarFromId;
