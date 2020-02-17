import React, { Component } from 'react';
// import './App.css';

import SearchBarBit from '../containers/bitcoin/search_bar';
import BitcoinList from '../containers/bitcoin/Bitcoin_list';

export default class Bitcoin extends Component {
  render() {
    return (
      <div>
        <SearchBarBit/>
        <BitcoinList />
      </div>
    );
  }
}

