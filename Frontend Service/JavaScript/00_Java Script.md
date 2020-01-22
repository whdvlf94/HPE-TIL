# JavaScript

> 웹 브라우저에서 많이 사용하는 프로그래밍 언어



- **Client Side Server**

  : 웹 문서의 내용을 동적으로 바꾸거나 마우스 클릭 같은 이벤트 처리



- ECMAScript : 많이 쓰이는 javascript 종류, JavaScript(ES6)로 표기 되어 있음

- 웹 페이지 화면 표시 : HTML5 , 구성 문법 : JavaScript


### 0. 예제
> 해당 내용은 개발자 도구 Elements, Console, 페이지 소스 보기(ctrl + U)에서 확인 가능 
---

``` html
<!DOCTYPE html>
<html>
    <head>
        <script>
            var name = "윤종필";
            alert("Hello" + name); // () : 함수 or 산술 연산
            // 문자열 '' or "" 가능
            // + : 1. 덧셈 , 2. 문자열 결합
            // overloading : 하나의 기호가 여러 개의 기능을 갖는 것

            console.log("Hello world!")
            // 중간 결과, 디버깅 용도로 쓰이는 명령어
            // 개발자 도구> console 창에서 확인
        </script>

        
    </head>  
    <body>
    

    </body>


</html>
```

#### 개발자 도구: F12

- 개발자 도구 창> Application 
    - Storage에 cash 정보들이 보관되어 있다.


### 1. 기본 용어
---

- **주석 처리**
```html
[script 주석처리]
<script>

 // 주석 처리
 or
 /* 여러 행 주석 처리 가능 */

</script>    


[body 주석처리]
<body>
        <h1> 제목입니다.</h1>
        <!--  <h1>제목입니다 </h1> -->
    

    </body>
```    



### 2. 기본 문법
---


- **숫자&불&비교 연산자**

```html

<script>

console.log(10 % 7); // 나머지 출력
console.log(10 / 7); // 몫 출력
console.log('가방'>'하마');
// false , 가나다 순으로 값 설정



var a=10; // 숫자형 데이터
var b='10'; // 문자형 데이터
console.log(a, b);
console.log(a == b); // data type은 반영하지 않기 때문에 true
console.log(a === b); // data type이 다르므로 false

// alert() : 사용자에게 경고 메세지
// confirm() : 2개의 버튼 제공 확인, 취소
// prompt() : 사용자에게 값을 입력 받음


var userVal = prompt("값을 입력하세요.");
console.log("당신이 입력한 값은=" + userVal);
// 짝수 or 홀수인지 판단
if (userVal%2 == 0) {
    console.log("짝수");
    
} else {
    console.log("홀수");
}
</script>
       

```



=== : 값과 data type 도 같다(다를 경우 !==)

※ 따라서, === 연산자 사용 할 것



- **변수**

``` html
<script>

var userVal = prompt("값을 입력하세요.");
console.log("당신이 입력한 값은=" + userVal);

if (userVal%2 == 0) {
    console.log("짝수");
    
} else {
    console.log("홀수");
}

userVal = "test1";
console.log("당신이 입력한 값은" + userVal);
var userVal = "test2";
console.log("당신이 입력한 값은" + userVal);
// 오류가 생성되지 않고 userVal 값이 계속 바뀐다.
// 사용 권장 X

</script>
```

- 변수 생성
    - var(일반 변수) - 기존 값을 계속 바꿔서 오류가 나지 않는다. 
    - let(block 내에서 사용되는 local 변수) - **사용 권장**
    - const(상수 값)


※ let은 최초에 설정한 값으로 컴파일이 진행되며, const와 달리 변수에 재할당이 가능하다. 

``` javascript
[let]

let name = 'abc'
console.log(name) // abc

let name = '123'
console.log(name) // error

name = 'edit'
console.log(name) // edit


[const]

let name = 'abc'
console.log(name) // abc

let name = '123'
console.log(name) // error

name = 'edit'
console.log(name) // error
```


- **복합 대입 연산자의 활용**
```javascript
window.onload = function () {
    // 변수 선언, 문자열로 지정
    var list = '';
    // 연산자를 사용합니다.
    list += '<ul>';
    list += '       <li>Hello</li>';
    list += '       <li>JavaScript..!</li>';
    list += '</ul>';
    // 문서 출력
    document.body.innerHTML = list;
                  
            
};
```