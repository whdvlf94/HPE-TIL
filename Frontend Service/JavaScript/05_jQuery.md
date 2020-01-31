# jQuery

### 1. 기본
---

- **기본, 태그 선택자**

```html
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script>
            $(document).ready(function() {
            // jQuery를 사용한 모든 웹 페이지는 위의 코드로 시작

                $('h1, p').css('color', 'orange');
                // ,를 사용하면 복수 개의 태그에 색을 적용할 수 있다.

                $('#myH1').css('color', 'blue');
                // id에 함수를 적용하기 위해서는 앞에 #를 붙여야 한다.
            });
        </script>
    </head>
    <body>
       <h1>Lorem ipsum</h1>
       <p>Lorem ipsum dolor sit amet.</p>
       <h1>Lorem ipsum</h1>
       <p>consectetur adipiscing elit.</p>
       <h1 id="myH1">header-0</h1>

    </body>
</html>
```

- **클래스 선택자**
```html
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script>

            $(document).ready(function() {
                $('.item').css('color', 'red')
                // .item -> 모든 class에 적용
           
            });
        </script>
        <!-- style 설정 방법 -->
        <style>
            .item {
                color: yellow;
                background-color: black;
            }
        </style>
    </head>
    <body>
        <h1 class="item">header-0</h1>
        <h1 class="item">header-1</h1 class="">
        <h1 class="item">header-2</h1 class="">
       
    </body>
</html>
```

- **자손, 후손 선택자**
```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $('div li').css('color', 'red');
            $('div > h1').css('color', 'blue');
            // div 한 단계 아래(자손)에 있는 h1 태그만 변경된다.

            // $('div h1').css('color', 'blue');
            // div 아래에 있는 모든(후손)에 있는 h1 태그 변경



        });
    </script>
</head>

<body>
    <div>
        <h1>Header-0</h1>
        <ul>
            <li>Apple</li>
            <li>Bag</li>
            <li>Cat</li>
            <li>Dog</li>
            <li>Elephant</li>
        </ul>
        <span>
            <h1>Hearder-1</h1>
        </span>
    </div>


</body>

</html>
```

- **속성 선택자**
```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $('input[type="text"]').val('test value');
            // type=text 인 속성에만 test value라는 text input
            // $('#name').val() // getter 값을 얻어옴
            // $('#name').val(test) // setter 값을 지정

            // button(onclick 이벤트)를 실행하면 로그 전송
            $('#btn1').on('click', function() {
                console.log($('#input1').val())
                console.log($('#input2').val())
                console.log($('#input3').val())
                console.log($('#input4').val())
                console.log($('#input5').val())
                console.log($('#input6').val())
            });

        });
    </script>
</head>

<body>
    입력필드(일반1):          <input id="input1" type="text"> <br/>
    입력필드(일반2):          <input id="input5" type="text"> <br/>
    입력필드(비밀번호):      <input id="input2" type="password"> <br/>
    라디오:                 <input id="input3" type="radio"> <br/>
    체크박스:               <input id="input4" type="checkbox"> <br/>
    <!-- 파일:                   <input id="input5" type="file"> <br/> -->
    히든(숨김):             <input id="input6" type="hidden"> <br/>

    <button type="button" id="btn1"> 전송 </button>

</body>

</html>
```

- **select - setTimeout**
```html
<script>
  setTimeout(function() {
                // let selectedItem = $('#mySelect')
                let selectedItem = $('select > option:selected').val();
                console.log(selectedItem)

            }, 5000)
            // 5초 뒤, 실행
</script>

<body>
    <select id="mySelect">
        <option value="apple">사과</option>
        <option value="banana">바나나</option>
        <option value="orange">오렌지</option>
        <option value="키위">kiwii</option>
    </select>
</body>
```



- **Selected/Checked 입력 양식 필터 선택자**
```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $('input[type="text"]').val('test value');
            // type=text 인 속성에만 test value라는 text input
            // $('#name').val() // getter 값을 얻어옴
            // $('#name').val(test) // setter 값을 지정

            // button(onclick 이벤트)를 실행하면 로그 전송
            $('#btn1').on('click', function() {
                console.log($('#input1').val())
                console.log($('#input2').val())
                console.log($('#input3').val())
                console.log($('#input4').val())
                console.log($('#input5').val())
                console.log($('#input6').val())
            });

            setTimeout(function() {
                // let selectedItem = $('#mySelect')
                let selectedItem = $('select > option:selected').val();
                // 선택된 라디오 버튼의 값과 체크 박스의 값 출력
                
    
                
                console.log($('input[name="gender"]:checked').val());

            }, 5000)
            // 5초 뒤, 실행

        });
    </script>
</head>

<body>
    입력필드(일반1):          <input id="input1" type="text"> <br/>
    입력필드(일반2):          <input id="input5" type="text"> <br/>
    입력필드(비밀번호):      <input id="input2" type="password"> <br/>
    라디오(남성):           <input id="input7" name="gender" type="radio" value="M"> <br/>
    라디오(여성):           <input id="input8" name="gender" type="radio" value="F"> <br/>

    라디오:                 <input id="input3" type="radio"> <br/>
    체크박스:               <input id="input4" type="checkbox"> <br/>
    <!-- 파일:                   <input id="input5" type="file"> <br/> -->
    히든(숨김):             <input id="input6" type="hidden"> <br/>

    <select id="mySelect">
        <option value="apple">사과</option>
        <option value="banana">바나나</option>
        <option value="orange">오렌지</option>
        <option value="키위">kiwii</option>
    </select>

    <button type="button" id="btn1"> 전송 </button>

</body>

</html>
```


<h4>배열 관리</h4>
---

- **$.each() 매서드**

```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            let array =[
            {name:"Naver", link:'https://www.naver.com'},
            {name:"Google", link:'https://www.google.com'},
            {name:"Daum", link:'https://www.daum.net'},
            {name:"Multicampus", link:'https://www.multicampus.com'},
            
            ];

            // for(let i=0; i<array.length; i++) {
            //     console.log(array[i].name)
            // }

            // each 문장
            // $('array').each(function(){})

            let output = "";
            $.each(array,function(index, item) {
                output += "<li>" + item.name + "(" + item.link + ")" + "</li>"

                // item : name, link 가 포함되어 있다.
                // console.log(item.name + '/' + item.link);
                // object와 문자열이 결합되면 문자열로 출력된다.
            });
            console.log(output)
            /*
            [java script] - 

            1)
            let div1 = document.getElementById("myDiv1");
            div1.innerHTML = output;

            2)
            document.getElementById('contents').innerHTML = output;
            */
           
            $('#myDiv1').append(output)
            // jQuery는 더 간단하게 innerHTML을 사용할 수 있다.
            // innerHTML : 웹 페이지 상에 출력

        });
    </script>
</head>
<body>
    <div id="myDiv1">

    </div>
</body>
</html>

```


- **forecast 실습**
```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script>
    let forecast = `http://api.openweathermap.org/data/2.5/forecast?q=Seoul&APPID=e96e8ea03713a611f7562052ab426863&metric`
    // json 형식의 파일

    $(document).ready(function() {
      // 1. PARSING -> string -> object
      let parsedForecast = JSON.parse(forecast);
      $.each(parsedForecast.list, function(index, item) {
        let _image = document.createElement("img");
          _image.src = "http://openweathermap.org/img/wn/" 
                + item.weather[0].icon +"@2x.png";

        let _divhtml = item.dt_txt;
        _divhtml += ", 기온:" + item.main.temp;
        _divhtml += " <span style='color:blue;'>" + item.main.temp_min + "</span>";
        _divhtml += "/<span style='color:red;'>" + item.main.temp_max + "</span>";
        _divhtml += ", " + item.weather[0].description;

        let imageSpan = document.createElement("span");
        imageSpan.appendChild(_image);
        let infoSpan = document.createElement("span");
        infoSpan.innerHTML = _divhtml;

        let div = document.createElement("div");
        div.appendChild(imageSpan);
        div.appendChild(infoSpan);

        $('#contents').append(div);
      });
    });
  </script>
</head>
<body>
 <div id="contents">
    
 </div>

</body>  
</html>
```



### 2. ajax 이용
> 비동기 방식
---
```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $.ajax({
                url: "http://api.openweathermap.org/data/2.5/forecast",
                method: "GET",
                // GET 방식으로 호출
                data: {
                    q: "seoul",
                    APPID: "e96e8ea03713a611f7562052ab426863",
                    units: "metric"
                    // data 에 포함되어 있는 값들은 url request 시에 필수로 입력해야 하는 값들이다.
                },
                success: function (data) {
                    $.each(data.list, function (index, item) {
                        let _image = document.createElement("img");
                        _image.src = "http://openweathermap.org/img/wn/"
                            + item.weather[0].icon + "@2x.png";

                        let _divhtml = item.dt_txt;
                        _divhtml += ", 기온:" + item.main.temp;
                        _divhtml += " <span style='color:blue;'>" + item.main.temp_min + "</span>";
                        _divhtml += "/<span style='color:red;'>" + item.main.temp_max + "</span>";
                        _divhtml += ", " + item.weather[0].description;

                        let imageSpan = document.createElement("span");
                        imageSpan.appendChild(_image);
                        let infoSpan = document.createElement("span");
                        infoSpan.innerHTML = _divhtml;

                        let div = document.createElement("div");
                        div.appendChild(imageSpan);
                        div.appendChild(infoSpan);

                        $('#contents').append(div);
                    });
                },
                error: function (err) {
                    console.log("err" + err);
                }
            })

        });
    </script>
</head>

<body>
    <div id="contents"></div>

</body>

</html>
```

<h4>중요</h4>
---

**※ 기존의 forecast.html 파일에서는 날씨 데이터를 다운 받아 코드에 작성해야 했다. 하지만 ajax 관련 매서드를 통해 url 주소로 데이터 값들을 호출할 수 있다.**


