var express = require('express');
var router = express.Router();
var notificationsCtrl = require('../controllers/notifications.js');

/*
Implementing the API endpoint that will store the tokens is up to you, but here's an example.

Pretend that your backend hostname is api.example.com. You could then define an API endpoint accessible via the following URL:

https://api.example.com/register/device?user_id=123&user_session=abc123&device_token=abc123

When your app makes an HTTP request to that endpoint,
your backend should authenticate the user
and store their device token next to their user ID within your database, if you have one.

*/

/*  listing. */
router.get('/register', function (req, res, next) {
    notificationsCtrl.index(req, res);
});
