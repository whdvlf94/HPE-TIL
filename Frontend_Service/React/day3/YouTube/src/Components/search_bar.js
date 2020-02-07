import React, { Component } from 'react';

class SearchBar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            term: ''
        }
    }

    onInputChange = (event) => {
        this.setState({
            term: event.target.value
        });
        // [변수명].target.value : 입력한 값이 개발자 도구에 표시되도록 하는 명령어

        this.props.onSearchTermChange(event.target.value);
    }


    render() {
        return (

            <div className='search-bar'>
                <input className="input" placeholder='검색' onChange={this.onInputChange} />
                <button className="button" type="submit">Search</button>
            </div>

            //  <input onChange={event => console.log(event.target.value)} />
        )

    }
}


export default SearchBar;
