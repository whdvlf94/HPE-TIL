import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PostsNew from './components/posts_new'
import PostsShow from './components/posts_show'
import PostsIndex from './components/posts_index'

function App() {
  return (
      <Router>
        <div>
          <Switch>
            
            <Route path="/blogs/new" component={PostsNew}/>
            <Route path="/blogs/:id" component={PostsShow}/>
            <Route path="/" component={PostsIndex}/>
          </Switch>
        </div>
      </Router>
  );
}

export default App;
