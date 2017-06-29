'use strict';

const App = require('actions-on-google').ApiAiApp;
const express = require('express');
const httpApp = express();
const bodyParser = require('body-parser');

let caveStory = (request, response) => {
  let app = new App({ request: request, response: response });

  function step1(app) {
      let action = app.getArgument('action');
      let thing = app.getArgument('thing');

      if (action == 'look around' && thing == null) {
        app.ask('The cave is very dark but you can make out some small rocks ' +
        'scattered around the floor. Behind you you can just make out some light in ' +
        'the distance.');
      } else if (action == 'move' && thing == 'light') {
        app.tell('You run towards the light and find the cave looks out onto a huge valley. ' +
       'You step into the outside world, and breath the air. You\'re out of the cave but how will ' +
       'you get home from here?');
      } else if (action == 'find' && thing == 'rock') {
        app.ask('There is a rock on floor. Maybe you could pick it up?');
      } else if (action == 'pick up' && thing == 'rock') {
        app.setContext('bag_contains_rock');
        app.ask('You pick up a rock and put in your bag.');
      } else {
        app.ask('You try that and it doesn\'t work.');
      }
  }

  const actionMap = new Map();
  actionMap.set('step_1', step1);

  app.handleRequest(actionMap);
}

httpApp.use(bodyParser.json())

httpApp.post('/', function(req, res) {
  caveStory(req, res);
});

httpApp.listen(process.env['PORT'])
