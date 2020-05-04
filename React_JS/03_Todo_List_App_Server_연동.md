# Todo-List App - Server 연동



**Setting**

```
[Todo-List]

1. redux, react-redux 설치
npm install --save redux react-redux

2. Axios
npm install --save axios

3. redux-thunk
npm install --save redux-thunk
```



#### Axios

- HTTP 클라이언트 라이브러리 중 하나

- 데이터를 외부(API 등)에서 가져오는 방법 중 하나, 요청 시 취소와 TypeScript도 지원함

  ```js
  // GET
  axios.get('/api/todos')
  .then(res => {
  console.log(res.data)
  })
  //axios 요청 메소드의 두 번째 인자로 config 객체를 넘길 수 있다
  axios.get('/api/todos', {
  params: { // query string
  title: 'React JS'
  },
  headers: { // 요청 헤더
  'X-Api-Key': 'my-api-key'
  },
  timeout: 3000 // 3초 이내에 응답이 오지 않으면 에러로 간주
  }).then(res => { console.log(res.data) })
  ```

  



### 1.  Action 생성 함수

> Action type과 Action 생성 함수 : Todo 목록 가져오기

- #### actions/index.js

  ```js
  import axios from 'axios';
  //Action type 정의
  
  export const FETCH_TODOS = "FETCH_TODOS";
  
  //Server URL
  const apiUrl = 'http://localhost:8083/todos';
  
  
  // Action 생성함수 선언
  
  // 1. Todo 목록
  export const fetchAllTodos = () => {
      //redux-thunk 미들웨어
      //객체 대신 함수를 생성하는 액션 생성 함수를 작성할 수 있게 해준다.
      return (dispatch) => {
          axios.get(apiUrl)
              //정상 실행
              .then(res => {
                  dispatch({
                      type: FETCH_TODOS,
                      payload: res.data
                  })
              })
  
              //에러 발생
              .catch(err => {
                  console.error(err);
                  throw (err);
              });
      }
  };//fetchAllTodos
  ```

  

### 2. Reducer 함수

> Reducer 함수 : Todo 목록 가져오기



- #### reducers/index.js

  ```js
  import { FETCH_TODOS } from '../actions';
  
  const initialState = {
      todos:[{
          id:0,
          text:'',
          check:false
      }]
  }
  
  // reducer 함수
  export const todoReducer = (state=initialState, action) => {
      switch(action.type) {
          case FETCH_TODOS:
              return Object.assign({}, state, {todos:action.payload})
          default:
              return state;
      }
  }
  ```

  



### 3. Store 생성, Middleware, redux-devtools 적용

> Store 생성 및 redux-thunk 미들웨어 적용



- #### src/index.js

  ```js
  import React from 'react';
  import ReactDOM from 'react-dom';
  import './index.css';
  import App from './App';
  import * as serviceWorker from './serviceWorker';
  import { todoReducer } from './reducers';
  
  import { createStore, applyMiddleware} from 'redux';
  import thunk from 'redux-thunk';
  import { Provider } from 'react-redux';
  import { composeWithDevTools } from 'redux-devtools-extension';
  
  //redux-thunk , redux-devtools
  const store = createStore(todoReducer, composeWithDevTools(applyMiddleware(thunk)));
  
  ReactDOM.render(
    
    <React.StrictMode>
      <Provider store={store}>
      <App />
      </Provider>
    </React.StrictMode>,
    document.getElementById('root')
  );
  
  serviceWorker.unregister();
  
  ```

  

### 4. Root 컴포넌트 수정

> App.js : Todo 목록 가져오기



- #### src/App.js

  ```js
  import React, { Component } from 'react';
  import TodoListTemplate from './components/TodoListTemplate';
  import Form from './components/Form';
  import TodoItemList from './components/TodoItemList';
  
  class App extends Component {
  
    render() {
  
      const {handleChange, handleCreate, handleKeyPress, handleRemove, handleToggle} = this;
      return (
        <div>
          <TodoListTemplate form={<Form todo={todo} myCreate={handleCreate} myChange={handleChange} myKeyPress={handleKeyPress} />}>
            <TodoItemList myRemove={handleRemove} myToggle={handleToggle} />
          </TodoListTemplate>
  
        </div>
      );
    }
  }
  
  export default App;
  ```



### 5. TodoItemList

> TodoItemList.js : Todo 목록 가져오기



- #### components/TodoItemList.js

  ```js
  import React, { Component } from 'react';
  import TodoItem from './TodoItem';
  import { connect } from 'react-redux';
  import { fetchAllTodos } from '../actions';
  
  class TodoItemList extends Component {
  
      componentDidMount() {
          this.props.fetchAllTodos();
      }
  }
  const mapStateToProps = (state) => {
      return {
          todos: state.todos
      }
  }
  
  
  export default connect(mapStateToProps, { fetchAllTodos })(TodoItemList);
  ```

  - `componentDidMount()` 메서드에서 Action 생성 함수 `fetchAllTodos()` 를 호출한다.
  - **컴포넌트**를 **store** 에 연결 하기위해서는 **react-redux** 의 **connect** 함수를 사용
  - 상태를 연결시키는 **mapStateToProps**와, `fetchAllTodos()`를 **connect()**인자로 전달하고, **TodoItemList** 컴포넌트를 파라미터로 전달해주면, **redux store**에 연결된 새로운 컴포넌트가 만들어 진다.







### ※ 목록 추가, 제거, Toggle 작업은 파일로 첨부



