# Blog - DB





#### Store

---

- **index.js**

> src/index.js

```js
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {Provider} from 'react-redux'
import {createStore, applyMiddleware} from 'redux'
import promise from 'redux-promise'

import reducers from './reducers/index';

const createStoreWithMiddle = applyMiddleware(promise)(createStore);

ReactDOM.render(
    <Provider store={createStoreWithMiddle(reducers)}>
    <App />
    </Provider>
, document.getElementById('root'));

```

**=> store 생성(상태 관리가 되는 곳)**



- **App.js**

> src/App.js

```js
import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PostsNew from './components/posts_new'
import PostsShow from './components/posts_show'
import PostsIndex from './components/posts_index'

function App() {
  return (
      <Router>
        <div>
          <Switch>
            
            <Route path="/blogs/new" component={PostsNew}/>
            <Route path="/blogs/:id" component={PostsShow}/>
            <Route path="/" component={PostsIndex}/>
          </Switch>
        </div>
      </Router>
  );
}

export default App;

```

**※ Route path 지정**





#### Actions

---

- **index.js**

> actions/index.js

```js
import axios from 'axios';

// define action constants
export const FETCH_POSTS = 'fetch_posts';
export const CREATE_POST = 'create_post';
export const FETCH_POST = 'fetch_post';
export const DELETE_POST = 'delete_post';

// define api server address
const ROOT_URL = "http://localhost:8800/api";

    

// fetch posts
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
//.then(() => callback()) : 현재 action을 진행하고, 이전 상태로 되돌아 간다.

    return {
        type: CREATE_POST,
        payload: request
    }
    
}
// fetch post
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
//.then(() => callback()) : 현재 action을 진행하고, 이전 상태로 되돌아 간다.


    return {
        type: DELETE_POST,
        payload: id
    }

    
}
```

**=> Store에 접근하기 위해서는 Action을 발행해야 함**



※ Action의 기본적인 포맷

```js
{
    type: 액션의 종류를 식별할 수 있는 문자열
    payload: 액션 실행에 필요한 임의의 데이터
}
```





#### Reducer

---



- **index.js(root)**

> reducers/index.js

```js
import { combineReducers } from 'redux';
// Reducer를 분할하는 경우
import PostsReducer from './reducer_post'
import {reducer as formReducer} from 'redux-form'

const rootReducer = combineReducers({
    posts: PostsReducer,
    form: formReducer
});

export default rootReducer;
```



- **reducer_post**

> reducers/reducer_post

```js
import {FETCH_POSTS, FETCH_POST, CREATE_POST, DELETE_POST} from "../actions";
import _ from 'lodash'

export default function(state={}, action) {

    switch(action.type) {
        case FETCH_POSTS:
            // return action.payload.data.blogs
            return _.mapKeys(action.payload.data.blogs, 'id')

        case FETCH_POST:
            //const post = action.payload.data
            //console.log(post)
            return {...state, [action.payload.data.blog.id]: action.payload.data.blog}

        default:
            return state;
    }
}
```

※  상태가 변할 때 전해진`state`  값이 기존의 `state` 값을 대체하는 것이 아니고, 변화된 값만 추가/제거 한다.



#### Components

---



- **posts_index.js**

> components/posts_index

```js
import _ from 'lodash';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { fetchPosts } from '../actions/index';

import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';

class PostsIndex extends Component {

  // define a lifecycle function for retrieve data  
  componentDidMount() {
    console.log('componentDidMount Called');
    this.props.fetchPosts();
    // fetchPosts가 실행될 때 마다 호출
  }

  renderPosts() {
    // list -> 개별 <li>  태그로 출력
    return _.map(this.props.posts, post => {
      return (

        <li className='list-group-item' key={post.id}>
          <Link to={`/blogs/${post.id}``}>{ '[' + post.id + '] ' }{post.title}</Link>
        </li>
      );
    })


  }


  render() {
    return (
      <div>
        <div className="text-xs-right">
          <Link className="btn btn-primary"
            to="/blogs/new">Add a Post</Link>
        </div>

        <h3>Posts</h3>
        <ul className="list-group">
          {this.renderPosts()}
        </ul>
      </div>
    );
  }
}

// 상태 관리
function mapStateToProps(state) {
  console.log(state)
  return { posts: state.posts }
}

// 액션 관리
function mapDispatchToProps(dispatch) {
  return bindActionCreators({ fetchPosts }, (dispatch))

}

// connect(상태관리 함수, 액션관리 함수)(연결 컴포넌트)
export default connect(mapStateToProps, mapDispatchToProps)(PostsIndex);

// ES6 -> {name:name} => {name}
// [액션관리 함수] 위의 방식 대신 아래와 같이 사용해도 된다.
// export default connect(mapStateToProps, {fetchPosts})(PostsIndex);


```



- **posts_new.js**

> components/posts_new.js

```js
import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { createPost } from '../actions';

class PostsNew extends Component {

renderField(field) {
  const { meta : {touched, error} } = field; //  meta = field.meta 
    // console.log("touched", touched);
    // console.log("error", error);

    const className = `form-group ${touched && error ? 'has-danger' : '' }`
    return (
      
      <div className={className}>
        <label>{field.label}</label>
        <input className="form-control" type='text'{...field.input} />
        <div className='text-help'>

        {touched ? error :''}
        </div>
      </div>
    )

}

onSubmit(values) {
  // submit values to creatPost function()
  this.props.createPost(values, () => {
    this.props.history.push('/');
  });
}


render() {
  // const handleSubmit = this.props.handleSubmit;
  const { handleSubmit } = this.props;

  return (
    <form onSubmit={handleSubmit(this.onSubmit.bind(this))}>
      <Field label="Title For Post" name="title" component={this.renderField} />
      <Field label="Category" name="category" component={this.renderField} />
      <Field label="Blog contents" name="contents" component={this.renderField} />

      <button type="submit" className="btn btn-primary">Submit</button>
      <Link to="" className="btn btn-danger">Cancel</Link>
    </form>
  );
}
}
function validate(values) {
  const errors = {}

if(!values.title || values.title.length <3 ) {
  errors.title = "제목을 3 글자 이상 입력하세요.";
}
if(!values.category) {
  errors.category = "카테고리를 지정해 주세요.";
}
if(!values.contents) {
  errors.contents = "블로그의 내용을 입력하세요.";
}
return errors;
}


export default reduxForm ({
  validate: validate,
  form: "PostsNewForm"
  
})(connect(null, {createPost})(PostsNew))
```





- **posts_show.js**

> components/posts_show.js

```js
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { fetchPost, deletePost } from '../actions';

class PostsShow extends Component {

  componentDidMount() {
    // const {id} = this.props.match.params.id 
    // console.log(this.props)
    // if (!this.props.post) {

    const { id } = this.props.match.params;
    console.log(this.props.match)
    this.props.fetchPost(id);
  }

  onDeleteClick() {
    const { id } = this.props.match.params;
    this.props.deletePost(id, () => {

      this.props.history.push('/');
    })

  }

  // localhost:8800/ -> 목록보기
  // 아이템 선택(클릭)
  // localhost:8800/posts/12 -> 상세보기
  render() {
    const { post } = this.props;

    if (!post) {
      return <div>loading...</div>
    }
    return (
      <div>
        <Link to='/'>back to index</Link>
        <button className="btn btn-danger pull-xs-right"
          onClick={this.onDeleteClick.bind(this)}>delete post</button>

        <h3>{post.title}</h3>
        <h6>category: {post.category}</h6>
        <p>{post.contents}</p>

      </div>
    );
  }
}
function mapstateToProps(state, ownProps) {
  console.log(state.posts)
  return { post: state.posts[ownProps.match.params.id] };

}

export default connect(mapstateToProps, { fetchPost, deletePost })(PostsShow);
```

