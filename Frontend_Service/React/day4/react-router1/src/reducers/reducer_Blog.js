import { FETCH_BLOG } from "../actions/blog";

//biz logic
//src/reducers/reducer_weather.js
//action.type(FETCH_WEATHER) , action.payload(weather.json)
export default function (state = [], action) {
    switch (action.type) {
        case FETCH_BLOG:
            // (x)return state.push(action.payload.data); 
            return action.payload;
        default:
            return state;
    }
}