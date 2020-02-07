import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
// router를 사용하기 위해 사용하는 명령어
import Home from './routers/Home'
import About from './routers/About'
import Header from './components/Header';
import Posts from './routers/Posts';
import MyProfile from './routers/MyProfile';
import Login from './routers/Login';
import Search from './routers/Search';
import NotFound from './routers/NotFound';

const App = () => {
  return (
    <Router>
      <Header/>
      <Switch>
      <Route exact path="/" component={Home}/>
      <Route path="/about/:userid" component={About}/>
      <Route path="/posts" component={Posts}/>
      <Route path="/search" component={Search}/>
      <Route path="/myprofile" component={MyProfile}/>
      <Route path="/login" component={Login}/>
      <Route component={NotFound}/>
      </Switch>

    </Router>
  );
};

export default App;
