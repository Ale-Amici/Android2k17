//route index
function index(request, response){
  response.status(200).send("Index");
}

//route /notifications/register
function push(request, response){
  response.status(200).send("Push");
}

//route /notifications/register
function register(request, response){
  var device = req.params.device;
  response.status(200).send("Register");
}
