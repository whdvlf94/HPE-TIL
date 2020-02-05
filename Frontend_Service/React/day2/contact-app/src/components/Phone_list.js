import React, {Component} from 'react';
import PhoneItem from './Phone_item';

class PhoneList extends Component {

    render() {
        const {data, onRemove, onEdit} = this.props;

        const list = data.map(value =>
            <PhoneItem 
            key={value.id} 
            info={value}
            onRemove = {onRemove}
            onEdit = {onEdit}
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