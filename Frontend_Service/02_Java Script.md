# Java Script - day3


### 1. 객체
---
> java Script) 객체 : Object



- **속성** : 객체가 가진 값
- **매서드** : 객체가 가진 속성 중에 함수 자료형인 것, 객체 내에 포함되어 있는 fucntion

```html
<script>
    alert(typeof([]));
    // [] : 배열, 출력 시 object로 표기된다.
</script>
```





- 배열은 객체를 기반으로 함
- 배열은 요소에 인덱스로 접근/ 객체는 요소에 키로 접근
```html
<script>


// 배열
var product = ['a', 'b', 'c'];

console.log(produect[0])
// a 출력

// 객체
    var product = {
        a: 1,
        b: 2,
        c: 3
    };

console.log(a)
// 1 출력     
</script>
```






- **속성과 매서드**

```html
<!DOCTYPE html>
<html>  
<head>
<script>

let person = {

name: '윤종필',
address: '경기',

// 매서드 : 객체 내에 포함되어 있는 fucntion
eat: function(food) {
    console.log(food + '을(를) 먹었습니다.')
    }
};

let school = {

name: 'Multi Campus',
capacity: [30, 30, 30],
grade: '3',
event: function(month, eventname) {
    console.log(this.name + ',' + month + ' 달에 ' + eventname +' 이벤트가 있습니다.')
    // school 객체 내에 있는 name 이라는 요소를 출력하고 싶을 때는
    // fucntion에서 함수명을 따로 지정해주지 않고
    // console.log에서 'this'라는 함수를 이용하여 객체 내에 있는 요소를 사용할 수 있다.
}
};

// '윤종필'라는 요소에 person.name 이라는 키로 접근
console.log(person.name)
person.eat('바나나');
school.event('1', '졸업식')

</script>
</head>

</html>
```




- **with 키워드**
```html
<!DOCTYPE html>
<html>
<head>
<script>
    let student = {};

        student.name = '윤종필'
        student.kor = 90,
        student.mat = 80,
        student.eng = 70

        let sum = 0;
        let avg = 0;

        // with: student 객체 내에 있는 속성들을 사용하겠다. 객체 이름을 선언하지 않아도 된다. 
        with(student) {
            sum = kor+mat+eng;
            avg = sum/3;
        }


console.log(sum)
console.log(avg)

    

</script>

</head>
</html>
```




- **객체와 배열을 사용한 데이터 관리**
```html
<!DOCTYPE html>
<html>
<head>
    <script>
    // 배열 [], 객체 {}
    let students = [];
    students.push({이름:'A', 국어:90, 수학:88, 영어:98});
    students.push({이름:'B', 국어:80, 수학:78, 영어:88});
    students.push({이름:'C', 국어:70, 수학:68, 영어:78});
    students.push({이름:'D', 국어:60, 수학:58, 영어:68});
    students.push({이름:'E', 국어:50, 수학:48, 영어:58});
    
    for (let i in students) {
        // students[i].sum = students[i].국어 + students[i].수학 + students[i].영어;
        // students[i].avg = students[i].sum / 3;
        students[i].sum = function() {
            return this.국어 + this.수학 + this.영어;
        }

        students[i].avg = function() {
            return studetns[i].sum/3;
        }

    }
    let totalavg = 0;
    for (let i in students) {
        with(students[i]) {
            console.log(이름 + ":" + sum() + "/" + avg());
            totalavg+= avg()
        }
    }
     // 전체 평균 구하기
    console.log('전체 평균: ' + (totalavg/students.length))
    </script>

</head>    
</html>
```





### 2. 생성자 함수

---
> this 키워드 

```html
    <script>
    function Student(name, kor, eng, mat, sci) {
        this.이름 = name; 
        this.국어 = kor;
        this.영어 = eng;
        this.수학 = mat;
        this.과학 = sci;
        this.sum = function() {
            return this.국어 + this.영어 + this.수학 + this.과학
        }

        this.avg = function() {
            return this.sum() / 4;
        }
    }
    let student1 = new Student('A', 100, 90, 80, 70);
    let student2 = new Student('B', 100, 60, 50, 70);
    console.log(student1.sum() + '/' + student1.avg());
    console.log(student2.sum() + '/' + student2.avg());

    
    </script>
```





- **배열 이용**
```html
<script>
    function Student(name, kor, mat, eng) {
        this.이름 = name; 
        this.국어 = kor;
        this.수학 = mat;
        this.영어 = eng;
        this.sum = function() {
            return this.국어 + this.영어 + this.수학
        };
        this.avg = function() {
            return this.sum() / 3;
        };
    };
    let students = [];
    students.push( new Student('A', 100, 90, 80));
    students.push( new Student('B', 90, 90, 80));
    students.push( new Student('C', 80, 90, 80));
    students.push( new Student('D', 70, 90, 80));
    students.push( new Student('E', 60, 90, 80));

    totalavg = 0;
    for (let i in students) {
        with(students[i]) {
            console.log(이름 + ":" + sum() + "/" + avg());
            totalavg+= avg()
        }
    }
     // 전체 평균 구하기
    console.log('전체 평균: ' + (totalavg/students.length))
   

    
    </script>
```





### 3. 기본 내장 객체

---

```html
    <script>
        let primitiviNumber = 273; //속성, 매서드 추가 x
        let objectNumber = new Number(273); //속성, 메소드 추가 o
        Number.prototype.method = function() {
            return "Method on Prototype"
        };

        console.log("Primitive: " + primitiviNumber);
        // Primitive: 273 -> 기본 자료형 숫자
        console.log("Object: " + objectNumber + '/' + objectNumber.method());
        // Object: 273/Method on Prototype -> 생성자 함수를 사용한 객체
        
    </script>
```






- **속성과 매서드**

```html
    <script>
        let student = {
            name: 'A',
            grade: '3',
            // 모든 객체는 toString() 매서드를 갖는다 (default : object Object)
            // 아래와 같이 다시 선언하면 toString()은 재선언 된다.
            toString: function() {
                return this.name + ' : ' + this.grade
            }        
        };
        console.log(student);
        console.log(student.toString());
        // A : 3 출력
        // toString() 매서드는 객체를 문자열로 변환하는 매서드
    </script>
```






- **String 객체**

```
※ 자주 사용하는 String 객체
charAt(position)
concat(args) - 문자열 결합
indexOf(searchString, position)
lastindexOf(searchString, position)
match(regExp)
split(separator, limit)
substr(start, count)
substring(start,end)
```

```html
    <script>
        let str1 = 'Hello World!'
        console.log(str1.length)
        // 12
        console.log(str1.charAt(0));
        // H
        console.log(str1.concat(" Hi, there!"))
        // Hello World! Hi, there!
        console.log(str1.indexOf("World"))
        // 6
        console.log(str1.lastIndexOf("World"))
        // 6
        let ipaddress = '127.0.0.1'
        let value = ipaddress.split('.')
        // 문자열을 separator로 잘라 배열을 리턴
        // value = ['127', '0', '0', '1']
        console.log(typeof value)
        // 배열 = object
        console.log(value[0]);
        // 127
        console.log(ipaddress.substr(0,3))
        // 127
        console.log(ipaddress.substring(4,6))
        // 0.
        
        let string = 'abcdef'
        string = string.toUpperCase();
        console.log(string)
        // ABCDEF 대문자 변환
    </script>
```






- **Array 객체**
```
pop()* - 배열의 마지막 요소를 제거하고 리턴
push()* - 배열의 마지막 부분에 새로운 요소를 추가, 동적인 배열
sort()* - 배열의 요소를 정렬
splice()* - 요소의 지정한 부분을 삭제하고 삭제한 요소를 리턴
```






- **pop()\***
```html
<script>

let students = [];
students.push( new Student('A', 100, 90, 80));
students.push( new Student('B', 90, 90, 80));
students.push( new Student('C', 99, 90, 90));
students.push( new Student('D', 70, 90, 80));
students.push( new Student('E', 60, 90, 80));

let totalRow = students.length

// students.length는 pop() 매서드 진행에 따라
// 그 값이 달라지기 때문에, 이전에 기존의 length를 변수로 지정한 값을 넣는다.
for (let i=0; i<totalRow; i++) {
console.log(students.pop())
}

</script>
```





- **sort()\***
```html
<script>
let students = [];
students.push( new Student('A', 100, 90, 80));
students.push( new Student('B', 90, 90, 80));
students.push( new Student('C', 99, 90, 90));
students.push( new Student('D', 70, 90, 80));
students.push( new Student('E', 60, 90, 80));


totalavg = 0;

// 속성 간 국어 점수를 비교하여 오름 차순으로 정렬
students.sort(function(left,right) {
    return right.국어 - left.국어;
});
for (let i in students) {
    with(students[i]) {
        console.log(이름 + ":" + sum() + "/" + avg());
        totalavg+= avg()
    }
}
</script>
```






- **Date 객체**
```html
    <script>
        let now = new Date();
        console.log(now);
        for (let i=0;i<10000;i++) {
            ;
        }
        let now2 = new Date('Tue Jan 28 2019 14:21:29');
        console.log(now2);
        console.log(now-now2);


    </script>
```







<h4>ECMAScript 5 Array 객체</h4>

---



- **반복 매서드 - forEach**

```html
    <script>
        let array = [1, 2, 3, 6, 7, 5, 8, 10, 4, 9];

        for(i=0; i<array.length; i++) {
            console.log(array[i])
        }
        console.log('-----------------------------')
        // forEach : for 문을 쓰지 않고도 배열의 속성들을 순차적으로 하나씩 출력하는 명령어
        // 홀수인 속성들만 새로운 배열에 추가하기
        let newArray = [];
        array.forEach(function(element) {
            if (element%2 != 0) {
                newArray.push(element);
            }});
        newArray.forEach(function(element) {

            console.log(element);
        });

        // map : 객체에 새로운 규칙을 추가
        let newMap = array.map(function(element) {
            return element*10;
        });

        newMap.forEach(function(element) {
            console.log(element);
        });

    </script>
```






- **등급에 따라 나누기**
```html
<script>
    let array = [100, 80, 75, 55, 79, 95, 68, 100, 94, 59];
    
    let grade = array.map((element) => {
        if (element >= 90) {
            return  'A';
        } else if (element >= 80) {
            return  'B';
        } else if (element >= 70) {
            return  'C';
        } else if (element >= 60) {
            return  'D';
        } else {
            return  'F';
        }
    });
    for (let i =0; i<array.length; i++) {
        console.log(array[i] + " -> " + grade[i]);
    }
</script>
```





- **조건 매서드 - filter**
```html

<script>
    let jumsu = [100, 80, 75, 55, 79,95, 68, 100, 94, 59];
    jumsu = jumsu.filter(function(element, index, array) {
        return element >= 80;
    });
    jumsu.forEach(function(element) {
        console.log(element);
    });
    
</script>
```





<h4>json 객체</h4>

---

```html

<script>
let json = {
"id": 12345,
"accountNumber": "123-456",
"name": "윤종필" ,
"balance": 1000,
"lastTxDate": "2020-01-22"
};
console.log(json)
console.log(typeof json);
console.log(json.name + "/" + json.balance);
// ▶ object
// Object
// 윤종필/1000


let json = `{
"id": 12345,
"accountNumber": "123-456",
"name": "윤종필" ,
"balance": 1000,
"lastTxDate": "2020-01-22"
}`;
console.log(json)
console.log(typeof json);
console.log(json.name + "/" + json.balance);
/*
`{
"id": 12345,
"accountNumber": "123-456",
"name": "윤종필" ,
"balance": 1000,
"lastTxDate": "2020-01-22"
}`
*/
// string
// undefined/undefined
</script>
```


- **Object -> string : stringify**

```html
    <script>
    let json = {
    "id": 12345,
    "accountNumber": "123-456",
    "name": "윤종필" ,
    "balance": 1000,
    "lastTxDate": "2020-01-22"
    };

    console.log(JSON.stringify(json));
    console.log(typeof JSON.stringify(json));
    // string

    </script>
```

- **string -> object : parse**
```html
    <script>
    let json = `{
    "id": 12345,
    "accountNumber": "123-456",
    "name": "윤종필" ,
    "balance": 1000,
    "lastTxDate": "2020-01-22"
    }`;

    console.log(JSON.parse(json));
    console.log(typeof JSON.parse(json));
    // Object
    </script>
```





- **sample 예제**

```html
<!DOCTYPE html>
<html>
<head>
    <script>
        let openapi = `{
            
    "coord": { 
        "lon": 139,
        "lat": 35
    },

  "weather": 
  [
    {
      "id": 800,
      "main": "Clear",
      "description": "clear sky",
      "icon": "01n"
    }
  ],

  "base": "stations",

  "main": 
  {
    "temp": 281.52,
    "feels_like": 278.99,
    "temp_min": 280.15,
    "temp_max": 283.71,
    "pressure": 1016,
    "humidity": 93
  },

  "wind": 
  {
    "speed": 0.47,
    "deg": 107.538
  },

  "clouds": 
  {
    "all": 2
  },

  "dt": 1560350192,

  "sys": 
  {
    "type": 3,
    "id": 2019346,
    "message": 0.0065,
    "country": "JP",
    "sunrise": 1560281377,
    "sunset": 1560333478
  },

  "timezone": 32400,
  "id": 1851632,
  "name": "Shuzenji",
  "cod": 200

            
        }`
console.log(openapi)        
console.log(typeof openapi)

let parseJson = JSON.parse(openapi);

console.log("현재날씨:" + parseJson.weather[0].main)
with(parseJson.main) {
console.log("현재온도:" + temp)
console.log("최고온도:" + temp_max)
console.log("최저온도:" + temp_min)
}

// console.log(JSON.parse(openapi))
    </script>
</head>    
</html>
```
