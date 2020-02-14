import { combineReducers } from 'redux';
import BitcoinReducer from './reducer_Bitcoin';

const rootReducer = combineReducers({
    bitcoin : BitcoinReducer
});

export default rootReducer;
