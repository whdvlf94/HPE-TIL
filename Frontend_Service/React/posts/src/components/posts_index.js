import _ from 'lodash';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { fetchPosts } from '../actions/index';

import { Link } from 'react-router-dom';
import { bindActionCreators } from 'redux';

class PostsIndex extends Component {

  // define a lifecycle function for retrieve data  
  componentDidMount() {
    console.log('componentDidMount Called');
    this.props.fetchPosts();
    // fetchPosts가 실행될 때 마다 호출
  }

  renderPosts() {
    // list -> 개별 <li>  태그로 출력
    return _.map(this.props.posts, post => {
      return (

        <li className='list-group-item' key={post.id}>
          <Link to={`/blogs/${post.id}`}>{'[' + post.id + '] '}{post.title}</Link>
        </li>
      )
    })


  }


  render() {
    return (
      <div>
        <div className="text-xs-right">
          <Link className="btn btn-primary"
            to="/blogs/new">Add a Post</Link>
        </div>

        <h3>Posts</h3>
        <ul className="list-group">
          {this.renderPosts()}
        </ul>
      </div>
    );
  }
}

// 상태 관리
function mapStateToProps(state) {
  console.log(state)
  return { posts: state.posts }
}

// 액션 관리
function mapDispatchToProps(dispatch) {
  return bindActionCreators({ fetchPosts }, (dispatch))

}

// connect(상태관리 함수, 액션관리 함수)(연결 컴포넌트)
export default connect(mapStateToProps, mapDispatchToProps)(PostsIndex);

// ES6 -> {name:name} => {name}
// [액션관리 함수] 위의 방식 대신 아래와 같이 사용해도 된다.
// export default connect(mapStateToProps, {fetchPosts})(PostsIndex);

