var express = require('express');
var router = express.Router();
var authenticationCtrl = require('../controllers/authentication.js');

/* GET bars listing. */
router.post('/login', function (req, res, next) {
    authenticationCtrl.login(req, res);
});


module.exports = router;
