# YouTube 만들어 보기





### 복습)
---

※ Component = view

```js
<form>
    <input placeholder="이름"
    value={this.state.name}
    // JSX 문법
    onChange={this.handleChange}
    // 글자가 입력되면 실행되는 이벤트. ex) test -> 이벤트 4번 실행
    />
</form>
```





- **배열**

---

```js
let Array = [1,2,3,4,5]

Q) 배열에서 3만 제거하는 방법

1) slice
Array.slice(0,2).concat(Array.slice(3,5));

2) 전개 연산자
var array1 = Array.slice(0,2)
var array2 = Array.slice(3,5)
var array3 = [...array1, ...array2];

3) filter
Array.filter(n => n !==3);

```

---





## 2. My YouTube

```
[google serach api download]
※ [google api](https://console.developers.google.com/) 에서 key 발급(youtube v3)

cmd 창에서 npm install --save youtube-api-search 실행(YouTube 폴더 위치)

※ 접속 : localhost:8080
```



![Untitled Diagram](https://user-images.githubusercontent.com/58682321/73912813-d6bcad00-48f8-11ea-8b91-5523beb8691b.png)

- **index.js(App)**

---

```javascript
import React, { Component } from 'react';
import ReactDom from 'react-dom';
import YTSearch from 'youtube-api-search';
import SearchBar from './Components/search_bar';
import VideoList from './Components/video_list';
import VideoDetail from './Components/video_detail';

const API_KEY = '인증키';

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





```

※ 부모 Component

- 





- **search_bar.js(SearchBar)**

```js
import React, { Component } from 'react';

class SearchBar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            term:''
        }
        }

    onInputChange = (event) => {
        this.setState ({
            term: event.target.value
        })
        // console.log(event.target.value)
        // [변수명].target.value : 입력한 값이 개발자 도구에 표시되도록 하는 명령어
        }   

    render() {
        return (
     
                <div className="search-bar">
                <input className="input" placeholder='검색' onChange={this.onInputChange} />
                {/* <div>Value of the input:{this.state.term}</div> */}
                <input className="button" type="submit" value="Search"/>
                </div>
   


            //  <input onChange={event => console.log(event.target.value)} />
        )

    }
}
        
        
export default SearchBar;   
    
```





- **video_list.js(VideoList)**

```js
import React from 'react';
import VideoListItem from './video_list_item';
import { ProgressPlugin } from 'webpack';

const VideoList = (props) => {
    // {video} : 부모 component에서 설정한 이름을 비구조 할당({})으로 가져오면 props를 쓰지 않고 사용할 수 있다. 

    const videoItems = props.videos.map(v => {
        // map을 통해 하나의 배열을 5개의 배열로 나눈다.
        return (
            <VideoListItem 
            key={v.etag}
            onVideoSelect = {props.onVideoSelect}
            video={v}/>
            // VideoListItem(자식) 컴포넌트로 값들이 넘어간다.
            )


    });

    return (
        <ul className="col-md-4 list-group">
           {videoItems}
        </ul>
    )
}

export default VideoList;

```





- **video_list_item.js(VideoListItem)**

```js
import React from 'react';

const VideoListItem = ({ video, onVideoSelect }) => {
    // {video, onVideoSelect} : 부모 component에서 설정한 이름을 비구조 할당({})으로 가져오면 props를 쓰지 않고 사용할 수 있다. 
    const imageUrl = video.snippet.thumbnails.default.url;
    // console.log(imageUrl)

    return (
        <li onClick={() => onVideoSelect(video)} className='list-group-item'>

            <div className='video-list media'>
                <div className='media-left'>
                    <img className='video-item img' src={imageUrl} />
                </div>
                <div className='media-body'>
                    <div className='media-heading'>
                        {video.snippet.title}
                    </div>
                </div>
            </div>

            {video.snippet.title}
        </li>

    )
};

export default VideoListItem;
```



- **style.css**

```css
.search-bar {
    margin: 1px;
    text-align: center;
}
.search-bar .input {
    margin-top: 10px;
    width: 75%    
}
.search-bar .button {
    margin-bottom: 15px;
    width: 9%;
    text-align: center;   
}
.video-item img {
    max-width: 64px;
}
.video-detail .details {
    margin-top:10px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    text-align: center;
    
}
.list-group-item {
    cursor: pointer;
}
.list-group-item:hover {
    background-color: #eee;
}



```

