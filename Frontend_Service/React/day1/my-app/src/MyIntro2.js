import React from 'react';

const MyIntro2 = function ({card}) {
return (
    
    <div>
    안녕하세요, 제 이름은 <b>{card.name} 입니다.
    제 이메일은 {card.email} 입니다.
    제 연락처는{card.phone}</b> 입니다.

    </div>
)

}

export default MyIntro2;