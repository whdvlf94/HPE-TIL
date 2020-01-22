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


- **숫자/불/비교 연산자**

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



- **변수 - var/let/const**

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

 





- **let 과 const 변수 설정의 차이점**

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

**※ let은 최초에 설정한 값으로 컴파일이 진행되며, const와 달리 변수에 재할당이 가능하다.**






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



- **결과**

![var list](https://user-images.githubusercontent.com/58682321/72869968-295b6e00-3d2a-11ea-9755-d4bac82bc1f7.PNG)





### 3. 테이블 생성

---

```html
<!DOCTYPE html>
<html>
<script>
// table width, boarder 설정
// name table 생성 : <th>
// table 행 생성 : td
// <th> : 가운데 정렬    
// colspan : 열 병합
// rowspan : 행 병합
</script>
<body>
    <table width="50%" border="1">
        <tr>
            <th>Name</th>
            <th>Phone</th>
            <th>Adrres</th>
            <th colspan="2">Class</th>

        
        
        </tr>
        <tr>

            <td>A</td>
            <td>B</td>
            <td>C</td>
            <td colspan="2">D</td>
        
        </tr>
        <tr>
            <td>E</td>
            <td>F</td>
            <td>G</td>
            <td colspan="2">H</td>
        
        </tr>
        <tr>
            <td>I</td>
            <td>J</td>
            <td colspan="3">K</td>
                
        </tr>
        <tr>
            <td>L</td>
            <td>M</td>
            <td>N</td>
            <td>O</td>
            <td>P</td>
        
        </tr>
    </table>
</body>
</html>
```

- **결과**
![table1](https://user-images.githubusercontent.com/58682321/72870654-15b10700-3d2c-11ea-9146-7728d3e34382.PNG)





### 4. 웹 브라우저 생성
---

```html
<!DOCTYPE html>
<html>
<script>
// input type="text"
// input type="password"
// input type="radio" - name 설정, name 이 동일하면 단일 선택 가능
// input type="checkbox" - 복수 선택 가능
// <select> : select box에서 선택 가능
// input type="file" - windows 파일 선택 가능
// textarea : 내용을 입력할 수 있는 창 생성
// input type="submit" ->  <form action="regist.html" method="GET">
// submit 하면 regist.html으로 요청, 방식은 GET

// input type="reset"
// input type="button"

</script>    
<head>
</head>
    <body>
        <!-- POST/GET
            POST : 사용자가 입력한 내용을 서버에 전달(가입, 저장)
                request(사용 요청)를 서버로 전달

            GET : 사용자가 서버의 resource를 요청(서버가 가지고 있는 데이터 값을 요청)
                웹 브라우저 -> URL 요청
            ex) url 주소 창에 naver.com을 검색하면 네이버 홈 페이지가 나오는 것
            즉, 외부에 노출되도 상관없는 정보들만 GET 방식으로 사용
        -->
        <form action="table.html" method="GET">
            이름: <input type="text" name='name'placeholder="이름을 입력하세요"><br/>
            아이디: <input type="text" name='id' placeholder="아이디를 입력하세요"><br/>
            비밀번호: <input type="password" name='password' placeholder="비밀번호를 입력하세요"><br/>
            남성: <input type="radio" name="gender"><br/>
            여성: <input type="radio" name="gender"><br/>
            사용하는 SNS:
             <input type="checkbox" name="sns"> Facebook
             <input type="checkbox" name="sns"> Twitter
             <input type="checkbox" name="sns"> Instagram
             <input type="checkbox" name="sns"> Google+ <br/>

             연령:
             <select name='age'> 
                 <option value="10">10대</option>
                 <option value="20">20대</option> 
                 <option value="30">30대</option> 

             </select name='age'> <br/>
             사진:
             <input type="file" name='photo'><br/>
             자기 소개:
             <textarea cols="40" rows="5" name='profile'>

             </textarea><br/>
             <input type="submit" value="회원 가입">
             <input type="reset" value="초기화">
             <input type="button" value="임시 저장">


        </form>
    </body>
</html>
```

- **결과**
![form](https://user-images.githubusercontent.com/58682321/72870713-4a24c300-3d2c-11ea-9733-c1fa59442d8c.PNG)







### 5. 조건문
---


- **if-else-if 문**


```html

<!DOCTYPE html>
<html>
<head>
    <script> 
    
    let a = Number(prompt("국어 점수를 입력하세요."));
    let b = Number(prompt("수학 점수를 입력하세요."));
    let c = Number(prompt("영어 점수를 입력하세요."));
    
    let sum = a+b+c;    
    let avg = (a+b+c)/3;
    console.log("avg =", avg);
    
    let grade = ""
    if (avg >= 90)  grade = "A";
    else if (avg >= 80)  grade = "B";
    else if (avg >= 70)  grade = "C";
    else if (avg >= 60)  grade = "D";
    else grade = "F";
    alert("당신의 점수는 " + grade + "입니다.");
    console.log("sum =" + sum)
    
    </script>
</head>
<body>


</body>    
</html>

```
**※ let a = Number(prompt()); : 입력과 동시에 string을 number 형 변환이 가능하다.**






- **swtich 문**
```html

<!DOCTYPE html>
<html>
<head>
    <script> 
    
    let a = Number(prompt("국어 점수를 입력하세요."));
    let b = Number(prompt("수학 점수를 입력하세요."));
    let c = Number(prompt("영어 점수를 입력하세요."));
    
    let sum = a+b+c;    
    let avg = (a+b+c)/3;
    console.log("avg =", avg);


[특정 값을 이용한 switch]

    switch(avg) {
        case 90:
            console.log("A");
            break;
        case 80:
            console.log("B");
            break;
        case 70:
            console.log("C");
            break;
        case 60:
            console.log("D");
            break;
        default:
            console.log("F");
    }


[범위 값을 이용한 switch]
        switch(true) {
        case avg >= 90:
            console.log("A");
            break;
        case avg >= 80:
            console.log("B");
            break;
        case avg >= 70:
            console.log("C");
            break;
        case avg >= 60:
            console.log("D");
            break;
        default:
            console.log("F");
    }
    </script>
</head>
<body>


</body>    
```








- **|| , && 연산자**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let isWinner = false;
        let toeic = 900;
        
        // < || 연산자 >
        // isWinner = true 이면, toeic 점수와 관계없이 합격
        // isWinner = false 이면, toeic 점수도 확인 해봐야 한다.

        // < && 연산자 >
        //  isWinner, toeic 모두 조건문을 만족해야 합격

        if (isWinner || toeic >= 900) {
            console.log("합격");
        }   else {
            console.log("불합격");
        }
        
    </script>

</head>
<body>

</body>
</html>
```







- **연산자를 이용한 문자열 사용**

``` html
<!DOCTYPE html>
<html>
<head>
    <script>
        let sayHello = prompt();
        // indexOf : 0안1녕2하3세4요

        if (sayHello.indexOf("안녕") >= 0) {
            alert("안녕하세요");
        } else if (sayHello.indexOf("잘자") >=0 || sayHello.indexOf("잘 자") >= 0) {
            alert("안녕히 주무세요");
        }
        
            
        
    </script>

</head>
<body>

</body>
</html>
```







### 6. 반복문

---

- **배열**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let array = [232, 32, 103, 55, 42];
        console.log(array); 
        console.log(typeof array);    //object
        console.log(array.length);    // 4
        console.log(array[1]);        // 32
        array[1] = 100;
        console.log(array[1]);        // 100

        
    </script>

</head>
<body>


</body>
</html>
```







- **for문을 이용한 random 게임**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let comNum = Math.floor(Math.random() * 100); // 0~1 숫자만 생성
        for (var i=1; i<=10; i++) {
            let userNum = Number(prompt("당신의 "+ i + "번 째 예측 숫자는?"));
            if (comNum > userNum) {
                alert("보다 큰 수를 입력해 주세요.");
            } else if (comNum < userNum) {
                alert("보다 작은 수를 입력해 주세요.");
            } else {
                alert("정답입니다.");
                break;
            }
            
        }
        </script>
</head>
<body>

</body>
</html>
```
