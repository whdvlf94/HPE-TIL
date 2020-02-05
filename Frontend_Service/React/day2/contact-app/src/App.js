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
    ]
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

  handleEdit = (sel_id, edit_data) => {
    const {contacts} = this.state;
    this.setState ({
      contacts: contacts.map(
        item => item.id === sel_id
        ? {...item, ...edit_data }
        : item
      )
    })

  }
    
  
  render() {
    const {contacts} = this.state;

    
    console.log(this.state.contacts)
    return (
      <div>
        <PhoneForm onCreate={this.handleCreate}/>
        <PhoneList 
        data={this.state.contacts} 
        onRemove={this.handleRemove} 
        onEdit={this.handleEdit}/>
      </div>
    );
  }
}

export default App;
