# React



#### ※ 흐름

`index.html` → `index.js` → `App.js`



## 1. JSX

> React.js는 일반 JavaScript 문법이 아닌 JSX 문법을 사용하여 UI를 템플릿 화 한다.



### 1.1) Nested Element

- **Component**에서 여러 **Element**를 렌더링 할 때, 이 **Element** 들은 필수적으로 **Container Element** 안에 포함시켜 줘야 한다.
  - **Container Element** : `<div>` 혹은 `<React.Fragment>`





## 2. Component

> React Component에서 다루는 데이터는 props와 state로 나누어 진다.



```
Component 생성 단축키 : rcc
함수형 Component 생성 단축키 : rsc
defaultProps 생성 단축키 : rdp
propTypes 생성 단축키 : rpt
```



### 2.1) props

- 자식 컴포넌트에서는 **props**를 받아 오기만 하고, 수정할 수 없다.
- **state**는 컴포넌트 내부에서 선언하며, 내부에서 값을 변경할 수 있다.



- **App.js(props)**

  ```js
  import React, { Component } from 'react';
  import MyComponent from './components/MyComponent';
  
  class App extends Component {
    render() {
      return (
        <React.Fragment>
          <MyComponent name="React"/>
        </React.Fragment>
      );
    }
  }
  export default App;
  ```

  - **MyComponent**에 **React**라는 **name**값을 전달한다.

    

- **MyComponent.js(state)**

  ```js
  import React, { Component } from 'react';
  
  class MyComponent extends Component {
      render() {
          const {name} =this.props
          return (
              <div>
                  Hello {name}
              </div>
          );
      }
  }
  export default MyComponent;
  ```

  - **비할당 구조**를 이용하여 이름 값을 상속할 수 있다.



#### DefaultProps , propTypes

- **MyComponent.js**

  ```js
  import React, { Component } from 'react';
  class MyComponent extends Component {
  (...)
  }
  MyComponent.defaultProps = {
  	name: '기본이름'
  	//props name의 기본 값을 '기본이름'으로 등록
      
  };
  MyComponent.propTypes = {
      name:propTypes.string
      //name 값을 String Type으로 출력
  };
  export default MyComponent;
  
  ```

  - 부모 컴포넌트(**App.js**)에서 아무런 설정 없이 호출하게 되면, **기본이름**이 출력된다.
  - 출력의 형태를 **String** 타입으로 출력한다.



**※ 다른 방법**

- **App.js**

  ```js
  import React, { Component } from 'react';
  import MyComponent from './components/MyComponent';
  
  class App extends Component {
    render() {
      return (
        <React.Fragment>
          <MyComponent name="React"/>	//Hello React
          <MyComponent/>	// Hello 기본이름 
          <MyComponent age={20}/>	 //Hello 20
          <MyComponent name={"300"}/>	//Hello 300
        </React.Fragment>
      );
    }
  }
  
  export default App;
  ```

  

- **MyComponent.js**

  ```js
  import React, { Component } from 'react';
  import propTypes from 'prop-types';
  
  class MyComponent extends Component {
      static defaultProps = {
          name:'기본이름',
      }
      static propTypes = {
          name:propTypes.string,
          age:propTypes.number.isRequired
      }
      render() {
          const {name, age} =this.props
          return (
              <div>
                  Hello {name} / {age}
              </div>
          );
      }
  }
  export default MyComponent;
  ```

  - **isRequried** :필수 propTypes를 설정하기 위해서 **isRequired**를 입력해야 한다.



#### 함수형 컴포넌트



- **App.js**

  ```js
  import React, { Component } from 'react';
  import MyComponentFunc from './components/MyComponentFunc';
  
  class App extends Component {
    render() {
      return (
        <React.Fragment>
          <MyComponentFunc name="함수형컴포넌트" age={15}/>
        </React.Fragment>
      );
    }
  }
  export default App;
  ```

  

- **MyComponentFunc.js**

  ```js
  import React from 'react';
  
  const MyComponentFunc = ({name,age}) => {
  // function MyComponentFunce(props)  
      return (
          <div>
              부모로 부터 받는 상태변수 : {name} / {age}
          </div>
      );
  };
  export default MyComponentFunc;
  ```

  - **props**만 받아와서 보여 주기만 하는 경우, **에로우 함수** 형태로 컴포넌트를 작성할 수 있다.
  - ` const {name, age} =this.props` 처럼 선언하지 않아도 된다.





### 2.2) State

- 컴포넌트 내부에서 읽거나 변경할 수 있는 값을 사용하기 위해서는 **State**를 사용해야 한다.
- **State** 는 언제나 기본 값을 설정할 수 있으며, **this.setState()**메서드를 통해서만 값을 변경할 수 있다.



- **MyComponent.js**

  ```js
  	//state variable
  	state = {
          number:0
      }
  
      render() {
          const {name, age} =this.props;
          const {number} = this.state;
          return (
              <div>
                  Hello {name} / {age}
                  <p>Number 값은 : {number}</p>
                  <button onClick={()=>(this.setState({number:number+1}))}>증가</button>
                  <button onClick={()=>(this.setState({number:number-1}))}>감소</button>
              </div>
          );
      }
  ```

  - **this.setState()**를 이용해 **State**값을 업데이트



![state](https://user-images.githubusercontent.com/58682321/80785443-3d312e80-8bbb-11ea-9929-4762475c7cdd.PNG)

**※ 다른 방법**

- **MyComponent.js**

  ```js
      //state variable
      state = {
          number:0
      }
  
      handleIncrease = () => {
          this.setState({number:this.state.number +1})
      }
  
      handleDecrease = () => {
          this.setState({number:this.state.number -1})
      }
  
  
      render() {
          const {name, age} =this.props;
          const {number} = this.state;
          const {handleIncrease} = this; //이 선언을 통해 onClick에서 this 를 쓰지않아도 된다.
          const {handleDecrease} = this;
          return (
              <div>
                  Hello {name} / {age}
                  <p>Number 값은 : {number}</p>
                  <button onClick={handleIncrease}>+</button>
                  <button onClick={handleDecrease}>-</button>
              </div>
          );
      }
  ```

  - **에로우 함수**를 **onClick**에 직접 사용하기 보다는 **handler 함수**를 별도로 작성해 호출하는 것이 더 효율 적이다.
  - 또한, **handler 함수**를 아래에 별도로 선언하여 **onClick**에서 **this**를 사용하지 않고 호출할 수 있다.

 



## 3. Event



- **div, button, input, form, span** 등 **DOM**요소에는 이벤트를 설정할 수 있다.
- **직접 만든 컴포넌트**에는 이벤트를 설정할 수 없다.
  - `App.js`에서 **\<MyComponents>**와 같은 컴포넌트



- **MyComponent.js**

  ```js
      handleDecrease = (e) => {
          console.log(e.target.value);
          this.setState({number:this.state.number -1})
      }
      
      render() {
          const {handleDecrease} = this;
          return (
              <div>
                  <button onClick={handleDecrease} value="decrease">-</button>
              </div>
          );
      }
  ----------------------------
  [Console]
  decrease
  ```

  

### 3.1 Event 핸들링



#### Method , Input

- **MyComponent.js**

  ```js
  	//handleChange() 메서드 구현 및 handler method 연결
  	handleChange = (e) => {
          this.setState({
              message: e.target.value
  
          })
      }
  
  
      render() {
          const { name, age } = this.props;
  
          const { number } = this.state;
          const { message } = this.state
  
          const { handleIncrease, handleDecrease, handleChange } = this;
          return (
              <div>
  
  
                  Hello {name} / {age}
                  <p>Number 값은 : {number}</p>
                  <button onClick={handleIncrease}>+</button>
                  <button onClick={handleDecrease} value="decrease">-</button><br />
                  <input type="text" value={message} onChange={handleChange} /><br/>
                  {/* MyComponent State 값 변경 */}
  
                  <button onClick={()=>(this.setState({
                      message:''
                  }))}>초기화</button>
                  {/* MyComponent State 값 초기화 작업 */}
                  
  
              </div>
          );
      }
  ```

  - `<input type="text" value={message} onChange={handleChange} />`

    - **input**에 입력한 값을 **State**에 저장
  - `<button onClick={()=>(this.setState({message:''}))}>초기화</button>`
    - **초기화** 버튼 클릭 시, **State** 값 초기화



#### 여러 개의 input과 state 관리

- **EventPractice.js**

  ```js
  import React, { Component } from 'react';
  class EventPractice extends Component {
      state = { message: '', username: '' }
      handleChange = (e) => {
          this.setState({
              [e.target.name]: e.target.value
          });
      }
      handleClick = () => {
          alert(this.state.username + ':' + this.state.message);
          this.setState({
              message: '',
              username: ''
          });
      }
  <input name="message" value={this.state.username} onChange={this.handleChange} />
  <input name="username" value={this.state.message} onChange={this.handleChange} />
  <button onClick={this.handleClick}>확인</button>
  }
  ```

  - `[e.target.name]: e.target.value`설정을 통해 `<input name="">`에서 어떤 **name** 값을 입력하느냐에 따라 그 **name** 자리에 알맞는 값(**value**)이 입력된다.







## 4. ref:DOM에 이름달기



- **MyComponent.js**

  ```js
  {/* MyComponent State 값 변경 */}
  <input type="text" value={message} onChange={handleChange} 
  ref={(ref) => this.mymessage=ref}/><br />
  
  {/* 클릭 시 input 작업 활성화 */}
  <button onClick={() =>this.mymessage.focus()}>포커스주기</button>
  ```

  - **포커스 주기** 버튼 클릭 시, **input 입력 창 활성화**





## 5. 컴포넌트 반복



#### map() 함수



- **Map() 함수**

  ```js
      state = {
          number: 0,
          message: '',
          messages: ['Angular', 'React', 'Vue', 'Ember']
      }
  
  // map.((속성,index) => ())
  	const msgList = messages.map((msg,index) => (
              <li key={index}>{msg}</li>));
  
  		<ul>
  			{msgList}
  		</ul>
  ------------------------
  - Angular
  - React
  - Vue
  - Ember
  ```

  - **Key** 값을 사용하여 어떤 변화가 일어났는지 빠르게 감지할 수 있다. 즉, 리스트를 순차적으로 모두 비교하지 않는다.



- **배열에 데이터 추가 기능 구현**

  ```js
      handleInsert = () => {
          const { message, messages } = this.state;
          this.setState({
              messages: messages.concat(message),
              message: ''
          });
      };
  
  	handleEnter = (e) => {
          if (e.key === 'Enter') {
              this.handleInsert();
          }
      }
      
  	const {handleEnter} = this;
  
   	<input type="text" value={message} onChange={handleChange}
      	ref={(ref) => this.mymessage = ref}
  		onKeyPress={handleEnter}/><br />
  
  ```

  - input 창에 값(**message**)을 입력하고 **Enter** 를 누르면, **handleEnter()** 메서드가 실행된다.
  - 이때, 이벤트 값이 **Enter** 이므로, **handleInsert()** 메서드가 실행된다.
  - 입력한 값(**message**)은 **messages**에 추가되고, **message**는 초기화 되며 작업이 완료된다.



- **데이터 삭제 기능 구현**

  ```js
      handleRemove = (index) => {
          const {messages} = this.state;
          this.setState({
              messages: messages.filter((item,idx)=>(idx !== index))
          })
      }
  	
      const { handleRemove } = this;
  
      
  	const msgList = messages.map((msg, idx) => (
  	<li key={idx} onDoubleClick={() => handleRemove(idx)}>{msg}</li> ));
  ```

  - **\<li>** 태그 더블 클릭 시, **handleRemove()** 메서드에 **idx** 키 값을 넘겨준다.
  - **handleRemove()** 에서 **키 값**을 받아 **messages** 배열의 **인덱스 값**과 비교한다.
  - **filter** 를 이용해 **키 값**과 **인덱스 값**이 다른 것만 배열에 남겨둔다.

