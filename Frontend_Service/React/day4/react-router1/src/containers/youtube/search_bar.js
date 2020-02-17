import React, { Component } from 'react';

class SearchBar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            term: ''
        }
    }

    onInputChange = (e) => {
        this.setState({
            term: e.target.value
        });

        this.props.onSearchTermChange(e.target.value);
    }


    render() {
        return (
            <div className="search-bar">
                <input className="search-bar input"
                    onChange={this.onInputChange} />
            </div>
        );
    }
}
export default SearchBar;