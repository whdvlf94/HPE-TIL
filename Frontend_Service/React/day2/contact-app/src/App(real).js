import React, { Component } from 'react';
import PhoneForm from './components/Phone_form';
import PhoneList from './components/Phone_list';


class App extends Component {
  id =1;
  state = {
    contacts: [
      {
        id: 0,
        name: '관리자',
        phone: '010-0000-0000'
      }
    ],
    keyword: ''
  }
  
  handleChange = (e) => {
    this.setState ({
      keyword: e.target.value
    })
  }

  handleCreate = (data) => {
    const {contacts} = this.state
    // 비구조 할당
    this.setState ({
      contacts: contacts.concat({id: this.id++, ...data})
    })
  }

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


    
  shouldComponentUpdate(nextProps, nextState) {
    console.log(nextProps);
    return true;
  }

  render() {
    const {contacts, keyword} = this.state;
    // 전체 목록 -> contacts
    // 검색할 키워드 -> keyword
    // contacts에서 keyword인 데이터만 검색해서 전달(list)
    // contacts.map or contacts.filter(여기서는 필터사용)
    // contacts.filter(v => (조건) ? true : false)
    const filteredContacts = contacts.filter(v => v.name.indexOf(keyword) !== -1);

    
    console.log(this.state.contacts)
    return (
      <div className="App-header">
        <PhoneForm onCreate={this.handleCreate}/>
        <p>
          <input placeholder="검색 할 이름을 입력하세요."
          onChange={this.handleChange}
          value={this.state.keyword}
          />
        </p>
        <PhoneList 
        data={filteredContacts} 
        onRemove={this.handleRemove} 
        />
      </div>
    );
  }
}

export default App;
