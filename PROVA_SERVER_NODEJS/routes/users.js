var express = require('express');
var router = express.Router();
var usersCtrl = require('../controllers/users.js');

/* GET users listing. */
// router.get('/', function (req, res, next) {
//   usersCtrl.index(req, res);
// });


/* GET new user form. */
// router.get('/new', function (req, res, next) {
//     usersCtrl.new(req, res);
// });

/* POST authenticate user. */
router.post('/create', function (req, res, next) {
  usersCtrl.create(req, res);
});

/* POST create user. */
router.post('/create', function (req, res, next) {
  usersCtrl.create(req, res);
});

/* GET edit user. */
// router.get('/edit/:id', function (req, res, next) {
//     usersCtrl.edit(req, res);
// });

/* POST update user. */
router.post('/update/:id', function (req, res, next) {
  usersCtrl.update(req, res);
});

/* GET delete user. */
router.get('/destroy/:id', function (req, res, next) {
  usersCtrl.destroy(req, res);
});

module.exports = router;
