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