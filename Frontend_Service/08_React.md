# Contact-app



### 이벤트 생성 과정

```
1. 이벤트 주체를 결정
2. 이벤트 종류를 결정
3. 이벤트 핸들러를 구현
4. 이벤트 주체 ↔ 핸들러 연결
```







### 1. input을 이용한 예제

---

- **App.js**
```js
import React, { Component } from 'react';
import PhoneForm from './components/Phone_form';


class App extends Component {

  render() {

    return (
      <form>
        <PhoneForm />
      </form>
    );
  }
}

export default App;
```

- **Phone-form.js**
```js
import React, { Component } from "react";

class PhoneForm extends Component {

    state = {
        name: '',
        phone: ''
    };

    handleChange = (e) => {
        console.log(e.target.value);
        this.setState ({
            [e.target.name]: e.target.value
            // name,phone을 유동적으로 할당하는 방법

        })
    }

    render() {

        return (
            <form>
                <input 
                placeholder='이름을 입력하세요'
                onChange={this.handleChange}
                name='name'/>
            
                <input 
                placeholder='연락처를 입력하세요'
                onChange={this.handleChange}
                name='phone'/>
                <div>
                   {this.state.name}/{this.state.phone}
                </div>
            </form>
 
        );
    }

}

export default PhoneForm;
```

**※ 모든 Class 이름은 대문자로 시작해야한다.**





### 2. 새로운 배열 추가

---

- **App.js**
```js
import React, { Component } from 'react';
import PhoneForm from './components/Phone_form';


class App extends Component {
  id =1;
  state = {
    contacts: [
      {
        id: 0,
        name: '관리자',
        phone: '010-0000-0000'
      }
    ]
  }
  handleCreate = (data) => {
    const {contacts} = this.state
    // 비구조 할당
    this.setState ({
      contacts: contacts.concat({id: this.id++, ...data})
    })

    
    
  }
  render() {
    const {contacts} = this.state;
    console.log(this.state)
    console.log(this.state.contacts)
    console.log(contacts)
    // 비구조 할당 이해 해보기
    
    return (
      <div>
        <PhoneForm onCreate={this.handleCreate}/>
        {JSON.stringify(contacts)}
      </div>
    );
  }
}

export default App;
```


**- 비구조 할당**
![비구조할당](https://user-images.githubusercontent.com/58682321/73806806-e3b0a200-480d-11ea-8461-4c93c00e3b07.PNG)




- **Phone_form.js**
```js
import React, { Component } from "react";

class PhoneForm extends Component {

    state = {
        name: '',
        phone: ''
    };

    handleChange = (e) => {
        console.log(e.target.value);
        this.setState ({
            [e.target.name]: e.target.value

        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
        // preventDefault 를 설정하지 않으면, 출력 값이 사라진다.
        this.props.onCreate(this.state);

        
        this.setState ({
            name: '',
            phone: ''

    })
    }



    render() {

        return (
            <form onSubmit={this.handleSubmit}>
                <input 
                placeholder='이름을 입력하세요'
                onChange={this.handleChange}
                name='name'/>
            
                <input 
                placeholder='연락처를 입력하세요'
                onChange={this.handleChange}
                name='phone'/>
                <button type='submit'>등록</button>
            </form>
 
        );
    }

}

export default PhoneForm;
```




- **Phone_list.js**
```js
import React, {Component} from 'react';

class PhoneList extends Component {

    render() {
        const {data} = this.props;
        // 비구조 할당
        // <div>관리자/111</div>
        // <div>관리자/222</div>
        //  loop -> data의 값 출력
        
        const list = data.map(value =>
           <div key={value.id}>{value.name}/{value.phone}</div>);
            // 분류할 수 있는 key 값을 지정해 주는 것이 좋다.

        // map 함수의 기능은 아래의 for 문장과 동일하다.
        // 배열의 각 속성 값을 꺼내서 하나씩 호출
        // for(let i=0; i<data.length; i++) {
        //     console.log(data[i])
        // }
        return(
            <div>
                {list}

            </div>
        )
    }
}

export default PhoneList;
```

- **App.js**
```js
    return (
      <div>
        <PhoneForm onCreate={this.handleCreate}/>
        <PhoneList data={this.state.contacts}/>
        // 추가
      </div>
    );
```

![phone_list](https://user-images.githubusercontent.com/58682321/73806807-e4e1cf00-480d-11ea-9daa-b23e29bd20a0.PNG)

### 3. 삭제 이벤트 작업

---

```
1. 이벤트 주체를 결정
<삭제> 버튼
2. 이벤트 종류를 결정
<클릭 이벤트>
3. 이벤트 핸들러를 구현
이벤트가 작동되면 어떤 동작을 할지 결정하는 함수 구현
4. 이벤트 주제 <-> 핸들러 연결
함수 <-> 버튼
```



**1) App.js - handleRemove 작업**

```js
  handleRemove = (del_id) => {
    console.log('App handleRmove =' + del_id);
    const {contacts} = this.state;
    this.setState ({
      contacts: contacts.filter(
        list => list.id !== del_id
        )
      })
    console.log(this.state.contacts)
  }

  
      return (
      <div>
        <PhoneForm onCreate={this.handleCreate}/>
        <PhoneList data={this.state.contacts} onRemove={this.handleRemove}/>
      </div>
    );
```



**2) Phone_list.js - PhoneItem 작업**

```js
const list = data.map(value =>
    <PhoneItem 
     key={value.id} 
     info={value}
     onRemove = {onRemove}
	 //onRemove 추가
     />
);
```

**※ Phone_list.js 는 Phone_Item의 부모 Component 이다.**





**3) Phone_Item.js - button , handleRemove 작업**

```js
    handleRemove = (e) => {
        const { info, onRemove } = this.props;

        onRemove(info.id)
    }
    // handleRemove 이벤트 추가
    
    
     return (
     <div style={css}>
     <div><b>{name}</b></div>
     <div><b>{phone}></b></div>
     <button onClick={this.handleRemove}>삭제</button>
     // onClick 이벤트 추가 
	 </div>
        );
    
    
    
```





### 4. 수정 이벤트 작업

---

```
1. 이벤트 주체를 결정
<수정> 버튼
2. 이벤트 종류를 결정
<클릭 이벤트>
3. 이벤트 핸들러를 구현
이벤트가 작동되면 어떤 동작을 할지 결정하는 함수 구현
4. 이벤트 주제 <-> 핸들러 연결
함수 <-> 버튼
```



**1) App.js - handleEdit 작업**

```js
  handleEdit = (sel_id, edit_data) => {
    const {contacts} = this.state;
    this.setState ({
      contacts: contacts.map(
        item => item.id === sel_id
        ? {...item, ...edit_data }
        : item
        // 삼항연산자
        // sel_id(수정 버튼 클릭)은 기존의 item 값들은 유지하고(...item),	
        // 수정한 데이터만 추가(...edit_data)
        // 수정 하지 않은 경우에는 기존의 item 값 그대로 사용  
      )
    })

  }
  
-------------------------------------------------  
  
      return (
      <div>
        <PhoneForm onCreate={this.handleCreate}/>
        <PhoneList 
        data={this.state.contacts} 
        onRemove={this.handleRemove} 
#       onEdit={this.handleEdit}/>
      </div>
    );
```



**2) Phone_list.js 작업**

```js
    render() {
        const {data, onRemove, onEdit} = this.props;

        const list = data.map(value =>
            <PhoneItem 
            key={value.id} 
            info={value}
            onRemove = {onRemove}
 #           onEdit = {onEdit}
            />
            );
```



**3) Phone_item.js 작업**

```js
import React, { Component } from 'react'

class PhoneItem extends Component {

    state = {
        editable: false,
        name: '',
        phone: ''
    }

    componentDidUpdate(preProps, prevState) {
        const { info, onEdit } = this.props;
        console.log(info.name + "/" + info.phone);
        console.log(prevState.editable + '/' + this.state.editable);
        if (!prevState.editable && this.state.editable) {
            this.setState({
                name: info.name,
                phone: info.phone
            })
        }
        // update
        if (prevState.editable && !this.state.editable) {
            onEdit(info.id, {
                name: this.state.name,
                phone: this.state.phone
            })
        }
    }

    handleEdit = (e) => {

        const { editable } = this.state;
        this.setState({
            editable: !editable
            // 현재 false로 설정되어 있는 editable. 버튼이 한번 클릭이 되면 앞에 !가 있기때문에 editable의 값은 true(수정모드)가 된다.
            // 다시 한번 클릭하게 되면 false(일반 모드)로 다시 변경된다.
        })

    }

    handleRemove = (e) => {
        const { info, onRemove } = this.props;

        onRemove(info.id)
    }

    handleChange = (e) => {
        const { name, value } = e.target; // name= name or phone
        this.setState({
            [name]: value
            // [name] : []안에는 name or phone이 들어가고, 그에 맞는 value 값이 들어가게 된다.
            // setState -> [name]: value 과정을 통해서 수정한 작업이 변경되어 저장될 수 있다.
        })
    }




    render() {

        const css = {
            border: '1px solid black',
            padding: '8px',
            margin: '5px'
        }
        // const info =this.props.info;
        // console.log(info.id);
        // console.log(info.name);
        // console.log(info.phone);
        // 아래와 동일한 방식
        // 위의 경우 info(변수 명)를 붙여서 써야한다.

        const { editable } = this.state

        if (editable) {
            console.log('수정모드')
            return (
                <div style={css} >
                    <div>
                        <input value={this.state.name}
                            name='name'
                            placeholder="이름을 입력하세요."
                            onChange={this.handleChange} />
                    </div>
                    <div>
                        <input value={this.state.phone}
                            name='phone'
                            placeholder="연락처를 입력하세요."
                            onChange={this.handleChange} />
                    </div>
                    <button onClick={this.handleEdit}>적용</button>
                    <button onClick={this.handleRemove}>삭제</button>
                </div >

            )
        } else {
            console.log('일반모드')

        }

        const { name, phone, id } = this.props.info;

        return (
            <div style={css}>
                <div><b>{name}</b></div>
                <div><b>{phone}</b></div>
                <button onClick={this.handleEdit}>수정</button>
                <button onClick={this.handleRemove}>삭제</button>
            </div>

        )

    }
}

export default PhoneItem;
```

