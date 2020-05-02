# TO-DO List APP





## 흐름

- **components 디렉토리 생성** → `TodoListTemplate.js` →  `App.js` →`Form.js` → `TodoItemList.js` → `TodoItem.js`





- #### TodoListTemplate.js

  ```js
  import React from 'react';
  import './TodoListTemplate.css';
  
  // function TodoListTemplate(props) {
  const TodoListTemplate = ({ form, children }) => {
  
      return (
          <div className="todo-list-template">
              <div className="title">
                  TO DO LIST
              </div>
              <div className="form-wrapper">
                  {form}
              </div>
              <div className="todos-wrapper">
                  {children}
              </div>
          </div>
      );
  }
  
  export default TodoListTemplate;
  ```

  - `TodoListTemplate.js`는 **함수형 컴포넌트**로 선언한다. 이때,  **{form}** 은 `App.js`에서 **\<TodoListTemplate form>**으로 **{children}** 은 내부에 선언

    

- #### App.js

  ```js
  import React, { Component } from 'react';
  import TodoListTemplate from './Components/TodoListTemplate';
  import Form from './Components/Form';
  import TodoItemList from './Components/TodoItemList';
  
  const initialTodos = new Array(500).fill(0).map(
    (item, idx) => ({ id: idx, text: `일정 ${idx}`, checked: true })
    );
    
  class App extends Component {
    id = 3;
    //상태변수
    state = {
      todo: '',
      todos: initialTodos
    //           [{ id: 0, text: "todo1", checked: false },
    //           { id: 1, text: "todo2", checked: true },
    //           { id: 2, text: "todo3", checked: false },
    //   ]
    }
  
    //Event Handler 함수 정의
    handleChange = (e) => {
      this.setState({
        todo:e.target.value
      })
    };
  
    handleCreate = () => {
      const {todo, todos} = this.state;
      this.setState({
        todos:todos.concat({id:this.id++, text:todo, checked:false}),
        todo:''
  
      })
    };
  
    handleKeyPress = (e) => {
      if(e.key === 'Enter'){
        this.handleCreate();
      }
    };
  
    handleRemove = (id) => {
      const {todos} = this.state;
      this.setState({
        todos:todos.filter(todo => todo.id !== id)
      })
    }
  
    handleToggle = (id) => {
      const {todos} = this.state;
      
      const index = todos.findIndex(todo => todo.id ===id)
      const selected = todos[index]
      const copyTodos = [...todos]
  
      copyTodos[index] = {
        ...selected,
        checked:!selected.checked
      }
      this.setState({
        todos:copyTodos
      })
    }
  
    render() {
      const {todo, todos} = this.state;
      const {handleChange, handleCreate, handleKeyPress, handleRemove, handleToggle} = this;
      return (
        <div>
          <TodoListTemplate form={<Form todo={todo} myCreate={handleCreate} myChange={handleChange} myKeyPress={handleKeyPress} />}>
            <TodoItemList todos={todos} myRemove={handleRemove} myToggle={handleToggle} />
          </TodoListTemplate>
  
        </div>
      );
    }
  }
  
  export default App;
  ```

  - **form** : Form 영역
  - **children** : List 영역



- #### Form.js

  ```js
  import React, { Component } from 'react';
  import './Form.css';
  
  class Form extends Component {
  
      render() {
          const {todo, myChange, myKeyPress, myCreate} = this.props;
          return (
              <div className="form">
                  <input value={todo} onChange={myChange} onKeyPress={myKeyPress}/>
                  <div className="create-button" onClick={myCreate}>
                      추가
                  </div>
              </div>
          );
      }
  }
  
  export default Form;
  ```
  - `App.js` 에서 **TodoListTemplate**에  **\<Form todo="todo">** 를 입력하면, `Form,js`에서 이를 **{todo}**로 상속받는다. `localhost:3000`에 접속하면, **Form** 영역에 todo 라는 텍스트 값이 출력된 것을 확인할 수 있다.



- #### TodoItemList.js

  ```js
  import React, { Component } from 'react';
  import TodoItem from './TodoItem';
  
  class TodoItemList extends Component {
      //life-cycle 메서드 overriding : render() 메서드의 호출을 줄일 수 있다.
      shouldComponentUpdate(nextProps, nextState) {
          return this.props.todos !== nextProps.todos;
          }
  
      render() {
          const { todos, myToggle, myRemove } = this.props;
          const todoList = todos.map(({ id, text, checked }) => (
              <TodoItem id={id} checked={checked} todoText={text} myToggle={myToggle} myRemove={myRemove} key={id}/>
          ));
          return (
              <div>
                  {todoList}    
              </div>
          );
      }
  }
  
  export default TodoItemList;
  ```

  



- #### TodoItem.js

  ```js
  import React, { Component } from 'react';
  import './TodoItem.css'
  
  class TodoItem extends Component {
  
      shouldComponentUpdate(nextProps, nextState) {
          return this.props.checked !== nextProps.checked;
          }
  
      render() {
          const { todoText, checked, id, myToggle, myRemove } = this.props;
          return (
              <div className="todo-item" onClick={() => myToggle(id)}>
                  <div className="remove" onClick={(e) => {
                      // bouble up(event 전파) 방지하기 위해 stopPropagation 선언
                      // 즉, 다른 remove 에서만 해당 이벤트가 작동되게 하기 위함이다.
                      e.stopPropagation();
                      myRemove(id);
  
                  }}>&times; 
                  {/* &times : x 표시 */}
                  </div>
  
                  {/* ${checked} 값이 true 인 경우 list line-through 진행 
                   `을 이용해서 작성한다.abs]*/}
                  <div className={`.todo-text ${checked && 'checked'}`}>
                      
                      {/* checked 가 true인 경우 아래와 같이 진행  */}
                  {/* <div className={".todo-text && checked"}> */}
                      <div>{todoText}</div>
                  </div>
                  {
                      checked && (<div className="check-mark">✓</div>)
                  }
  
              </div>
          );
      }
  
  }
  
  export default TodoItem;
  ```

  - **&times** : x 표시 css

  - **${checked}** : 해당 값이 true 인 경우 **{todoText}** text 값에 line-through 진행

    - checked 가 true인 경우 :  **<div className={".todo-text && checked"}>** 

      



![todo 완성](https://user-images.githubusercontent.com/58682321/80857246-99638380-8c8b-11ea-953f-b09ca9841c86.PNG)