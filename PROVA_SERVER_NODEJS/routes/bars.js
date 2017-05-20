var express = require('express');
var router = express.Router();
var barsCtrl = require('../controllers/bars.js');

/* GET employees listing. */
router.get('/', function (req, res, next) {
    barsCtrl.index(req, res);
});

module.exports = router;
