import {FETCH_POSTS, FETCH_POST, CREATE_POST, DELETE_POST} from "../actions";
import _ from 'lodash'

export default function(state={}, action) {

    switch(action.type) {
        case FETCH_POSTS:
            // return action.payload.data.blogs
            return _.mapKeys(action.payload.data.blogs, 'id')

        case FETCH_POST:
            const post = action.payload.data
            console.log(post)
            return {...state, [action.payload.data.blog.id]: action.payload.data.blog}

        default:
            return state;
    }
}