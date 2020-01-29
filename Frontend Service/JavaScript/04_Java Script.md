# jQuery



<h4>jQuery Download</h4>

---
[jQuery](https://jquery.com/download/)

- 직접 다운로드
- CDN 이용 (별도의 다운로드 없이 링크 이동을 통해 사용할 수 있다.)

```html
<!DOCTYPE html>
<html>
    <head>
        <script src="jQuery/jquery.js"></script>
        <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
        <script>
            $(document).ready(function() {
                alert("jQuery start");
            });
        </script>
    </head>
    <body>

    </body>
</html>
```






- **jQuery 사용**
```html
<!DOCTYPE html>
<html>
    <head>
        <script src="jQuery/jquery.js"></script>
        <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
        <script>
            $(document).ready(function() {
                // let h1 = document.getElementById("myH1");
                // 기존 이용방법
                $("#myH1").css('color', 'red');
                // jQuery를 사용하면 더 간단하게 이용할 수 있다.
            });
        </script>
    </head>
    <body>
        <h1 id="myH1">Hello, jQuery</h1>
    </body>
</html>
```






- **jQuery를 이용한 이벤트**
```html
<!DOCTYPE html>
<html>
    <head>
        <script src="jQuery/jquery.js"></script>
        <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
        <script>
            $(document).ready(function() {
                $("#btnRed").on('click', function() {
                    // onclick 이벤트
                    $("#myH1").css('color', 'red');
                    // h1 에 해당하는 문장을 red 색으로 변경
                });
                $("#btnBlue").on('click', function() {
                    $("#myH1").css('color', 'blue');
                });

            });
        </script>
    </head>
    <body>
        <h1 id="myH1">Hello, jQuery</h1>
        <button id="btnRed">RED</button>
        <button id="btnBlue">BLUE</button>
    </body>
</html>
```



**※ javascript 와 비교**

---

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