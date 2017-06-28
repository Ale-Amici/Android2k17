// Import to use in this file
var usersDAOImpl = require('../DAOIMPL/users.js');
var User = require('../models/user.js');
/*
comunque per le API si diceva

host/users/:userId/preferreds/  (index)
host/users/:userId/preferreds/add/:menuItemId  (add)
host/users/:userId/preferreds/remove/:menuItemId  (remove)
*/

/*
* This function lists all users in the system
*/
function index(request, response) {
  usersDAOImpl.all(function(users){
    console.log("ECCO cosa ne viene fuori");
    console.log(users);
    response.json(users);
  });
}

/*
* This function creates an user managing errors
*/
function createUser(request, response) {
  var params = request.body;
  var user = new user(params.id, params.name, params.surname, params.level, params.salary);

  if (usersDA.create(user)) {
    response.redirect('/users');
  } else {
    response.render('users/new', {
      user: user
    });
  }
}

/*
* This function destroyes a user with param id
*/
function destroyUser(request, response) {
  var id = parseInt(request.params.id);
  if (usersDA.destroy(id)) {
    response.redirect('/users');
  } else {
    response.render('users/edit', {
      user: user
    });
  }
}

  module.exports.index = index;
  // module.exports.new = newuser;
  // module.exports.create = createuser;
  // module.exports.edit = edituser;
  // module.exports.update = updateuser;
  // module.exports.destroy = destroyuser;
