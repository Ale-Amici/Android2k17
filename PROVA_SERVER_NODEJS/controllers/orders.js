// Import to use in this file
var ordersDAOImpl = require('../DAOIMPL/orders.js');
var order = require('../models/order.js');

//percorso "/orders/create"
function createOrder(request, response) {

}

//percorso /orders/:order_id
function (request, response){


}

//percorso /orders/:order_id/:destroy_code
function deleteOrder(request, response){


}

module.exports.createOrder = createOrder;
module.exports.getOrder = getOrder;
module.exports.deleteOrder = deleteOrder;
