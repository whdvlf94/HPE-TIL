# jQuery - ajax - 비동기 방식

[참조](https://private.tistory.com/24)


### 1. ajax 이용
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


**※ ajax에서 자주 사용되는 요청 방식**
- GET 요청 : 데이터에 접근하기 위함
- POST 요청 : 회원가입과 같이 데이터를 제공하기 위함




- **날씨 검색 실습**
```html
<!DOCTYPE html>
<html>

<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
            $(document).ready(function () {
                // 버튼(검색)을 통한 Query 시작
                $('#btnSubmit').on('click', function () {
                    
                    // [input 을 통한 날씨 검색]
                    // let city = $('#city').val();

                    // [select를 통한 날씨 검색]
                    let city = $('#city > option:selected').val();

                    // [input을 통해 검색할 경우 if을 사용해야 한다.] 
                    // if (city != null && city != '') {


                        $.ajax({
                            url: "http://api.openweathermap.org/data/2.5/forecast",
                            type: "GET",
                            data: {
                                q: city,
                                APPID: "e96e8ea03713a611f7562052ab426863",
                                units: "metric"
                            },
                            success: function (data) {

                                $('#contents').empty();

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
                                console.log("ERROR");
                            }
                        })

                    // }
                })
            });
    </script>
</head>

<body>
    <!-- select를 통한 날씨 검색 -->
    <select id="city">
        <option value="seoul">서울</option>
        <option value="tokyo">도쿄</option>
        <option value="london">런던</option>
    </select>
        <!--input으로 날씨 검색  -->
        <!-- <input type="text" name="city" id="city" placeholder="도시를 입력하세요"> -->

        <button id="btnSubmit">검색</button>
        <!-- 아래와 같은 방법으로도 button 사용 가능, 그러나 요즘에는 위의 방법을 선호한다. type을 따로 지정해주지 않아도 되기 때문 -->
        <!-- <input type="button" id="btnSubmit" value="검색"> -->
 
    <div id="contents"></div>

</body>

</html>
```

- **mychart 실습**
```html
<!DOCTYPE html>
<html>
<head> 
  <meta charset="UTF-8"> 
  <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
  <meta http-equiv="X-UA-Compatible" content="ie=edge"> 
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> 
  <!-- 차트 링크 --> 
  <style>
    .item {
      display: inline-block;
      overflow: hidden;
      width: 120px; 
    }
    .red {
      color: red;
    }
    .blue {
      color: blue;
    }
  </style>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

  <!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>  -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script> 
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script> 
  <script src="../mydate.js"></script>
  <script>

  $(document).ready(function () {
    let dateArray = [];
    let highArray = [];
    let lowArray = [];
    // 최상위 단계에서 변수를 선언했기 때문에, ajax 내부(블럭 내부)에서 사용 가능하다.
    $.ajax({
      url: "https://poloniex.com/public",
      type: "GET",
      data: {
        'command': "returnChartData",
        'currencyPair': "USDT_BTC",
        'start': "1577836800",
        'end': "9999999999",
        'period': "86400"
      },
      success: function (data) {

        $.each(data, function (index, item) {
          let date = convertDate(item.date);
          let high = item.high;
          let low = item.low;
          let volume = item.volume;

          dateArray.push(date)
          highArray.push(high)
          lowArray.push(low)

          let div = document.createElement("div");
          let html = "";
          html += "<span class='item'>" + date + "</span>";
          html += "<span class='item red'>" + high + "</span>";
          html += "<span class='item blue'>" + low + "</span>";
          html += "<span class='item'>" + volume + "</span>";
          div.innerHTML = html;

          $("#contents").append(div)

        })

        let ctx = document.getElementById('myChart').getContext('2d');
        // Context : 작업 영역
        let chart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: dateArray,
            datasets: [{
              label: 'High price',
              backgroundColor: 'transparent',
              borderColor: 'red',
              data: highArray
            }, {
              label: 'Low price',
              backgroundColor: 'transparent',
              borderColor: 'blue',
              data: lowArray
            }]
          },
          options: {
            legend: {
              display: false
            }
          }
        });
      },
        error: function (err) {
          alert("ERROR")
        }
      })
  });
      </script>
      </head>
  
  <body>
    <div class="container">
    <canvas id="myChart"></canvas>
    </div>
    <div id="contents"></div>
  </body>
  
  </html>
  ```

  