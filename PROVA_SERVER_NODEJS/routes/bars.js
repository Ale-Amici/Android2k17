var express = require('express');
var router = express.Router();
var barsCtrl = require('../controllers/bars.js');

/* GET bars listing. */
router.get('/', function (req, res, next) {
    barsCtrl.index(req, res);
});

/* GET a bar from ID */
router.get('/:bar_id', function (req, res, next) {
    barsCtrl.getBar(req, res);
});

module.exports = router;
