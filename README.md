<p align="center">
    <img width="450" src="https://user-images.githubusercontent.com/35298140/135116647-7277fba0-3df1-4c3e-a51f-59398fe18a80.png"
</p>

---

### 개요 
Pusha2는 TCP기반의 푸시 서버구축 라이브러리 입니다.

### 특징
+ 빠른 속도
+ Pusha 보다 안정적임

### 호환성
본 라이브러리는 Kotlin으로 빌드되었고, Java프로젝트와 호환됨.

---

### 사용법(Kotlin)

#### 구성
**관리자**  
+ ServerManager : 서버 구축을 담당  
+ ClientManager : 클라이언트 구축을 담당

**메시지 처리기**  
+ ServerRecieveHandler : 서버가 받은 메시지 처리를 담당
+ ClientRecieveHandler : 클라이언트가 받는 메시지 처리를 담당

**패킷**   
+ SockDto : 데이터 패킷 

#### 구성요소 불러오기 

```kotlin
//ServerManager
val serverManager: ServerManager
    = ServerContainer.serverManager()

//ClientManager
val clientManager: ClientManager 
    = ClientContainer.clientManager()

//ServerRecieveHandler
val serverRecieveHandler:ServerRecieveHandler 
    = ServerContainer.serverRecieveHandler()

//ClientRecieveHandler
val clientRecieveHandler:ClientRecieveHandler 
    = ClientContainer.clientRecieveHandler()
```

#### SockDto 구성

```kotlin
data class SockDto(
    //발신자 아이디
    val from:String,
    
    //명령어 (메시지 처리기에서 식별)
    val command: String,
    
    //분리자 (data의 분리 용도)
    val seperator: String,
    
    //문자 데이터
    val data: String,
    
    //모든 종류의 데이터
    val obj: Any?
) : Serializable
```

---

#### 서버 구축

```kotlin
//포트 지정
ServerManager.port = 9090

//ServerManager 불러오기
val serverManager: ServerManager = ServerContainer.serverManager()
//서버 바인드
serverManager.bind()
//서버 접속 처리기 실행
serverManager.accept()
//서버 메시지 처리기 실행
serverManager.processing()
```
위의 절차로 서버구축이 완료됩니다.

---

#### 클라이언트 구축

```kotlin
//접속할 서버의 아이피와 포트 설정
ClientManager.ip="127.0.0.1"
ClientManager.port=9090;

//ClientManager 불러오기 
val clientManager: ClientManager = ClientContainer.clientManager()

//"name"이라는 이름으로 서버 접속 
//"name"은 서버에서 고유식별자로 사용됨
clientManager.connect("name")

//클라이언트 메시지 처리기 실행
clientManager.processing()
```

---

#### 메시지 전송

##### 서버

```kotlin
//ServerManager
 val serverManager: ServerManager = ServerContainer.serverManager()

//SockDto를 생성후, 메시지 전송
 serverManager.sendData(SockDto(clientManager.id,"NOTICE", "#", "message", null))
```

#### 클라이언트
```kotlin
//ClientManager
val clientManager: ClientManager = ClientContainer.clientManager()

//SockDto를 생성후, 메시지 전송
clientManager.sendData(SockDto(clientManager.id,"NOTICE", "#", "message", null))
```

---

#### 메시지 처리 액션 추가

##### 서버

1.ServerRecieveExcutor을 상속받은 구상클래스를 생성

```kotlin
class NoticeExcutor : ServerRecieveExcutor {
    override fun excute(sockDto: SockDto) {
        PushaLog.log("[${sockDto.from}]==> " + sockDto.data)
    }
}
```
>위의 코드는 로그출력을 수행한다.

2. ServerRecieveHandler에 액션을 추가

```kotlin
//ServerRecieveHandler
val serverRecieveHandler:ServerRecieveHandler 
    = ServerContainer.serverRecieveHandler()

//"NOITCE"라는 명령을 가진 메시지가 들어오면, 
// NoticeExcutor의 execute 실행
serverRecieveHandler.addCommand("NOTICE",NoticeExcutor())
```

##### 클라이언트

1.ServerRecieveExcutor을 상속받은 구상클래스를 생성

```kotlin
class NoticeExcutor : ClientRecieveExcutor {
    override fun excute(sockDto: SockDto) {
        PushaLog.log("[${sockDto.from}]==> " + sockDto.data)
    }
}
```
>위의 코드는 로그출력을 수행한다.

2. ClientRecieveHandler에 액션을 추가

```kotlin
//ClientRecieveHandler
val clientRecieveHandler:ClientRecieveHandler 
    = ServerContainer.serverRecieveHandler()

//"NOITCE"라는 명령을 가진 메시지가 들어오면, 
// NoticeExcutor의 execute 실행
clientRecieveHandler.addCommand("NOTICE",NoticeExcutor())
```

---

Copyright 2021.Sanghoon.All rights reserved