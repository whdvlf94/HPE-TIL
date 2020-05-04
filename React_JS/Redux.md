# Redux



```js
//[Action과 Action 생성 함수]
const INCREMENT = 'INCREMENT';
const DECREMENT = 'DECREMENT';

//값을 증가시키는 액션
const increment = () => ({
type : INCREMENT
});

//가변적인 값이 들어가야 하면 파라미터를 넣어서 액션을 만든다.
const increment2 = (diff) => ({
type : INCREMENT,
diff : diff
});

//값을 감소시키는 액션
const decrement = (diff) => ({
type : DECREMENT,
diff : diff
});

const initialState = { number : 0 , foo:'bar', baz:'qux'};


//[Reducer 함수]
function counter(state = initialState, action) {
switch(action.type) {
    
case INCREMENT:
return {
  ...state,
number:state.number + action.diff
};
    
case DECREMENT:
return { 
  ...state,
  number:state.number - action.diff};
default:
return state;
}
}


//[Redux Store 생성]
//Action , Reducer 가 준비되면 Redux Store 생성 가능
const { createStore } = Redux;
const store = createStore(counter);

//[Subscribe]
//Redux Store의 상태가 바뀔 때마다 특정 함수를 실행
const unsubscribe = store.subscribe(() => {
console.log(store.getState());
});


//[dispatch로 액션 전달]
store.dispatch(increment2(10)); //10
store.dispatch(decrement(5)); //5
store.dispatch(increment2(3)); //8
```



## 1. Counter 실습



#### src 디렉토리

- **actions** : 액션 타입과 액션 생성자 파일을 저장
- **components** : 컴포넌트의 뷰에 해당하는 presentational 컴포넌트를 저장

- **containers** : store에 있는 상태를 props로 받아 오는 container 컴포넌트들을 저장

- **reducers** : 스토어의 기본 상태 값과 상태의 업데이트를 담당하는 reducer 파일들을 저장
- **utils** : 일부 컴포넌트에서 함께 사용하는 파일을 저장



### 흐름

`index.html` → `index.js` → `containers/App.js` → `containers/CounterConatiner.js` → `components/Counter.js`



### 1. 기본적인 틀 생성

### 2. Counter presentational 컴포넌트 생성

### 3. Action 생성



### 4. reducer 생성

> 액션의 type에 따라 변화를 일으키는 함수



- **state** , **action** 을 파라미터로 받는다.

- **state**를 직접 수정하면 안되고, **기존 상태 값에 원하는 값을 덮어 쓴 새로운 객체를 만들어 변환**해야 한다.

  

- #### src/reducers/index.js

  ```js
  import * as types from '../actions/ActionType';
  
  // 초기 상태를 정의합니다.
  const initialState = {
      color: 'black',
      number: 0
  };
  
  function counter(state = initialState, action) {
      switch (action.type) {
      case types.INCREMENT:
      return {
      ...state,
      number:state.number + 1
      };
      case types.DECREMENT:
      return {
      ...state,
      number:state.number - 1
      };
      case types.SET_COLOR:
      return {
      ...state,
      color:action.color
      };
      default:
      return state;
      }
      };
      export default counter;
  ```

  

### 5. store 생성, Provider 컴포넌트

> redux에서 가장 핵심적인 인스턴스. 현재 상태를 내장하고 있으며, subscribe 중인 함수들의 상태가 업데이트 될 때 마다 다시 실행되게 해준다.



- #### src/index.js

  ```js
  import React from 'react';
  import ReactDOM from 'react-dom';
  import './index.css';
  import App from './containers/App';
  import * as serviceWorker from './serviceWorker';
  import { createStore } from 'redux';
  // import {} : export 가 복수 개 인 경우 {}로 지정해준다.
  import reducers from './reducers';
  import { Provider } from 'react-redux';
  //index를 import 하는 경우 ./reducers/index 로 입력하지 않아도 된다.
  
  const store = createStore(reducers);
  //store 내에 reducers 등록
  
  ReactDOM.render(
    <React.StrictMode>
        //Provider : react-redux 라이브러이에 내장된 컴포넌트
  	  // 리액트 애플리케이션에서 손쉽게 스토어를 연동할 수 있도록 도와주는 컴포넌트
      <Provider store={store}>
        <App />
      </Provider>
    </React.StrictMode>,
    document.getElementById('root')
  );
  
  serviceWorker.unregister();
  ```

  - **redux** 에서 **createStore** 를 불러와 **reducer(앞서 만든 것)** 을 전달하며 **store** 를 생성한다.
  - **Provider** : 리액트 애플리케이션에서 손쉽게 **store** 를 연동할 수 있도록 도와주는 컴포넌트



### 6. CounterContainer 생성

> react-redux의 connect 함수를 사용해 Counter 컴포넌트와 store를 연결해준다.



- #### CounterContainer.js

  ```js
  import { connect } from 'react-redux';
  import Counter from '../components/Counter';
  import * as actions from '../actions'
  
  //store에 저장된 상태변수(state variable)를 Counter의 props로 연결한다.
  const mapStateProps = (state) => ({
      color: state.color,
      number: state.number
  })
  
  //Action 생성 함수를 dispatch 하고, Counter의 function Props로 연결한다.
  const mapDispatchProps = (dispatch) => ({
      onInc: () => dispatch(actions.increment()),
      onDec: () => dispatch(actions.decrement()),
      onColor: () => {
          const color = 'black';
          dispatch(actions.setColor(color));
      }
  });
  
  //Counter 컴포넌트를 애플리케이션의 데이터 레이어와 묶는 역할을 한다.
  const CounterContainer = connect(mapStateProps,mapDispatchProps)(Counter);
  export default CounterContainer;
  ```

  - **connect( [mapStateProps] , [mapStateToProps] )( Counter )**
    - **mapStateProps** : `store.getState()` 결과 값인 

