import React, { Component } from 'react'

class PhoneItem extends Component {

    // state = {

    //     todo: '',

    // }



    handleRemove = (e) => {
        const { info, onRemove } = this.props;
        console.log(info)

        onRemove(info.id)
    }

    // handleChange = (e) => {
    //     const { todo, value } = e.target;
    //     this.setState({
    //         [todo]: value
    //     })
    // }




    render() {

        const css = {
            border: '1px solid black',
            padding: '8px',
            margin: '5px'
        }



        const { todo, id } = this.props.info;

        return (
            <div style={css}>
                <div><b>{todo}</b></div>
                <button onClick={this.handleRemove}>삭제</button>
            </div>

        )

    }
}

export default PhoneItem;