# Java Script - day2


### 1. 반복문
---


- **While 문**

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
            var Value = 0;
            var startTime = new Date().getTime();

            while (new Date().getTime() < startTime) {
                Value++;
                }
        //   for(var cps =0; new Date().getTime() < startTime + 1000; cps++) {}

            alert(Value);

        
        </script>
    </head>

</html>
```
**※ 조건에 따라 while 문은 작동하지 않을 수도 있다. 하지만, do while 문의 경우 무조건 실행함**





- **for 문 - 이중 중첩**


- 삼각형 모양 * 출력
---

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
          let output = "";
          
          for(let row = 1; row <= 10; row++) {
              for(let col = 0; col<row; col++) {
                output += '*';

             }
             output += '\n'
          }
             console.log(output);

        
        </script>
    </head>

</html>
```

- 역삼각형 모양 * 출력
---

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
          let output = "";
          
          for(let row = 1; row <= 10; row++) {
              for(let col = 10; col>row; col--) {
                output += '*';

             }
             output += '\n'
          }
             console.log(output);

        
        </script>
    </head>

</html>
```

- 피라미드 모양 * 출력

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
          let output = "";
          
          for(let i = 0; i<15; i++) {
            for(let j=15; j>i; j--) {
                output+= ' ';
             }
            for(let k=0; k<2*i+1; k++) {

                 output += '*';
            }
             output += '\n'
          }
             console.log(output);

        
        </script>
    </head>

</html>
```


- **소수 출력**
```html
<!DOCTYPE html>
<html>
    <head>
        <script>

        for (let i=2; i<=10; i++) {
            let isPrime = true;
            for(let j=2; j<i; j++){
                if (i%j ==0) {
                    isPrime =false;
                    break;
                }
            }
        
        if (isPrime) {
            console.log(i);
        }
        }
        
    
         
        </script>
    </head>

</html>
```

- **max,min 출력**

```html
<!DOCTYPE html>
<html>
    <head>
        <script>
            let array = [52, 273, 103, 32, 57, 103, 31, 2];
            let max = array[0];
            let min = array[0];

            for (i=0; i<8; i++) {
                if (max < array[i]) {
                    max = array[i]
                }
                if (min > array[i]) {
                    min = array[i]
                }
            }
            console.log(max)
            console.log(min)

            
          
        </script>
    </head>

</html>
```



### 2. 함수
---

- **함수**

```html
<!DOCTYPE html>
<html>
    <head>
        <script>

            // 익명 함수
            let func = function() {
                let output = prompt("숫자를 입력해 주세요.")
                alert(output)
            };

            func();

            // 함수명 지정, 입력 없이 바로 호출
            let func2 = function(name) {
                alert("Hello " + name)
                // return 'Hello' + name
                
            };

            func2('종필')
            // console.log(func2('종필'))
    
         
        </script>
    </head>

</html>
```
**※ 함수명을 지정해서 func(원하는 변수명)으로 지정하여 함수를 호출 할 수도 있다.**




- **array 매개 변수**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let myArray1 = ["apple", "banana", "orange"];
        let myArray2 = new Array()
        myArray2[0] = "grape"
        myArray2[1] = "grape1";


        console.log(myArray1);
        console.log(myArray2);


</script>

</head>
</html>
```


- **타이머 함수**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        setTimeout(function() {
            alert('Works!');
            } , 5000);

        // 5초 후에, Test 문자열을 alert로 띄우겠다.

</script>

</head>
</html>
```


- **eval 함수**
```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let willEval = "";
        willEval += "var number=10;";
        willEval += "alert (number);";

        eval(willEval);

        // 문자열을 코드로 실행할 수 있는 함수

</script>

</head>
</html>
```


- **arguments 변수 활용**
> 매개변수의 배열. 객체 자료형이므로 object를 출력함

```html
<!DOCTYPE html>
<html>
<head>
    <script>
function power() {
    if (arguments.length == 1){
    console.log(arguments[0]*arguments[0]);

    } else if (arguments.length == 2){
    console.log(arguments[0]**arguments[1]);

    }
}
function multiply() {
    let result = 1;
    for(i=0; i<arguments.length; i++) {
    result = result*arguments[i]
    }
console.log(result)
}

power(3); //arguments.length = 1
power(3,4); // arguments[0]=3, arguments[1]=4
multiply(1,2,3,4,5);



</script>

</head>
</html>
```
※ typeof (arguments) = object