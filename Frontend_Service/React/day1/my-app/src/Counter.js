import React, { Component } from 'react';


class Counter extends Component {
    state = {
        count: 10,
        info: {
            name: 'React',
            age: '10',

        }
    }

    constructor(props) {
            super(props) 

                this.state.count = this.props.init;
                // 상탯값을 직접 할당하는 것은 constructor 메서드에서만 허용
                // 원래는 this.setState 활용
                console.log('call constrouctor')
        }
        

    componentDidMount() {
            console.log('componenetDidMount')
        }

    shouldComponentUpdate(nextProps, nextState) {
            // 5의 배수라면 리렌더링 하지 않음
            console.log('shouldComponentUpdate')
            if (nextState.count % 5 === 0) return false;
            return true;
        }
    
    componentWillUpdate(nextProps, nextState) {
            console.log('componentWillUpdate')

        }
    componentDidUpdate(prevProps, prevState) {
            console.log('componentDidUpdate');
        }

    handleIncrease = () => {
            this.setState({
                // this.setState 를 통해 state 내에 포함되어 있는 내용을 수정 할 수 있다.
                count: this.state.count + 1
            })

        }    
    handleDecrease = () => {
            this.setState({
                count: this.state.count - 1

            })
        }
    handleChangeInfo = () => {
            this.setState({
                info: {
                    ...this.state.info,
                    name: 'yjp'
                }
                // age: this.state.info.age = '20'
            })
        }
    render() {
            return (
                <div>
                    <h1>Counter</h1>
                    <b>{this.state.count}</b>
                    <button onClick={this.handleIncrease}>+</button>
                    <button onClick={this.handleDecrease}>-</button>
                    <button onClick={this.handleChangeInfo}>Change info name</button>
                    <h2>{this.state.info.name}/{this.state.info.age}</h2>
                </div>
            )
        }

    }

    export default Counter;