import React, { Component } from 'react';

const ErrorObject = () => {
    throw (new Error('에러 발생'));
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

export default Error1;