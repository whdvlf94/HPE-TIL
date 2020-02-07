import React, { Component } from "react";

class PhoneForm extends Component {

    state = {
        name: '',
        phone: ''
    };

    handleChange = (e) => {
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
                value={this.state.name}
                onChange={this.handleChange}
                name='name'/>
                <input 
                placeholder='연락처를 입력하세요'
                value={this.state.phone}
                onChange={this.handleChange}
                name='phone'/>
                <button type='submit'>등록</button>
            </form>
 
        );
    }

}

export default PhoneForm;