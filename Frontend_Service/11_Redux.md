# Redux

>  [Redux](https://velopert.com/3528)



![](https://i.imgur.com/nnYKPBo.png)

​															※ 출처 : https://velopert.com/3528





#### Flux

---

> [Flux 이해하기](http://bestalign.github.io/2015/10/06/cartoon-guide-to-flux/) 

- MVC(Model, View, Controller)

- Mode에서 Rendering을 위해 View로 데이터 전달

  \-  View에서 Model 데이터의 업데이트 발생

  \- 의존성 문제로 인해 다른 Model 데이터 업데이트

  

**=> 해결책**

	- Unidirectional Data Flow
	- 데이터는 단방향으로 전달
	- 새로운 데이터가 발생되면, 처음부터 흐름이 다시 시작 -> Flux





#### Redux

---

> [Redux 이해하기](http://bestalign.github.io/2015/10/26/cartoon-intro-to-redux/)





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

