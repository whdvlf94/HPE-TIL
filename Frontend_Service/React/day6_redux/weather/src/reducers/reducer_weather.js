import { FETCH_WEATHER } from "../actions";

export default function(state = [], action) {
  switch(action.type) {
    case FETCH_WEATHER:
      return [ 
        {
          d: action.payload,
          n: action.name
        }, 
        // action.payload.data,
        ...state
      ];
    default:
      return state;
  }
}