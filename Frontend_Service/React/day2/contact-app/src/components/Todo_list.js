import React, {Component} from 'react';
import PhoneItem from './Todo_item';

class PhoneList extends Component {

    render() {
        const {data, onRemove} = this.props;

        const list = data.map(value =>
            <PhoneItem 
            key={value.id} 
            info={value}
            onRemove = {onRemove}
            />
            );
 
        return(
            <div>
                {list}
                
            </div>
        )
    }
}

export default PhoneList;