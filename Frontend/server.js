'use strict';

const express = require('express');
const path = require('path');
const HOST = '0.0.0.0';
const PORT = 8080;

const app = express();
app.use('/static', express.static('./dist/static/'));

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, './dist/index.html'));
});

app.listen(PORT, HOST);
console.log('Listening on localhost:44044');
