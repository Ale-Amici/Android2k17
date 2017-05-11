var mysql     =    require('mysql');

var pool      =    mysql.createPool({
    connectionLimit : 100, //important
    host     : 'localhost',
    user     : 'idabere',
    password : 'android2k17idabere',
    database : 'android2k17',
    debug    :  false
});
//HOW TO MANAGE TRANSACTION  https://github.com/mysqljs/mysql#transactions
function getDBPool() {
  /*
    var newConnection = pool.getConnection(function(err,connection){
        if (err) {
          res.json({"code" : 100, "status" : "Error in connection database"});
          return;
        }

        console.log('connected as id ' + connection.threadId);



        connection.on('error', function(err) {
              res.json({"code" : 100, "status" : "Error in connection database"});
              return;
        });
        return connection;
      });
      return newConnection;
      */
      return pool;
}

module.exports.getDBPool = getDBPool;
