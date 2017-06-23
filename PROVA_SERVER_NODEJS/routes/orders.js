var express = require('express');
var router = express.Router();
var ordersCtrl = require('../controllers/orders.js');

/* POST create order. */
router.post('/create', function (req, res, next) {
  ordersCtrl.createOrder(req, res);
});

/* GET order */
router.get('/:id', function (req, res, next) {
  ordersCtrl.getOrder(req, res);
});

/* GET to delete order */
router.get('delete/:order_id/:destroy_code', function (req, res, next) {
  ordersCtrl.deleteOrder(req, res);
});

module.exports = router;
