import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { fetchBlog } from '../../actions/blog';



class BlogList extends Component {

  constructor(props) {
    super(props);

    this.props.fetchBlog();

  }

  renderBlog(data) {

    console.log(data)
    // console.log(typeof Data)
    // let id = Data.blog.id


    // const category = Data.map(blog => blog.category)
    // const title = Data.map(blog => blog.title)
    // const author = Data.map(blog => blog.author)
    // const contents = Data.map(blog => blog.contents)


    return (
      <tr key={data.id}>
        <th>{data.category}</th>
        <th>{data.title}</th>
        <th>{data.author}</th>
        <th>{data.contents}</th>

      </tr>

    )
  }


  render() {
    if (this.props.blogs === []) {
      return (
        <div>loading...</div>
      )
    }

    return (
      <table className="table">
        <thead>
          <tr>
            <th>category</th>
            <th>title</th>
            <th>author</th>
            <th>contents</th>

          </tr>
        </thead>
        <tbody>
          {this.props.blogs.map(this.renderBlog)}

        </tbody>
      </table>
    );
  }
}

// mapSrtateToProps funciton
function mapStateToProps(state) {
  return { blogs: state.blogs };
}

// mapDispatchToProps function
function mapDispatchToProps(dispatch) {
  return bindActionCreators({ fetchBlog }, dispatch);
}

// connect mapping
export default connect(mapStateToProps, mapDispatchToProps)(BlogList);


// // connect mapping
// export default connect(null,mapDispatchToProps)(BlogList);