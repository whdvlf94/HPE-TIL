import React, { Component } from 'react';
import { connect } from 'react-redux';
import Chart from '../../components/chart';

class WeatherList extends Component {
  renderWeather(v) {
    const cityData = v.d.data;
    // cityData.d(Promise), cityData.n
    // cityData.d.then(
    //   v => console.log(v.data.city.name)
    // );
    // setTimeout -> new Promise() ...

    const name = cityData.city.name;
    const country = cityData.city.country;
    const temps = cityData.list.map(weather => weather.main.temp)
    ;
    
    const pressures = cityData.list.map(weather => weather.main.pressure);
    const humidities = cityData.list.map(weather => weather.main.humidity);

    return (

      <tr key={name}>
        <td>{name},{country}</td>
        <td><Chart data={temps} color="orange"/></td>
        <td><Chart data={pressures} color="green"/></td>
        <td><Chart data={humidities} color="black"/></td>
      </tr>
    );
  }

  render() {
    return (
      <table className="table">
        <thead>
          <tr>
            <th>City</th>
            <th>Temperature</th>
            <th>Pressure</th>
            <th>Humidity</th>
          </tr>
        </thead>
        <tbody>
          {this.props.weather.map(this.renderWeather)}
        </tbody>
      </table>
    );
  }
}

// mapStateToProps funciton
function mapStateToProps(state) {
  return { 
    weather: state.weather 
  };
}

// connect mapping
export default connect(mapStateToProps)(WeatherList);