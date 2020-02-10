import React, { Component } from "react";
import './style.css'

class PhoneForm extends Component {

    state = {
        todo: '',
    };

    handleChange = (e) => {
        this.setState ({
            [e.target.name]: e.target.value

        })
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.onCreate(this.state);

        
        this.setState ({
            todo: '',

    })

    }



    render() {

        return (
            <form onSubmit={this.handleSubmit} className="Header">
                <h1 className="item">To Do List</h1>

                <div className='search-bar'>
                <input className="input"
                placeholder='오늘 할 일을 입력하세요.'
                value={this.state.todo}
                onChange={this.handleChange}
                name='todo'/>
                <button type='submit' className="button">추가</button>
                </div>
            </form>
 
        );
    }

}

export default PhoneForm;