import React, {Component} from 'react';
import Counter from './Counter';
// import Error1 from './Error1';

class App extends Component {
  render() {
    const count =0
  

    return (
   
        <Counter init={count}/> 
      // <Error1 init={count}/>
      
      )
    }
  }

export default App;