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
