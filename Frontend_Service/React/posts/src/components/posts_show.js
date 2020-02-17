import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { fetchPost, deletePost } from '../actions';

class PostsShow extends Component {

  componentDidMount() {
    // const {id} = this.props.match.params.id 
    // console.log(this.props)
    // if (!this.props.post) {

    const { id } = this.props.match.params;
    console.log(this.props.match)
    this.props.fetchPost(id);
  }

  onDeleteClick() {
    const { id } = this.props.match.params;
    this.props.deletePost(id, () => {

      this.props.history.push('/');
    })

  }

  // localhost:8800/ -> 목록보기
  // 아이템 선택(클릭)
  // localhost:8800/posts/12 -> 상세보기
  render() {
    const { post } = this.props;

    if (!post) {
      return <div>loading...</div>
    }
    return (
      <div>
        <Link to='/'>back to index</Link>
        <button className="btn btn-danger pull-xs-right"
          onClick={this.onDeleteClick.bind(this)}>delete post</button>

        <h3>{post.title}</h3>
        <h6>category: {post.category}</h6>
        <p>{post.contents}</p>

      </div>
    );
  }
}
function mapstateToProps(state, ownProps) {
  console.log(state.posts)
  return { post: state.posts[ownProps.match.params.id] };

}

export default connect(mapstateToProps, { fetchPost, deletePost })(PostsShow);