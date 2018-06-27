var express = require('express');
var bodyParser = require('body-parser');
var path = require('path');

const customerManager = require('./routes/customerManager.js');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));


app.use(express.static(path.join(__dirname, 'html')));

app.use('/CustomerOptions', express.static(path.join(__dirname, 'html/customerclient')));
app.use('/customerService', customerManager);


app.set('port', 3000);

app.listen(app.get('port'), function(){
	console.log("Listening on port " + app.get('port'));
});