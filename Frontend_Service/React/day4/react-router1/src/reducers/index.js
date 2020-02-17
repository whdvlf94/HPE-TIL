import { combineReducers } from 'redux';
import WeatherReducer from './reducer_weather';
import BitcoinReducer from './reducer_Bitcoin';
import BlogReducer from './reducer_Blog';

const rootReducer = combineReducers({
  weather: WeatherReducer,
  bitcoin : BitcoinReducer,
  blogs: BlogReducer
});

export default rootReducer
