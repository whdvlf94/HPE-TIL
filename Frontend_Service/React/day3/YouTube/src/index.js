import React, { Component } from 'react';
import ReactDom from 'react-dom';
import YTSearch from 'youtube-api-search';
import SearchBar from './Components/search_bar';
import VideoList from './Components/video_list';
import VideoDetail from './Components/video_detail';

const API_KEY = 'AIzaSyAGBGeWwYqkg9d21m9cmwFubxoOjTVDNGI';

class App extends Component {
  constructor(props) {
    super(props); // 부모의 생성자 함수를 호출

    this.state = {
      videos: [],
      selectedVideo: null
    }

    YTSearch({ key: API_KEY, term: "surfboards"}, (data) => {
      this.setState({
        videos: data,
        selectedVideo: data[0]
      });
    });

  }
      handleSelect = (selectedVideo) => {
        console.log('selectedVideo=' + selectedVideo)
        this.setState ({
          selectedVideo: selectedVideo
          // 선택한 video가 video detail 에 나타나게 하는 함수
        })
      }


  render() {
    return (
      <div>
        <SearchBar />
        <VideoDetail 
        video={this.state.selectedVideo}/>
        <VideoList 
        onVideoSelect={this.handleSelect}

        // handleSelect 함수에서 this.setState ~ 를 지우고 아래와 같은 방법으로도
        // videoDetail를 설정할 수 있다.
        // onVideoSelect = {selectedVideo => this.setState ({selectedVideo})}
        videos={this.state.videos}
        />
      </div>
      // videoDetail은 가장 상단에 있는 video가 출력되어야 하므로 {this.state.vidoes[0]} 로 설정한다.
    )
  }
}
ReactDom.render(<App />, document.querySelector('.container'));
// document.[조건]('#id')
// document.[조건]('.className')




