import React, { Component } from 'react';

class MyIntro extends Component {
    render() {
        const css = {
            color: 'black',
            fontSize: '35px'
        
        };

        return (

            <div style={css}>
            안녕하세요, 제 이름은 <b>{this.props.card.name} 입니다.
            제 이메일은 {this.props.card.email} 입니다.
            제 연락처는{this.props.card.phone}</b> 입니다.
     
            </div>
        )
    }
}

export default MyIntro;
