// Import to use in this file
var usersDAOImpl = require('../DAOIMPL/users.js');
var preferredsDAOImpl = require('../DAOIMPL/preferreds.js');
var User = require('../models/user.js');

/*
comunque per le API si diceva

host/users/:userId/preferreds/  (index)
host/users/:userId/preferreds/add/:menuItemId  (add)
host/users/:userId/preferreds/remove/:menuItemId  (remove)
*/

function getAll(request, response){
    //RESTITUISCI LISTA DI MENU_ITEM CON SOLO L'ID SETTATO
}

function addPreferred(request, response){
    itemId = request.params.item_id;
    userId = request.params.user_id;
    preferredsDAOImpl.add(userId,itemId)
    .then(function(res){
        if(res.affectedRows ==1){
            response.status(200).json("AGGIUNTO")
        }
        else{
            console.log("ERR AGGIUNTA PREFERITO")
            console.log(res);
            response.status(500).json("ERRORE NELL'AGGIUNGERE IL PREFERITO")
        }


    }).catch(function(err){
        response.status(500).json("ERRORE NELL'AGGIUNGERE IL PREFERITO")
    });

}

function removePreferred(request, response){
    itemId = request.params.item_id;
    userId = request.params.user_id;
    preferredsDAOImpl.remove(userId,itemId)
    .then(function(res){
        if(res.affectedRows ==1)  response.status(200).json("RIMOSSO")
        else{
            console.log("ERR RIMOZIONE PREFERITO")
            console.log(res);
            response.status(500).json("ERRORE NEL RIMUOVERE IL PREFERITO")
        }

    }).catch(function(err){
        console.log(err)
        response.status(500).json("ERRORE NEL RIMUOVERE IL PREFERITO")
    });
}

module.exports.getAll = getAll;
module.exports.addPreferred = addPreferred;
module.exports.removePreferred = removePreferred;
