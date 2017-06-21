// Import to use in this file
var usersDAOImpl = require('../DAOIMPL/users.js');
var User = require('../models/user.js');


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
 * This function authenticate a user given its email/username and password
 */
function authenticateUser(request, response) {
}

// /*
//  * This function shows a form to insert a new user
//  */
// function new(request, response) {
//     var user = new user();
//     response.render('users/new', {
//         user: user
//     });
// }

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

// /*
//  * This function shows a form to edit a user
//  */
// function edituser(request, response) {
//     var id = parseInt(request.params.id);
//     var user = usersDA.find(id);
//     if (user == null) {
//         response.status(404).send('Not found');
//     } else {
//         response.render('users/edit', {
//             user: user
//         });
//     }
// }
//
/*
 * This function shows a form to insert a new user
 */
function updateUser(request, response) {
//     var id = parseInt(request.params.id);
//     var params = request.body;
//
//     var user = usersDA.find(id);
//     if (user == null) {
//         response.status(404);
//         return;
//     }
//
//     var editeduser = new user();
//     editeduser.id = params.id;
//     editeduser.name = params.name;
//     editeduser.surname = params.surname;
//     editeduser.level = params.level;
//     editeduser.salary = params.salary;
//
//     try {
//         if (usersDA.update(editeduser, id)) {
//             response.redirect('/users');
//             return;
//         } else {
//             response.render('users/edit', {
//                 user: user
//             });
//             return;
//         }
//     } catch (exception) {
//         console.log(exception);
//         response.status(500).send(exception);
//     }
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
