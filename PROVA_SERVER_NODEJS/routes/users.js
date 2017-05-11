var express = require('express');
var router = express.Router();
var usersCtrl = require('../controllers/users.js');

/* GET employees listing. */
router.get('/', function (req, res, next) {
    usersCtrl.index(req, res);
});


// /* GET new employee form. */
// router.get('/new', function (req, res, next) {
//     usersCtrl.new(req, res);
// });
//
// /* POST create employee form. */
// router.post('/create', function (req, res, next) {
//     usersCtrl.create(req, res);
// });
//
// /* GET edit employee form. */
// router.get('/edit/:id', function (req, res, next) {
//     usersCtrl.edit(req, res);
// });
//
// /* POST update employee. */
// router.post('/update/:id', function (req, res, next) {
//     usersCtrl.update(req, res);
// });
//
// /* GET delete employee. */
// router.get('/destroy/:id', function (req, res, next) {
//     usersCtrl.destroy(req, res);
// });

module.exports = router;
