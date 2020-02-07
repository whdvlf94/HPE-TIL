# React Router

- **SPA**에서의 라우팅 문제를 해결하기 위해 사용되는 네비게이션 라이브러리

- 브라우저 내장 객체 사용

  \-  loaction

  \-  history

- React Router

  \-  Web

  \-  Natvie

  \-  react-router-dom 라이브러리 필요



### 실습

---

>  ※ vscode 에서 Reactjs code snippets 다운, rcc or rcs 로 파일 형식 작성 가능



![Untitled Diagram (2)](https://user-images.githubusercontent.com/58682321/74005281-ab4ec680-49bb-11ea-83fb-9c2ee31a4a48.png)







- **App.js**

```react
import React from 'react';
import Router from 'Routers';


const App = () => {
  return (
    <Router/>

  );
};

export default App;
```



- **Routers.js**

```react
import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Page1 from './routers/Page1';
import Page2 from './routers/Page2';
import Page3 from './routers/Page3';
import Header from './components/Header'

const Routers = () => {
  return (
    <Router>
      <Header />
      <Route path="/my_note1" component={Page1} />
      <Route path="/my_note2" component={Page2} />
      <Route path="/my_note3" component={Page3} />
    </Router>
  );
};

export default Routers;

```



- **Header.js**

```react
import React from 'react';
import {Link} from 'react-router-dom';

const Header = () => {
    return (
        <div>
            <ul>
                <li><Link to="my_note1">Page1</Link></li>
                <li><Link to="my_note2">Page2</Link></li>
                <li><Link to="my_note3">Page3</Link></li>
            </ul>
            
        </div>
    );
};

export default Header;
```





### Router.js 통합 하기

---

> App.js , Header.js 만 이용하여 router 기능 이용하기



- **App.js**

```react
import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Page1 from './routers/Page1';
import Page2 from './routers/Page2';
import Page3 from './routers/Page3';
import Header from './components/Header'


const App = () => {
  return (
    <Router>
      <Header />
      <Route path="/my_note1" component={Page1} />
      <Route path="/my_note2" component={Page2} />
      <Route path="/my_note3" component={Page3} />
    </Router>
  );
};

export default App;

```



- **Header.js**

```react
import React from 'react';
import {Link} from 'react-router-dom';

const Header = () => {
    return (
        <div>
            <ul>
                <li><Link to="my_note1">Page1</Link></li>
                <li><Link to="my_note2">Page2</Link></li>
                <li><Link to="my_note3">Page3</Link></li>
            </ul>
            
        </div>
    );
};

export default Header;
```

