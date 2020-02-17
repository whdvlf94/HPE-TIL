import axios from 'axios';

// define action constants
export const FETCH_POSTS = 'fetch_posts';
export const CREATE_POST = 'create_post';
export const FETCH_POST = 'fetch_post';
export const DELETE_POST = 'delete_post';

// define api server address
const ROOT_URL = "http://localhost:8800/api";

    

// fetch post
export function fetchPosts() {

    const request = axios.get(`${ROOT_URL}/blogs`);

    return {
        type: FETCH_POSTS,
        payload: request
    }

}
// create post
export function createPost(values, callback) {
    const request = axios.post(`${ROOT_URL}/blogs`, values).then(() => callback())
    // callback : 현재 action을 진행하고, 이전 상태로 되돌아 간다.
    return {
        type: CREATE_POST,
        payload: request
    }
    
}
// detail post
export async function fetchPost(id) {
    const request = await axios.get(`${ROOT_URL}/blogs/${id}`);
    console.log(request)
    return {
        type: FETCH_POST,
        payload: request
    }

}
//delete post
export function deletePost(id, callback) {
    const request = axios.delete(`${ROOT_URL}/blogs/${id}`).then(() => callback());

    return {
        type: DELETE_POST,
        payload: id
    }

    
}