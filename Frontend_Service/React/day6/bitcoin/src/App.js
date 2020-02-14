import React, { Component } from 'react';
import './App.css';

import SearchBar from './containers/search_bar';
import BitcoinList from './containers/Bitcoin_list';

export default class App extends Component {
  render() {
    return (
      <div>
        <SearchBar/>
        <BitcoinList />
      </div>
    );
  }
}
