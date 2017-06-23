// Import to use in this file
var ordersDAOImpl = require('../DAOIMPL/orders.js');
var order = require('../models/order.js');

//percorso "/orders/create"
function createOrder(request, response) {

}

//percorso /orders/:order_id
function getOrderFromId(request, response){


}

//percorso /orders/:order_id/:destroy_code
function deleteOrder(request, response){


}

module.exports.createOrder = createOrder;
module.exports.getOrderFromId = getOrderFromId;
module.exports.deleteOrder = deleteOrder;
