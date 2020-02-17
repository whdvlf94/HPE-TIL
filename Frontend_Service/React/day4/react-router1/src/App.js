import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// router를 사용하기 위해 사용하는 명령어
import Blog from './routers/Blog'
import Youtube from './routers/Youtube'
import Header from './components/Header';
import Weather from './routers/Weather';
import Bitcoin from './routers/Bitcoin';

const App = () => {
  return (
    <Router>
      <Header/>
      <Switch>
      <Route exact path="/Blog" component={Blog}/>
      <Route path="/Youtube" component={Youtube}/>
      <Route path="/Weather" component={Weather}/>
      <Route path="/Bitcoin" component={Bitcoin}/>

      </Switch>

    </Router>
  );
};

export default App;
