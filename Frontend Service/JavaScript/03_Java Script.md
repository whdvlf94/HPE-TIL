# Java Script - day 4



### 1. 브라우저 객체 모델

---

```html
<!DOCTYPE html>
<html>
<head>
  <script>
     window.open("https://www.naver.com", "myNaver", "width=600, height=400", true)
  </script>
</head>
<body>
<ul>
    <!-- a tag 이용 링크를 추가-->
    <li><a onclick="window.open('https://www.naver.com')">Naver</a></li> 
    <li><a onclick="window.open('https://www.daum.net')">Daum</a></li>
    <li><a onclick="window.open('https://www.google.com')">Google</a></li>
</ul>
</body>
</html>
```
※ 일반적으로 3회 이상 반복되는 반복문에 대해서는 함수를 정의해 사용한다.



```html
<!DOCTYPE html>
<html>
<head>
  <script>
    // 함수 추가  
    function myOpen(url) {
        window.open(url);
    }
  </script>
</head>
<body>
<ul>
    <li><a onclick="myOpen('https://www.naver.com')">Naver</a></li> 
    <li><a onclick="myOpen('https://www.daum.net')">Daum</a></li>
    <li><a onclick="myOpen('https://www.google.com')">Google</a></li>
</ul>
</body>
</html>
```



- **window, location 객체**
```html
<!DOCTYPE html>
<html>
<head>
  <script>
    // window 객체의 매서드
    function myOpen(url) {
        window.open(url, '_blank', 'width=600, height=400,');
    }
    // location 객체의 속성
    function myLocation(url){
        location.href="https://www.multicampus.com";
    }
  </script>
</head>
<body>
<ul>
    <li><a onclick="myOpen('https://www.naver.com')">Naver</a></li>
    <li><a onclick="myOpen('https://www.daum.net')">Daum</a></li>
    <li><button onclick="myLocation()">Multicampus</a></li>
</ul>
</body>
</html>
```





- **navigator 객체**
```html
  <script>
    
    console.log(navigator.appCodeName);
    console.log(navigator.appName);
    console.log(navigator.appVersion);
    console.log(navigator.platform);
    console.log(navigator.userAgent);
  </script>
```
※ 웹 페이지를 실행하고 있는 브라우저에 대한 정보






### 2. 문서 객체 모델
---


- **document 객체의 노드 생성 매서드**

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
          // window.onload : body가 실행되기전 먼저 실행되어야 하는 내용들
            window.onload = function() {

                let ul_tag = document.createElement("ul");
                let li_tag1 = document.createElement("li");
                let li_tag2 = document.createElement("li");
                let _naver = document.createTextNode("Naver");
                let _daum = document.createTextNode("Daum");

                li_tag1.appendChild(_naver);
                li_tag2.appendChild(_daum);
                ul_tag.appendChild(li_tag1);
                ul_tag.appendChild(li_tag2);

                document.body.appendChild(ul_tag);
            
            };
        </script>
    </head>
    <body>
        <h1>Hello, DOM</h1>
    </body>
</html>
```
![문서객체](https://user-images.githubusercontent.com/58682321/73322487-3c20f600-4288-11ea-8d95-1cda368cfce7.PNG)

**※ document.createElement/appendChild 명령어를 통해 <body> 내에 명령어를 추가할 수 있다.**






- **img 태그 사용**
```html
    <script>
        window.onload = function() {
        // img 태그 추가
        
        let myImg = document.createElement("img");
        myImg.src = "googlelogo.png"
        myImg.width = 100;
        myImg.height = 100;
        
        document.body.appendChild(myImg);
    </script>
```





- **innerHTML 속성**
```html
<!DOCTYPE html>
<html>
<head>
    <script>
        window.onload = function() {
        // img 태그 추가
        
        let myImg = document.createElement("img");
        myImg.src = "googlelogo.png"
        myImg.width = 100;
        myImg.height = 100;
        
        document.body.appendChild(myImg);
        
        1)
        document.body.innerHTML = "<h1 style='color: blue;'>Hello, jp. Hi, there</h1>"
        // innerHTML 속성 기존에 적용했던 속성 값들이 다 사라지고 해당 내용만 웹 페이지 상에 나타난다.
        
        2)
        let div1 = document.getElementById("myDiv1");
        div1.innerHTML = "<h1 style='color: blue;'>Hello, jp. Hi, there</h1>"
        // div 태그를 이용하면 innerHTML 속성하더라도 기존에 body에 있었던 내용이 사라지지 않는다.

        //document.body.removeChild(document.getElementById('myH1'));
        // removeChild, getElementById 를 이용하여 body 내에 있는 내용들을 제거할 수 있다.
        }

    </script>
</head>

<body>
<!--<h1 id="myH1">Hello, DOM</h1>-->
    <h1>Hello, DOM</h1>
    <!-- id는 고유한 값으로 부여-->
    <div id="myDiv1">
        here
    </div>

</body>
</html>
```
**※ innerText 는 웹 페이지 상에서 Text 형태로 나타난다.**






- **form 이용 예제**
```html
<!DOCTYPE html>
<html>
    
<script>

  function saveTemporary() {
    //   선택된 sns 값 취득
    let selectedSns = document.getElementsByName("sns");
    let selectCount = 0;
            for (let i = 0; i < selectedSns.length; i++) {
                if (selectedSns[i].checked) {
                    selectCount++;
                    console.log(selectedSns[i].value);
                }
            }
                if (selectCount == 0) {
                    alert("sns를 선택해 주세요.");
                }
            };   
</script>    
</head>
    <body>
       
        <form action="table.html" method="GET">

            사용하는 SNS:
             <input type="checkbox" name="sns" value="Facebook"> Facebook
             <input type="checkbox" name="sns" value="Twitter"> Twitter
             <input type="checkbox" name="sns" value="Instagram"> Instagram
             <input type="checkbox" name="sns" value="Google+"> Google+ <br/>


             <input type="button" value="임시 저장" onclick="saveTemporary()">


        </form>
    </body>
</html>
```
![form예제](https://user-images.githubusercontent.com/58682321/73327208-65498280-4298-11ea-8871-2fa426445ef4.PNG)





### 3. 실습

---

- **1) 날씨 웹 페이지 생성**

[Open API 참조](https://openweathermap.org/current)

```html
<!DOCTYPE html>
<html>

<head>
    <script>
    let weather_json =
            `{
    "weather": [
        {
            "id": 804,
            "main": "Clouds",
            "description": "overcast clouds",
            "icon": "04d"
        }
    ],
    "main": {
        "temp": 9.27,
        "temp_min": 9,
        "temp_max": 10,
        "humidity": 45
    },
    "wind": {
        "speed": 1.5
    },
    "dt": 1580272499
}`;


window.onload = function () {

    let _img = document.getElementById("_img");
    let _temp = document.getElementById("_temp");
    let _temp_min = document.getElementById("_temp_min");
    let _temp_max = document.getElementById("_temp_max");
    let _wind = document.getElementById("_wind");
    // json 파일의 값을 각 tag 값에 지정
  
    let parseJson = JSON.parse(weather_json);
    // 문자열 데이터를 객체로 변환

    let _image = document.createElement("img")
    _image.src = "https://openweathermap.org/img/wn/"
     +  parseJson.weather[0].icon + "@2x.png";

    //  배열 데이터이므로 배열 인덱스 [0] 지정해주어야 한다.
     _img.appendChild(_image)

    _temp.innerText = "현재온도: " + parseJson.main.temp + " 도"
    + ', ' + parseJson.weather[0].main;
    
    _temp_min.innerText = "최저기온: " + parseJson.main.temp_min ;
    _temp_max.innerText = "최고기온: " + parseJson.main.temp_max ;

    _wind.innerText = "/ 풍속: " + parseJson.wind.speed;


}

    

    </script>
</head>
<body>
    <table>
        <tr>

            <td rowspan="2" id="_img"></td>
            <td colspan="2" id="_temp">현재온도: 10도, 맑음</td>
            
        </tr>
        <tr>
            
           <td>
            <spin id=_temp_min style="color: blue;">10 /</spin>
            <spin id=_temp_max style="color: red;"> 20</spin>
            <td id="_wind">풍속</td>
            </td>
        </tr>
        


    </table>


</body>
</html>
```

![weather](https://user-images.githubusercontent.com/58682321/73332782-240e9e00-42ab-11ea-9e2f-918317935a09.PNG)






- **2) 현재 시간 생성**
```html
<!DOCTYPE html>
<html>
<head>
    <script>
    window.onload = () => {
        let clock = document.getElementById("clock");

        setInterval(function() {
            clock.innerHTML = new Date().toString();
        }, 1000);
    }
    
    </script>


</head>
<body>
    <h1 id="clock"></h1>
    
</body>
</html>
```
**※ function() {}  ==  () => {}**







### 4. 이벤트
---



- **대표적인 이벤트**
```
window.onload: window 객체의 onload 속성에 함수 자료형 할당
header.onclick()
```






- **onclick 이벤트**

---




- **예제 1)**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        
    window.onload = () => {
        let clock = document.getElementById("clock");

        setInterval(function() {
            clock.innerHTML = new Date().toString();
        }, 1000);

        clock.onclick = () => {
            clock.style = "color: blue;";
            clock.style.backgroundColor = "red";
            // backgroundColor => 배경색
        };
    }
    
    </script>


</head>
<body>
    <h1 id="clock"></h1>
    
</body>
</html>
```





- **예제 2) - clock.html**
```html
<!DOCTYPE html>
<html>
<head>
    <script>
        
    window.onload = () => {
    
        let counter = document.getElementById("counter");
        
        let plusBtn = document.getElementById("plus")
        plusBtn.onclick = function() {

            counter.innerText ++;
        }

        let minusBtn = document.getElementById("minus")
        minusBtn.onclick = function() {
            counter.innerText --;
        }
    }
    
    </script>


</head>
<body>
    <h1 id="counter">0</h1>
    <button id="plus">+</button>
    <button id="minus">-</button>

    
</body>
</html>
```




- **예제 3) - form2.html**
```html
<!DOCTYPE html>
<html>

<script>

    function saveTemporary() {
        //   선택된 sns 값 취득
        let selectedSns = document.getElementsByName("sns");
        let selectCount = 0;
        for (let i = 0; i < selectedSns.length; i++) {
            if (selectedSns[i].checked) {
                selectCount++;
                console.log(selectedSns[i].value);
            }
        }
        if (selectCount == 0) {
            alert("SNS는 필수사항 입니다.");
        }
    }


    let inputs = document.getElementsByName("input");
    for (let i = 0; i < inputs.length; i++) {
        console.log(inputs[i])
    }
    window.onload = function () {
        let btnNext = document.getElementById("btnNext");
        btnNext.onclick = function () {
            let name = document.getElementById("txtName");
            let id = document.getElementById("txtId");
            let pwd = document.getElementById("txtPwd");

            if (name.value == "") {
                alert("이름은 필수사항 입니다.")
                return;
            }
            if (id.value == "") {
                alert("아이디는 필수사항 입니다.")
                return;
            }
            if (pwd.value == "") {
                alert("비밀번호는 필수사항 입니다.")
                return;
            }
            // 다음 단계 or form.action 페이지로 이동
            let form = document.getElementById("registform");
            form.action = "next.html"
            form.submit();

        }
    }




</script>
</head>

<body>

    <form action="table.html" method="POST" id="registform">
        <div>*는 필수 입력사항입니다.</div>
        * 이름: <input type="text" name='name' id='txtName' placeholder="이름을 입력하세요"><br />
        * 아이디: <input type="text" name='id' id="txtId" placeholder="아이디를 입력하세요"><br />
        * 비밀번호: <input type="password" name='password' id="txtPwd" placeholder="비밀번호를 입력하세요"><br />

        사용하는 SNS:
        <input type="checkbox" name="sns" value="Facebook"> Facebook
        <input type="checkbox" name="sns" value="Twitter"> Twitter
        <input type="checkbox" name="sns" value="Instagram"> Instagram
        <input type="checkbox" name="sns" value="Google+"> Google+ <br />

        자기 소개:
        <textarea cols="40" rows="5" name='profile'>

             </textarea><br />
        <input type="submit" value="회원 가입">
        <input type="button" value="다음단계" id="btnNext">
        <input type="button" value="임시 저장" onclick="saveTemporary()">


    </form>
</body>

</html>
```
