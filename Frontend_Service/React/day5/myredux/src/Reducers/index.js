// src/reducers/index.js
// 외부의 component가 reducers를 참고하게 되면, 가장먼저 index를 참고한다.

import {combineReducers} from 'redux';
import BookReducer from './Reducer-books';
import ActiveBook from './Reducer-active-book'

const rootReducer = combineReducers ({
    // combineReducers : 여러 개의 Reducer를 관리해준다.
    books: BookReducer, 
    activeBook: ActiveBook
})

export default rootReducer;