# Bitcoin - redux

[개념 참고](https://medium.com/@ca3rot/아마-이게-제일-이해하기-쉬울걸요-react-redux-플로우의-이해-1585e911a0a6)



- **index.js**

> src / index.js

```js
import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';

import ReduxPromise from 'redux-promise';

import App from './App';

import reducers from './reducers';

// make a store 
const createstoreWithMiddleware = applyMiddleware(ReduxPromise)(createStore);

ReactDOM.render(
  <Provider store={createstoreWithMiddleware(reducers)}>
    <App />
  </Provider>
  , document.getElementById('root'));

```



- **App.js**

> src/App.js

```js
import React, { Component } from 'react';
import './App.css';

import SearchBar from './containers/search_bar';
import WeatherList from './containers/weather_list';

export default class App extends Component {
  render() {
    return (
      <div>
        <SearchBar/>
        <WeatherList/>    
      </div>
    );
  }
}

```





#### action

---



- **action - index.js**

> actions/index.js

```js
import axios from 'axios';

//const API_KEY = '3ab6be6c58fdbe263c7d3dee8c8adc23';
//const ROOT_URL = `https://api.openweathermap.org/data/2.5/forecast?appid=${API_KEY}`;
const ROOT_URL = `https://poloniex.com/public?command=returnChartData&start=1580962570&end=9999999999&period=86400`;
export const FETCH_WEATHER = 'FETCH_WEATHER';

//redux action
// tyoe(mandatory)
// payload(optional, data?)
export async function fetchWeather(usdt) {
    const url = `${ROOT_URL}&currencyPair=${usdt}`;
    const request = await axios.get(url)
  
    return{
        type: FETCH_WEATHER,
        payload: request,
        currency: usdt
    }
}
```





#### reducer

---



- **reducer_weather.js**

> reducers/reducer_weather.js

```js
import { FETCH_WEATHER } from "../actions";

//biz logic
//src/reducers/reducer_weather.js
//action.type(FETCH_WEATHER) , action.payload(weather.json)
export default function(state = [], action) {
    switch(action.type){
        case FETCH_WEATHER:
            // (x)return state.push(action.payload.data); 
            return[{type: action.type, currency: action.currency , data: action.payload.data},...state ];
            default:
                return state;
            }
}
```



- **index.js(root)**

> reducers/index.js

```js
import { combineReducers } from 'redux';
import WeatherReducer from './reducer_weather';

const rootReducer = combineReducers({
    weather : WeatherReducer
});

export default rootReducer;

```





#### containers

---



- **search_bar.js**

> container/search_bar.js

```js
import React, { Component } from 'react';
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux';
import { fetchWeather } from '../actions/index';

class SearchBar extends Component {
  constructor(props) {
    super(props);

    this.state={term: ''}
    this.onInputChange = this.onInputChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this)

  }

  onInputChange(event) {
      this.setState({
        term: event.target.value
      })
  }

  onFormSubmit(event) {
      event.preventDefault()
      this.props.fetchWeather(this.state.term);
      this.setState({term:''})
  }

  render() {
    return (
      <form onSubmit={this.onFormSubmit} className="input-group">
        <input
          placeholder ="Get a five day forecast in your favorite city" 
          className="form-control"
          value = {this.state.term}
          onChange={this.onInputChange}
          />
        <span className="input-group-btn">
          <button type="submit" className="btn btn-secondary">Submit</button>
        </span>
      </form>
    );
  }
}

// mapDispatchToProps function
function mapDispatchToProps(dispatch){
  return bindActionCreators({fetchWeather},dispatch);
}

// connect mapping
export default connect(null,mapDispatchToProps)(SearchBar);
```



- **weather_list.js**

> containers/weather_list.js

```js
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Chart from '../components/chart';

class WeatherList extends Component {
  renderWeather(cityData){
    console.log(cityData)
    //const name = cityData.date;

   // const country = cityData.high;
    const high = cityData.data.map(btn => btn.high);
    const low = cityData.data.map(btn => btn.low);
    const volume = cityData.data.map(btn => btn.volume);
    const quoteVolume = cityData.data.map(btn => btn.quoteVolume);
    
   //  const presures = cityData.list.map(weather => weather.main.pressure);
   //   const humidities = cityData.list.map(weather => weather.main.humidity);

    return(
      <tr key={cityData.currency}>
            <th>{cityData.currency}</th>
            <th><Chart data={high} color="orange"/></th>
            <th><Chart data={low} color="red"/></th>
            <th><Chart data={volume} color="black"/></th>
            <th><Chart data={quoteVolume} color="pink"/></th>
      </tr>
    )
  }

  render() {
    return (
      <table className="table">
        <thead>
          <tr>
            <th>NAME</th>
            <th>HIGH</th>
            <th>LOW</th>
            <th>VOLUME</th>
            <th>QUOTEVOLUME</th>
          </tr>
        </thead>
        <tbody>
          {this.props.weather.map(this.renderWeather)}
        </tbody>
      </table>
    );
  }
}


// mapSrtateToProps funciton
function mapStateToProps(state){
  return{ weather: state.weather};
}

// connect mapping
export default connect(mapStateToProps)(WeatherList);
```



**※ component > chart.js**

