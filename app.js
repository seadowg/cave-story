'use strict';

const App = require('actions-on-google').ApiAiApp;
const express = require('express');
const httpApp = express();
const bodyParser = require('body-parser');

let caveStory = (request, response) => {
  let app = new App({ request: request, response: response });

  function setState(app, location, prompt) {
    app.setContext('in_' + location, 1);
    app.ask(prompt);
  }

  function cave1(app) {
      let action = app.getArgument('action');
      let thing = app.getArgument('thing');

      if (action == 'look around' && thing == null) {
        setState(app, 'cave_1', 'The cave is very dark but you can make out some small rocks ' +
        'scattered around the floor. Behind you you can just make out some light in ' +
        'the distance. You can also here the sound of running water in the opposite direction');
      } else if (action == 'move' && thing == 'light') {
        setState(app, 'cave_1', 'You run over to the light hoping to find a way out of the cave but ' +
        'you find a shaft of light descending from a roof that must be 30 or 40 foot up. How are you going ' +
        'to get out of here?');
      } else if (action == 'pick up' && thing == 'rock') {
        app.setContext('bag_contains_rock', 1);
        setState(app, 'cave_1', 'You pick up a rock and put in your bag.');
      } else if (action == 'move' && thing == 'water') {
        setState(app, 'waterfall', 'You walk towards the sound of water. You walk through a narrow tunnel for several ' +
        'minutes and then emerge in a large chamber with a giant waterfall.');
      } else {
        setState(app, 'cave_1', 'You try that and it doesn\'t work.');
      }
  }

  function waterfall(app) {
    let action = app.getArgument('action');
    let thing = app.getArgument('thing');

    if (action == 'look around' && thing == null) {
      setState(app, 'waterfall', 'Oh look a waterfall!');
    } else if (action == 'wash' && thing == 'waterfall') {
      setState(app, 'waterfall', 'You emerge from the waterfall sparkling clean. This changes nothing.');
    } else {
      setState(app, 'waterfall', 'You try that and it doesn\'t work.');
    }
  }

  function fallback(app) {
    console.log(app.getContexts());

    app.getContexts().forEach(function(context) {
      app.setContext(context.name, 1);
    });

    app.ask("I don't understand. Why don't you try looking around?");
  }

  const actionMap = new Map();
  actionMap.set('cave_1', cave1);
  actionMap.set('waterfall', waterfall);
  actionMap.set('input.unknown', fallback);

  app.handleRequest(actionMap);
}

httpApp.use(bodyParser.json())

httpApp.post('/', function(req, res) {
  caveStory(req, res);
});

httpApp.listen(process.env['PORT'])
