# React



### 1. SPA

---
> 단일 페이지 웹 애플리케이션

- javaScript 문제점
    1) 구현의 어려움
    2) 언어의 모호성
    3) Cross Browsing의 한계


- jQuery
    1) Open Source JavaScript Library
    2) JavaScript의 DOM 처리의 어려움과 Cross Browsing 해결





- npm : node.js package management

**- npx : npm 와 비슷, npx는 필요한 라이브러리만 다운 받아 사용할 수 있다.**





**Frontend library/framework : DOM 관리 및 상태 값 업데이트**

---

>  html에서 동적인 관리를 위해서 javascript 사용. 이 때, framework로써 Angular, Backbone, Ember, React를 사용한다.



※ **DOM(Document Object Model)** : 웹 페이지에 대한 인터페이스. 여러 프로그램들이 페이지의 컨텐츠 및 구조, 그리고 스타일을 읽고 조작할 수 있도록 API 제공

[DOM 개념 참조](https://wit.nts-corp.com/2019/02/14/5522)





### 2. React - UI 라이브러리 

---

>  UI 기능만 제공, HTTP 클라이언트, 라우터, 상태 관리 등의 기능이 내장되어 있지 않기 때문에, 자유롭게 사용가능하며, 직접 라이브러리 용이



 \- UI를 자동으로 업데이트 해준다. 

\- **가상 돔(virtual dom)**을 통해서 UI를 빠르게 업데이트 한다.

​	※ **가상 돔(virtual dom)** : 이전 U 상태를 메모리에 유지해서, 변경될 UI의 최소 집합을 계산하는 기술. 이 덕분에 불필요한 UI 업데이트는 줄고, 성능은 좋아진다.



\- 높은 자유도



- **react Project**
---
  - Node.js

  - Yarn

    - facebook에서 만든 자바스크립트 패키지 매니저, 	설치) npm install -g yarn
    - yarn -version
    - 더 나은 속도, 더 나은 캐싱 시스템

    

  - Webpack

    - 리액트 프로젝트는 컴포넌트를 여러가지 파일로 분리해서 저장 -> JSX 문법
    - 여러가지 파일을 한개로 결합하기 위한 도구

  - Babel

    - JavaScript Code를 변환해 주는 컴파일러. 최신 자바스크립트 문법을 지원하지 않는 환경에서도 최신 문법을 사용할 수 있다.

    ```javascript
    [ES7]
    [1,2,3].map(n => n**n);
    
    
    [ES5]
    "use strict"
    [1,2,3].map(function(n) {
        return Math.pow(n,n);
    });
    
    // ES7(최신) 문법을 지원하지 않는 환경에서 ES7와 같이 입력해도, ES5와 같은 문법으로 변환해준다.
    ```

---





### 3. Create-react

---

- 페이스북에서 제공하는 react template
- [create-react-app](https://github.com/facebook/create-react-app)



- **설치 방법**

```shell
npx create-react-app my-app
cd my-app
npm start

# localhost:3000 시작
```



- **src > App.js : UI 수정**

```javascript
import React from 'react';
# 리액트 conponent, 외부 Component, CSS, images 등 불러오기

function App() {
# class App extends Component () {}
  return (
    <div className="App">
      <header className="App-header">
       Hello, React
      </header>
    </div>
  );
}
# 컴포넌트 생성 -> Class or Function, 내부에서 JSX를 반환

export default App;
# 외부에서 사용할 수 있도록..

```

**※ 모든 React 파일은 시작을 App.js에서 시작한다.**

---







- **class , function 표현 방법**

```js
import React, {Component} from 'react';
// class 가 있는 경우에만 {Component} 작성
// 없는 경우에는 import React from 'react'; 로 작성하면 된다.

#class
class App extends Component {
  render() {
// class를 만들 경우에만 render() 호출, function 에는 사용 x
    return (
      <div>
        Hello, React with class type
      </div>
// return() 아래에 root element는 반드시 1개가 와야한다.
// 여러 개의 element가 존재할 경우 전체를 포함하는 root element가 있어야 한다.
// <Fragment>     
    )
  }
}

#function
function App () {
  return (
    <div className="App">

    </div>

  )
}
export default App;
```
**※ Class 적용을 더 많이 사용한다.**




**- Fragment 사용하기**
```js

import React, {Component} from 'react';
import {Fragment} from 'react';
class App extends Component {
  render() {
    // class를 만들 경우에만 render() 호출, function 에는 사용 x
    return (
      <Fragment>
      <div>
        Hello, React with class type
      </div>
      <div>
        Hello, React with class type
      </div>
      </Fragment>
      // 2개의 element가 들어있지만, Fragment로 설정했기 때문에 오류가 나지 않는다.
      // 단, 사용하기 전에 import 과정을 거쳐야 함.
    )
  }
}

export default App;
```



**- Const name**

```js
import React, {Component} from 'react';
import './App.css'

class App extends Component {
  render() {
    const name = "YJP"
    return (
      <div className='App-header'>
        <h1>

        Hello, {name}
        </h1>
      </div>

      

    )
  }
}

export default App;
```



**- const time**

```js
import React, {Component} from 'react';
import './App.css'

class App extends Component {
  render() {
    const time = 10;
    const name = "YJP"
    return (

      <div className='App-header'>
        {time < 15
        ? (<h1>Hello, {name}</h1>) 
        // Hellod, YJP 출력
        : (<h1>Bye, {name}</h1>) 
        }
      </div>
      
    )
  }
}


class App extends Component {
  render() {
    const time = 19;
    const name = "YJP"
    return (

      <div className='App-header'>
        {
          (function(){
            if (time < 12) return (<div>Good morning</div>);
            if (time < 18) return (<div>Good afternoon</div>);
            if (time < 22) return (<div>Good eveing</div>);
          })()
        }
      </div>
```

---



## 4. Props ↔ State



**- style 작성 규칙**

```js
import React, {Component} from 'react';
import './App.css'

class App extends Component {
  render() {
    const name = 'React'
    const css = {
      color: 'red',
      background: 'black',
      padding: '20px',
      fontSize: '25px'

    }
    return (

      <div className='App-header'>
      // 외부 함수 사용. App.css에 있는 App-header 호출
        <h1 style={css}> Hello, {name}</h1>
        // 내부 함수 사용
      </div>
      
    )
  }
}
```

**※ style 적용 방법**

1) ClassName : 외부의 함수를 호출해서 사용하는 방법
2) style : 내부 함수를 호출해서 사용하는 방법



**- props**
- 부모 컴포넌트가 자식 컴포넌트에게 전달하는 값
- 자식 컴포넌트에서는 props의 값을 수정할 수 없음
- props 값은 `this.` 키워드를 이용하여 사용

**- state**
- 컴포넌트 내부에 선언하여 사용되는 보관용 데이터 값
- 동적인 데이터 처리



## Props 사용 - 부모 컴포넌트





- **실습**

---



- MyIntro.js
```javascript
import React, {Component} from 'react';

class MyIntro extends Component {
    render() {
        const css = {
            color: 'red',
            fontSize: '50px'
        };
        return (
            <div style={css}>
                안녕하세요, 제 이름은 <b>{this.props.name}</b> 입니다.
            </div>
              /* 자식 컴포넌트는 부모 컴포넌트가 지정한 값을 this.props.[const 명]을 이용하여 불러올 수 있다.
                수정은 불가 */
        )
    }
}
export default MyIntro;
```


- App.js
```js
import React, {Component} from 'react';
import './App.css'
import MyIntro from './MyIntro';

class App extends Component {
  render() {
    // const name1 = '윤종필'
    // <MyIntro name = {name1} /> 으로 사용할 수도 있음
    return (
      <MyIntro name='윤종필'/>

      // 외부 Component 사용, App.js는 부모 컴포넌트 
      // 부모가 지정한 name 

      )
    }
  }

export default App;
```



#### 1) Class 이용하기(권장)

---

- App.js
```js
import React, {Component} from 'react';
import './App.css'
import MyIntro from './MyIntro';

class App extends Component {
  render() {
    const card = {
      name: "윤종필",
      email: "abc@gmail.com",
      phone: "1234"

    };
    return (
      <MyIntro card={card} />
      

      )
    }
  }

export default App;
```

- MyIntro.js
```js
import React, { Component } from 'react';

class MyIntro extends Component {
    render() {
        const css = {
            color: 'red',
            fontSize: '50px'
        };

        return (

            <div style={css}>
            안녕하세요, 제 이름은 <b>{this.props.card.name},
            {this.props.card.email},
           {this.props.card.phone}</b> 입니다.
     
            </div>
        )
    }
}

export default MyIntro;
```



#### 2) function 이용

---

- MyIntro2.js
```js
import React from 'react';

const MyIntro2 = function ({card}) {
// const MyIntro2 = ({card}) => {} 와 동일
return (
    
    <div>
    안녕하세요, 제 이름은 <b>{card.name} 입니다.
    제 이메일은 {card.email} 입니다.
    제 연락처는{card.phone}</b> 입니다.

    </div>
)

}

export default MyIntro2;
```

- App.js
```js
import React, {Component} from 'react';
import MyIntro2 from './MyIntro2';

class App extends Component {
  render() {
    const card = {
      name: "윤종필",
      email: "abc@gmail.com",
      phone: "1234"

    };
    return (
      <MyIntro2 card={card} />
      

      )
    }
  }

export default App;
```



## state 사용 - 자식 컴포넌트

- Counter.js
```js
import React, { Component } from 'react';

class Counter extends Component {
    state = { 
        count: 100
        
    }
    
    handleIncrease = () => {
        this.setState ({
        // this.setState 를 통해서만 state 내에 포함되어 있는 내용을 수정 할 수 있다.
           count: this.state.count +1
        })
        
    }    
    handleDecrease = () => {
        this.setState ({
            count: this.state.count -1

        })
    }
    render() {
        return (
            <div>
                <h1>Counter</h1>
                <h2>{this.state.count}</h2>
                <button onClick={this.handleIncrease}>+</button>
                <button onClick={this.handleDecrease}>-</button>
            </div>
        )
    }

}

export default Counter;
```

- App.js
```js
import React, {Component} from 'react';
import Counter from './Counter';

class App extends Component {
  render() {

    return (
      <div>
        <Counter />
      </div>
      

      )
    }
  }

export default App;
```





### 6. 전개 연산자

---

- Counter.js
```js
import React, { Component } from 'react';

class Counter extends Component {
    state = { 
        count: 100,
        info: {
            name: 'React',
            age: '10'
        }
        
    }
    
    handleIncrease = () => {
        this.setState ({
        // this.setState 를 통해 state 내에 포함되어 있는 내용을 수정 할 수 있다.
           count: this.state.count +1
        })
        
    }    
    handleDecrease = () => {
        this.setState ({
            count: this.state.count -1

        })
    }
    handleChangeInfo = () => {
        this.setState ({
            info: {
                ...this.state.info,
                name: 'yjp'
            }
            // ...this.state : name 빼고 나머지 값들은 이전 값 그대로 사용

        })
    }
    render() {
        return (
            <div>
                <h1>Counter</h1>
                <h2>{this.state.count}</h2>
                <button onClick={this.handleIncrease}>+</button>
                <button onClick={this.handleDecrease}>-</button>
                <button onClick={this.handleChangeInfo}>Change info name</button>
                <h2>{this.state.info.name}/{this.state.info.age}</h2>
            </div>
        )
    }

}

export default Counter;
```



### 7. 생명 주기 메서드
---

**- construcotr 메서드 (Counter.js)**

```js
import React, { Component } from 'react';

class Counter extends Component {
    state = { 
        count: 100,
        info: {
            name: 'React',
            age: '10'
        }
        
    }

    constructor(props) {
        super(props);
        // this.state.count = this.props.init;
        console.log('call constrouctor')
        }
        

    componentDidMount() {
        console.log('componenetDidMount')
    }

    shouldComponentUpdate(nextProps, nextState) {
        // 5의 배수라면 리렌더링 하지 않음
        console.log('shouldComponentUpdate')
        if (nextState.count % 5 ===0) return false;
        return true;
    }
    
    componentWillUpdate(nextProps, nextState) {
        console.log('componentWillUpdate')
    
    }
    componentDidUpdate(prevProps, prevState) {
        console.log('componentDidUpdate');
    }

    handleIncrease = () => {
        this.setState ({
        // this.setState 를 통해 state 내에 포함되어 있는 내용을 수정 할 수 있다.
           count: this.state.count +1
        })
        
    }    
    handleDecrease = () => {
        this.setState ({
            count: this.state.count -1

        })
    }
    handleChangeInfo = () => {
        this.setState ({
            info: {
                ...this.state.info,
                name: 'yjp'
            }
            // age: this.state.info.age = '20'
        })
    }
    render() {
        return (
            <div>
                <h1>Counter</h1>
                <b>{this.state.count}</b>
                <button onClick={this.handleIncrease}>+</button>
                <button onClick={this.handleDecrease}>-</button>
                <button onClick={this.handleChangeInfo}>Change info name</button>
                <h2>{this.state.info.name}/{this.state.info.age}</h2>
            </div>
        )
    }

}

export default Counter;
```





- **Error 발생 시키기(Error.js)**

```js
import React, { Component } from 'react';

const ErrorObject = () => {
    throw (new Error('에러 발생'));
 	# Error라는 객체 생성   
}

class Counter extends Component {
    state = { 
        count: 0,
        info: {
            name: 'React',
            age: '10',
            error: false
        }
        
    }
    componentDidCatch(error, info) {
        this.setState ({
            error: true            
        })
    }


    handleIncrease = () => {
        this.setState ({
        // this.setState 를 통해 state 내에 포함되어 있는 내용을 수정 할 수 있다.
           count: this.state.count +1
        })
        
    }    
    handleDecrease = () => {
        this.setState ({
            count: this.state.count -1

        })
    }
    handleChangeInfo = () => {
        this.setState ({
            info: {
                ...this.state.info,
                name: 'yjp'
            }
            // age: this.state.info.age = '20'
        })
    }
    render() {
        if(this.state.error) return (<h1>에러가 발생되었습니다.</h1>);
		# return 값을 받았으므로 아래 문장을 출력하지 않고 종료된다.
                                     
        return (
            <div>
                <h1>Counter</h1>
                <b>{this.state.count}</b>
                {this.state.count == 3 && <ErrorObject/>}
                <button onClick={this.handleIncrease}>+</button>
                <button onClick={this.handleDecrease}>-</button>
                <button onClick={this.handleChangeInfo}>Change info name</button>
                <h2>{this.state.info.name}/{this.state.info.age}</h2>
            </div>
        )
    }

}

export default Counter;
```

