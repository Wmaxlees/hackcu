var express = require('express');
var glob = require('glob');

var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var compress = require('compression');
var methodOverride = require('method-override');

var fs = require('fs');

module.exports = function(app, config) {
  var env = process.env.NODE_ENV || 'development';
  app.locals.ENV = env;
  app.locals.ENV_DEVELOPMENT = env == 'development';
  
  app.set('views', config.root + '/app/views');
  app.set('view engine', 'jade');

  // app.use(favicon(config.root + '/public/img/favicon.ico'));
  app.use(logger('dev'));
  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({
    extended: true
  }));
  app.use(cookieParser());
  app.use(compress());
  app.use(express.static(config.root + '/public'));
  app.use(methodOverride());

  var controllers = glob.sync(config.root + '/app/controllers/*.js');
  controllers.forEach(function (controller) {
    require(controller)(app);
  });

  app.post('/classify', (req, res) => {
    var xValues = new Buffer(req.body.x);
    var yValues = new Buffer(req.body.y);


  });

  app.post('/upload', (req, res) => {
    var xValues = new Buffer(req.body.x);
    var yValues = new Buffer(req.body.y);
    var shape = req.body.shape;

    var filename = __dirname + '/../data/' + shape + '/' + (new Date).getTime() + '.txt';
    var aStream = fs.createWriteStream(filename);

    aStream.write(xValues.length)
    for (var i = 0; i < xValues.length; ++i) {
      aStream.write(xValues[i])
    }
    for (var i = 0; i < yValues.length; ++i) {
      aStream.write(yValues[i])
    }
    aStream.end();

    res.status(200).send('Success');
  });

  app.use(function (req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
  });
  
  if(app.get('env') === 'development'){
    app.use(function (err, req, res, next) {
      res.status(err.status || 500);
      res.render('error', {
        message: err.message,
        error: err,
        title: 'error'
      });
    });
  }

  app.use(function (err, req, res, next) {
    res.status(err.status || 500);
      res.render('error', {
        message: err.message,
        error: {},
        title: 'error'
      });
  });

  return app;
};
