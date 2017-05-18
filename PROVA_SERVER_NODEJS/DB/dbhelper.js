const Promise = require('bluebird');
const mysql = require('mysql2');
const Pool = require('mysql2/lib/pool');
const Connection = require('mysql2/lib/connection');
Promise.promisifyAll([Pool, Connection]);

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
      return pool;
}

module.exports.getDBPool = getDBPool;
