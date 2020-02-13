# Redux

>  [Redux](https://velopert.com/3528)



## 1. Flux & Redux?

> Redux를 사용하면 상태 값을 Component에 종속시키지 않고, 상태 관리는 Component 바깥에서 관리 할 수 있다.







#### Flux

---

> [Flux 이해하기](http://bestalign.github.io/2015/10/06/cartoon-guide-to-flux/) 



**=> 문제점**

- MVC(Model, View, Controller)

- Mode에서 Rendering을 위해 View로 데이터 전달

  \-  View에서 Model 데이터의 업데이트 발생

  \- 의존성 문제로 인해 다른 Model 데이터 업데이트



**※ 위와 같은 상황에서 변경들이 비동기적으로(asynchronously)  생길 수도 있고, 하나의 변경이 다수의 변경들을 일으킬 수 있다.**



**=> 해결책**

- **Unidirectional Data Flow**
- 데이터는 **단방향**으로 전달
- 새로운 데이터가 발생되면, 처음부터 흐름이 다시 시작 -> Flux
- 

![](http://bestalign.github.io/2015/10/06/cartoon-guide-to-flux/05.png)

​						(※ 출처 : http://bestalign.github.io/2015/10/06/cartoon-guide-to-flux/)



#### Flux 이해하기

---



- **action creator**

  - 어떤 메세지를 보낼지 알려주면 나머지 시스템이 이해할 수 있는 포맷으로 바꿔준다.

  - `type` 과 `payload`를 포함한 액션 생성

  - `type` : 시스템에 정의 된 액션 들 중 하나 (ex. MESSAGE_CREATE)
  
    

**=> 액션 메세지를 생성한 후, `dispatcher`로 넘겨줌**

​    

- **dispatcher**

  - `callback` 이 등록되어 있는 곳

  - 액션을 보낼 필요가 있는 모든 store를 보유

  - 액션 생성자로부터 액션이 넘어오면 모든 스토어에 액션을 보냄

    - 동기적으로(synchronously) 실행, store들 사이에 의존성이 있다면 `waitFor()`을 사용하여 처리
  
  

**=> 액션 `type`과 관계없이 등록된 여러 `store`로 액션을 보냄, `store`는 특정 액션을 `subscribe`하지 않고 모든 액션을 받는다.**



- **store**
  - App 내의 모든 상태와 그와 관련된 `logic`을 가지고 있다.
  - 모든 상태 변경은 `store`에 의해 결정
  - 상태 변경 요청을 위해서는 반드시 `dispatch(action)`을 거쳐야 한다.
  - `store` 내부에는 `switch statement`를 사용해서 처리할 액션과 무시할 액션 결정
  - 상태 변경 완료 후, 변경 이벤트를 `컨트롤러 뷰(controller View)`에 전달 



- **controller view & view**
  - `view` : 상태를 가져오고 유저에게 UI 제공, 입력받을 화면을 렌더링 하는 역할
  - `controller view` : `store` 와 `view` 사이의 중간 관리자 역할 
    - `store` →`controller view` → `store`





#### Redux 이해하기

---

> [Redux 이해하기](http://bestalign.github.io/2015/10/26/cartoon-intro-to-redux/)



**Flux to Redux, why?**

- `store` 의 코드는 애플리케이션 상태를 삭제하지 않고는 reloading이 불가능하다.

- 액션에 대한 각각의 객체가 완벽히 독립적이지 않다.

  ...



- **action creators**
  - Flux에서 액션 생성자를 그대로 가져옴
  - **차이점** : Redux 액션 생성자는 `dispatcher`로 액션을 보내지 않는다. 대신, 포멧을 바꾼 뒤 액션을 돌려줌
- **store**
  - 상태 트리(state tree) 전체를 유지하는 책임
  - 액션이 들어왔을 때, 상태 변화의 필요 여부는 위임





- **reducer**
  - 

#### React Redux

---



**기존의 React**

- 구조가 복잡한 애플리케이션

  \- 관련 없는 컴포넌트가 props를 전달해 줘야 함

  

![](https://i.imgur.com/nWgg01Z.png)

​																		※ 출처 : https://velopert.com/3528



****







## 2. 실습

```
1. Reducer

   \- business logic (데이터 처리, 상태 처리)

   \- Root reducer에 Reducer를 추가 (index.js)

   \- src/reducers/reducer-book.js

   \- src/reducers/reducer-active-book.js

   

2. src/index.js

   \-  reducer를 가지고 store 생성

   \- App.js 실행 시 store 지정

   

3. 사용자의 요청 작업 (이벤트 등) - Action

   \- src/actions/index.js 등록 -> selectBook

   \- Action -> type(BOOK_SELECTED), payload (상태 값)

   

4. 사용자 View (or Container)  component (view or container 둘 중 이름 아무거나)

   \- src/containers/book-list.js

   \- src/containers/book-detail.js

   

5. Component하고 Reducer(Store) 하고 연결

   \- mapStateToProps(state)

   \- mapDispatchToProps(dispatch)

   \- connect() 함수 사용

   \-  ex1) connect(mapStateToProps, mapDispatchToProps)(BookList)

   \-  ex2) connect(mapStateToProps)(BookDetail);


```

---



- **App.js**

```js
import React from 'react';
import './App.css';
import BookList from './Container/Book-list';
import BookDetail from './Container/Book-detail'

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <BookList />
        <BookDetail />
      </header>
    </div>
  );
}

export default App;

```





### 1. Reducer

---



- **index.js**

> src/Reducers/index.js

```js
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
```

\- Biz logic (데이터 처리, 상태 처리)

\- Root reducer에 Reducer를 추가 (index.js)



- **Reducer.js**

> src/Reducers/

```js
// src/reducers/reducer-books.js

export default function () {
    return [
        {title: 'javascript', page:'100'},
        {title: 'Docker and kubernetes', page:'110'},
        {title: 'Java programming', page:'120'},
        {title: 'Microservice Architecture', page:'130'}
    ]
}

// 목록을 반환시켜 주는 logic


------------------------------------------------------------

// src/reducers/reducer-active-books.js

export default function(state=null, action) { //ES6 문법 : sate=null 초기값 지정
    switch(action.type) {
        case 'BOOK_SELECTED':
            return action.payload;
        default:
            return state;
    }
}

```

\- src/reducers/reducer-book.js

\- src/reducers/reducer-active-book.js





### 2. src/index.js

---



- **index.js**

> src/index.js

```js
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {Provider} from 'react-redux';
import {createStore} from 'redux';
import reducer from './Reducers'; //reducers 폴더 내에 있는 index.js, reducer-books.js 모두 사용

const store = createStore(reducer);

ReactDOM.render(
<Provider store={store}>
<App />
</Provider>, document.getElementById('root'));

```

\-  reducer를 가지고 store 생성

\- App.js 실행 시 store 지정



### 3. 사용자의 요청 작업 (이벤트 등)

---

> src>Actions>index.js



- **index.js**

```js
export function selectBook (book) {
    return {
        type: 'BOOK_SELECTED', //대문자 그리고 _ 사용
        payload: book

    }
}
```

\- src/actions/index.js 등록 -> selectBook

\- Action -> type(BOOK_SELECTED), payload (상태 값)





### 4. 사용자 Component

---

> Component의 root는 Container 이지만, 이번 예제에서는 Container에서만 진행했음



- **Book-list.js**

```js
import React, { Component } from 'react';
import {connect} from 'react-redux';
// component와 redux의 connect를 해주는 함수
import {bindActionCreators} from 'redux';
import {selectBook} from '../Actions/index';

class BookList extends Component {
    renderList() {
        return this.props.books.map(book => {

            return (
                <li key={book.title} onClick={() => this.props.selectBook(book)}>{book.title}</li> // Booklist 배열 -> 반복처리
            )
        })
    }
    render() {
        return (
            <ul>
                {this.renderList()}
            </ul>

        );
    }
}

function mapStateToProps(state) {
    return {
        books: state.books
    }
}

function mapDispatchToProps (dispatch) {
    return bindActionCreators({selectBook: selectBook}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(BookList);
```

\- src/containers/book-list.js



- **Book-detail.js**

```js
import React, { Component } from 'react';
import {connect} from 'react-redux';

class BookDetail extends Component {
    render() {
        if (!this.props.book) {
            return <div>Select a book to get start</div>
        }
        return (
            <div>
                 <h3>Detail for:</h3>
                 <div>Title: {this.props.book.title}</div>
                 <div>Page: {this.props.book.page}</div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        book: state.activeBook
    }
}
export default connect(mapStateToProps)(BookDetail);
```

\- src/containers/book-detail.js



### 5. Component ↔ Reducer(Store) 연결

---



```
- mapStateToProps(state)

- mapDispatchToProps(dispatch)

- connect() 함수 사용

-  ex1) connect(mapStateToProps, mapDispatchToProps)(BookList)

-  ex2) connect(mapStateToProps)(BookDetail);
```

