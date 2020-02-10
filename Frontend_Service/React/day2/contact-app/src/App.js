import React, { Component } from 'react';
import PhoneForm from './components/Todo_form';
import PhoneList from './components/Todo_list';
import './components/style.css'


class App extends Component {
  id = 1;
  state = {
    contacts: [
      {
        id: 0,
        todo: '리액트 소개',
      }
    ],
    keyword: ''
  }

  handleChange = (e) => {
    this.setState({
      keyword: e.target.value
    })
  }

  handleCreate = (data) => {
    const { contacts } = this.state
    this.setState({
      contacts: contacts.concat({ id: this.id++, ...data })
    })
  }

  handleRemove = (del_id) => {
    console.log('App handleRmove =' + del_id);
    const { contacts } = this.state;
    this.setState({
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
    const { contacts, keyword } = this.state;

    const filteredContacts = contacts.filter(v => v.todo.indexOf(keyword) !== -1);


    console.log(this.state.contacts)
    return (
      <div className="App-header">
        <PhoneForm onCreate={this.handleCreate} />
        <p className='title'>
          <input placeholder="작업 목록 검색"
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
