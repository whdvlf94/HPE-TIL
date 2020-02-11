import React, { Component } from 'react';
import {connect} from 'react-redux';
// component와 redux의 connect를 해주는 함수
import {bindActionCreators} from 'redux';
import {selectBook} from '../Actions/index';

class BookList extends Component {
    renderList() {
        return this.props.books.map(book => {

            return (
                <li key={book.title} onClick={() => this.props.selectBook(book)}>{book.title}</li> // Booklist 배열 -> 반복처리
            )
        })
    }
    render() {
        return (
            <ul>
                {this.renderList()}
            </ul>

        );
    }
}

function mapStateToProps(state) {
    return {
        books: state.books
    }
}

function mapDispatchToProps (dispatch) {
    return bindActionCreators({selectBook: selectBook}, dispatch);
}

export default connect(mapStateToProps, mapDispatchToProps)(BookList);