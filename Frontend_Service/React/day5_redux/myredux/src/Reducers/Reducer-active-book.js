export default function(state=null, action) { //ES6 문법 : sate=null 초기값 지정
    switch(action.type) {
        case 'BOOK_SELECTED':
            return action.payload;
        default:
            return state;
    }
}
