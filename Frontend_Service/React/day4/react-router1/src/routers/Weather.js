import React, { Component } from 'react';
// import './App.css';

import SearchBar from '../containers/weather/search_bar';
import WeatherList from '../containers/weather/weather_list';

export default class Weather extends Component {
  render() {
    return (
      <div>
        <SearchBar />
        <WeatherList />
      </div>
    );
  }
}
