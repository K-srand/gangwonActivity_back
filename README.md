액티비티 강추! 프로젝트 백엔드 부분입니다.

프론트엔드 소스코드 주소
-> https://github.com/K-srand/gangwonActivity_front

# 액티비티 강추!(강원 액티비티 추천) 프로젝트

태그: 팀 프로젝트

사용기술: AWS, IntelliJ, Java, Mysql, Naver Cloud Platform, React, SpringBoot, TourAPI, Visual Studio Code, 날씨API

진행일자: 2024/06/11 → 2024/07/26

프로젝트 참여인원: 6명

# **P**roject GangwonActivity~~!!!!

<img width="362" alt="20240823_174412" src="https://github.com/user-attachments/assets/d1606724-6155-4208-8b84-72ab7fe3d393">
<img width="362" alt="20240823_174721" src="https://github.com/user-attachments/assets/458fe6d5-89c1-4816-95a1-29879911f6e1">


# 목차

## 📚Project Outline

### 📕목표

실제 사용자들의 리뷰를 기반으로 강원도의 다양한 액티비티와 여행지를 추천하는 여행 플랫폼

### 📙**프로젝트 기간**

- 2024/06/10 - 2024/07/28(7주, 6인 백엔드 개발자)
    - 이후 배포, 프론트엔드 리팩토링 등을 진행 중에 있음

### 📗시연 영상

- [https://youtu.be/ELQ3XLpOPKI](https://youtu.be/ELQ3XLpOPKI)

### 📘**링크**

- 배포 링크
    - [http://3.36.27.202/](http://3.36.27.202/)
- Git 링크
    - 백엔드

        https://github.com/K-srand/Gangwon-do-activity_backend
        
    - 프론트엔드

        https://github.com/K-srand/Gangwon-do-activity_frontend
        

### 

 📙핵심 기능

- 회원 관리 : JWT 토큰을 이용한 사용자 인증 및 권한 부여 시스템 구현
- 추천 알고리즘 : Tour 4.0 API를 활용하여 평점, 거리, 경로 시간을 기준으로 최적의 서비스를 추천하는 알고리즘 설계 및 구현
- 이미지 : AWS S3를 사용하여 이미지 업로드, 저장, 관리 시스템 구축

## **📚System Architecture/ERD**

### 📕System Architecture
![Gangwon_Architecture drawio](https://github.com/user-attachments/assets/38d52035-698e-4f5b-80b1-e45fb5c9de0f)


| **Component** | **툴/프레임워크** |
| --- | --- |
| Backend | Spring Boot 3.3.x/**Java 17** |
| Frontend | React.js 18.0.x/ CSS |
| ORM/Data Mapper | JPA / MyBatis 3.0.3 |
| Database | MySQL Server 8.0 |
| SCM | github |
| CloudStorage | AWS S3 |
| 배포 | Jenkins |
| Test | Postman |
| Api문서 관리 | Swagger |
| TeamCommunication | Zoom, Discord |

### 📙ERD 설계

- **ERD 설계서**

![%EA%B7%B8%EB%A6%BC1](https://github.com/user-attachments/assets/e237c10a-a187-47a9-b8b7-dd1ecc52abd3)



- **ERD 테이블 명세서**

![image](https://github.com/user-attachments/assets/fa111ca7-b84d-479e-a470-afb3a4b903d0)



1. Board : 커뮤니티 데이터 테이블
2. Boardimage : 커뮤니티 이미지 테이블
3. Boardlikesuser : 사용자와 커뮤니티 매핑 테이블
4. Comment : 댓글 데이터 테이블
5. Mycourse : 나의 코스 데이터 테이블
6. Mycourseplace : 나의 코스와 장소 매핑 테이블
7. Myfavoritesuserplace : 사용자와 나의 찜 매핑 테이블
8. Place : TourAPI 데이터 테이블
9. Reportedcontent : 신고된 데이터 테이블
10. User : 사용자 데이터 테이블
11. Weather : 날씨 API 데이터 테이블

## 📚 UX/UI 화면설계서/REST API 명세서

### **📕 UX/UI 화면설계서 및 기능 정의서 링크**

- GoogleDocs(화면설계서, 요구사항정의서, 정책정의서, 기능정의서, TC요구서, WBS포함)
    - [https://drive.google.com/drive/folders/1-wUUd3x-Bkqn-aZ3qXwbIMLeOMdLGVnh?usp=sharing](https://drive.google.com/drive/folders/1-wUUd3x-Bkqn-aZ3qXwbIMLeOMdLGVnh?usp=sharing)
- Figma
    - [https://www.figma.com/design/BFAG60ask6W6TwIgEfPtc6/Figma-basics?node-id=1669-162202&node-type=CANVAS&t=4Bgo0DAaEwq9YjDL-0](https://www.figma.com/design/BFAG60ask6W6TwIgEfPtc6/Figma-basics?node-id=1669-162202&node-type=CANVAS&t=4Bgo0DAaEwq9YjDL-0)

### 📙 **REST API 명세서 (Swagger)**

   (Swagger 사진 첨부 필)

### 📗**백엔드 패키지 구조**

- **백엔드 패키지 구조**
  
![image 1](https://github.com/user-attachments/assets/b49a845a-1df6-4755-af66-93b12bf67e1a)



1. Java : Java package
    
    1-1. Common : ResponseEntity의 CodeMessage(ex : 404, 405..etc)를 포함
    
    1-2. Config : Web, Swagger, API설정 관련 class를 포함
    
    1-3. Controller : controller package
    
    1-4. Dto 
    
    - Request : 클라이언트로부터 요청 받는 패키지
    
    - Response : 클라이언트로 요청을 내보내는 패키지(사실상 ExceptionHandler의 역할 포함)
    
    1-5. Entity : model package
    
    1-6. Exception : Exception package
    
    1-7. Filter : Filter package(SpringSecurity 동작을 포함)
    
    1-8. Mapper : MyBatis와 연동한 mapper를 포함
    
    1-9. Provider : Email, JWT 등 라이브러리 활용/설정 class를 포함
    
    1-10. Repository : 데이터베이스 접근을 담당하는 인터페이스가 위치하는 패키지
    
    1-11. Service : service package
    
2. Resources : resource package
    
    2-1. mapper : mapper XML  package
    
    2-2. static.json : Naver Map API 활용을 위해 강원도 지역 영역 표시를 위한 정적 package
    

- **프론트 패키지 구조**

![image 2](https://github.com/user-attachments/assets/ebea650c-be0d-4610-a5c4-06f186bf2a38)



1. node_modules : npm으로 설치된 패키지들이 저장되는 dir
2. Public : 정적 파일들이 위치하는 dir
3. Src : 소스 코드가 포함된 dir
4. Assets : 애플리케이션에서 사용되는 자원 파일들이 위치하는 dir
5. Images : 이미지 파일들이 위치하는 dir
6. Styles : CSS 파일들이 위치하는 dir
7. Components : React 컴포넌트들이 위치하는 dir
8. Common : 공통으로 사용되는 컴포넌트들이 위치하는 dir
9. Specific : 특정 기능이나 페이지에서만 사용되는 컴포넌트들이 위치하는 dir
10. Pages : 페이지 컴포넌트들이 위치하는 dir

## 📚주요 기능

<img width="540" alt="20240823_171605" src="https://github.com/user-attachments/assets/f1c69fc8-d915-4cbb-8a97-af4d6d0a6bb5">



  → 주요 기능 개략도

### **📕커뮤니티**

- 사용자가 작성한 글/댓글 DB에 저장하고 관리하는 기능.
- CRUD, Pagination, 좋아요/싫어요 기능 및 신고 기능 구현하여 사용자 간의 상호작용을 지원.
- 신고/제재 기능 : 사용자가 작성한 글 및 댓글에 대해 신고 기능 구현. 중복 신고 방지 및 신고된 콘텐츠의 제재 여부 관리.
- 커뮤니티 관련 데이터를 효과적으로 조회하고 관리할 수 있도록 시스템 설계 및 최적화.

<img width="365" alt="20240823_174259" src="https://github.com/user-attachments/assets/24530508-8d6b-4a65-9774-2d7c6ceb45db">

<img width="369" alt="20240823_174215" src="https://github.com/user-attachments/assets/dc9ffd14-a66b-4812-91ae-c576419c3309">


### **📙회원가입/로그인**

- Spring Security 기반으로 OAuth2.0 흐름의 인증 구현. (JWT 형식 사용)
- 삭제된 유저의 경우 재가입 불가능, 삭제/제재 유저를 DB에서 삭제하는 것이 아닌 해당 시점을 TimeStamp로 기록하는 방식
- UserRole에 따라 달리 동작하는 마이페이지/관리자 창을 구현

![image 3](https://github.com/user-attachments/assets/458d31a5-8bf3-439d-9faa-a0bf88e730c7)

![image 4](https://github.com/user-attachments/assets/5cc2525b-689d-4973-a9bc-999e8d5103da)


→ FE 분기처리를 통해 아이디/이메일 인증/닉네임 등 조건에 부합 할 시에만 회원 가입 기능 구현

### **📗관리자 페이지**

<img width="722" alt="20240823_174846" src="https://github.com/user-attachments/assets/29c17759-5dde-4a87-8d9c-d896fef4c0d2">

<img width="541" alt="20240823_174908" src="https://github.com/user-attachments/assets/c71347bd-9148-4f60-9084-b617e56e3217">

![image 5](https://github.com/user-attachments/assets/00ea2004-e9c7-4d10-982f-44129de59ccc)

- 회원 제재 및 신고 받은 컨텐츠 검수 기능
- 특정 신고가 자주 누적되면 우선순위가 높아져 목록 상단에 노출되게 구현
- UserRole이 ‘ADMIN’인 경우에만 접근 가능하게 WebConfig에서 설정.
- 관리자가 올리는 글은 무조건 ‘공지사항’으로 커뮤니티글 목록 중 최상단에 노출.

### 📘**마이페이지**

![4ca6dde7-1ad8-4001-9a86-c9b25a8ea8c0](https://github.com/user-attachments/assets/46cbe06f-8980-4727-b120-0990576cf58f)

![image 6](https://github.com/user-attachments/assets/a92f0be5-607f-4e2d-a145-ae4984d22f63)


- 사용자 정보 수정/탈퇴 기능 구현
    - 정보 수정 시 E-mail 인증 서비스 구현
    - 회원 탈퇴시 TimeStamp형식으로 회원탈퇴시점 기록(삭제 x)
- 내가 찜한 곳 및 마이 코스 목록 조회/삭제

### 📔**코스 만들기**

- 유저가 담아둔 place들의 최단 경로를 구해주는 기능
    - NaverMap API를 활용
- 유저가 찜한 place들은 카테고리 별로 ‘찜 리스트’에 담김
    - 4개 이상의 place들을 조합하면 ‘경로 총 정리’ 창이 완성

![image 7](https://github.com/user-attachments/assets/c61a16f3-bb96-444d-9a04-ff994259b8dd)

→ 찜 하기 기능

![image 8](https://github.com/user-attachments/assets/15763db4-344a-4682-ae5b-e53d93eda7f1)


→ 경로 총 정리 기능

## 📚이슈와 개선 사항

### **📕CORS 정책 관련 오류**

- API 요청 시 발생한 "Access-Control-Allow-Origin" 오류로 인해 CORS(Cross-Origin Resource Sharing) 정책에 위배되어 요청이 차단되었음.

![image 9](https://github.com/user-attachments/assets/b71d55eb-47b0-4b2e-ac80-9ecfec6111d6)


 →  백엔드포트(4040)와 프론트엔드포트(3030)간 명시되지 않은 데이터 연결로 인해 보안 상 발생하는 오류

![image 10](https://github.com/user-attachments/assets/75d4e125-05ba-4260-ba74-11da1fa4751f)


이를 해결하기 위해 백엔드 패키지의 WebSecurityConfig 클래스 내에 corsConfig 메소드 구현

![image 11](https://github.com/user-attachments/assets/9cccf308-5c03-46f0-876b-4a81332a3207)


이후 프론트/백엔드 간 데이터를 주고받는 Controller에 @CrossOrigin을 명시하여 문제 해결

![image 12](https://github.com/user-attachments/assets/f812732e-33e8-47fe-8571-d9d89a559b62)


👍 **성과** 

**백/프론트 간 실제로 연결해보는 작업을 통해 RESTful한 통신 규약의 필요성과 서버 동작의 기본 원리를 습득할 수 있었음.**

### **📙N:M 관계 Mapping 시 MappingTable 활용**

- ERD 설계 중 N:M관계와 관련된 데이터를 ‘List’ 형태로 활용하는 방법을 처음 고려하였으나, Service단에서 받아온 List를 String화 하여 parsing하는데 비효율적임을 느꼈음.

- 또한 ‘place’ table의 경우 업체가 망하거나 상호를 변경하는 경우 해당 placeNo를 가진 모든 요소들을 전체 탐색하면서 일일히 변경해주어야 하는 어려움이 있음.

(직접 구현한 StringParser 사진 첨부 필요)

→ DB단에서 받아온 데이터들을 직접 parsing하기 위해 추가한 메소드 ‘’

복잡하게 생긴 만큼, 데이터 하나하나를 일일히 받아오는데 개선의 필요성을 느낌

**이를 보완하기 위해,**

- Mapping Table을 활용하여 table간 N:M matching을 구현.

![image 13](https://github.com/user-attachments/assets/87a0c0eb-dd0c-4191-8221-8ff615340174)

→ MappingTable을 따로 두어 복잡한 parsing이 필요하지 않을 뿐더러, 삭제/변경 된 place에 대한 분기 처리 또한 편리하게 개선하였음.

👍 **성과** 

**직관적이고 효율적 DB설계를 어떻게 하는지 배울 수 있었음.** 

### **📗**색인(Indexing)을 활용해 조회 효율화

<img width="616" alt="20240823_171809" src="https://github.com/user-attachments/assets/bd8331f1-ebcc-492f-b6b2-00889dfb542f">


- join을 통해 두 table에서 각각 userNo/boardNo를 활용하였음.
- 이외에도 orderBy 절에 있는 칼럼 또한 신고된 시점의 timeStamp에 색인을 생성하였음

- 색인 칼럼 산정 기준
    - **분포도가 낮은 column**
    
    →  다양한 user가 올린 글/댓글은 분포도가 낮다. 즉 100명의 유저가 있다면 이들이 작성한 글/댓글들은 대체적으로 100명이 고루 작성한 글일 확률이 높다.
    
    - **Join의 연결고리로써 활용되는 column**
    
    - **조건절에서 자주 사용되는 column**

👍 **성과** 

**인덱싱 적용 이후 단순 조회 시 2배(0.4초 → 0.2초)의 속도 향상 결과를 얻을 수 있었음.**

### 📘**외부 API Parsing시, 비동기(Async)를 사용한 시간 단축**

- 외부 API를 끌어오는 날씨, placeData 중, placeData는 SpringScheduler를 사용, 매주 월요일마다 동작하게 설정하였음
- 하지만 매주 월요일마다 6000여개의 placeData를 받아오고, placeData중 rating column의 경우 google Api를 활용해 ‘상호명’을 통해 일일히 조회해야함.

![image 14](https://github.com/user-attachments/assets/96ecddd2-68c5-4387-92e0-0d243a812355)

    

  → 간단한 개략도

- 일단 아무런 조치 없이 1. 외부 API호출, 2.상호명을 통한 평점 조회를 진행한 결과 1시간 가량의 시간이 소요

→ 이를 개선하기 위해 Async를 활용하기로 함

👍 **성과** 

**Async를 활용하니, Url에 요청하는 동시에 비동기적으로 rating Column에 데이터를 Insert하는 작업이 수행되었고, 이는 50분→7분으로 시간 효율을 증가시켰음.** 

### 📔인프라 구축과 CI/CD 적용

- AWS EC2를 이용하여 Amazon Linux 2 환경의 서버 구축
- DB 서버는 AWS RDS를 사용하였으며, 보안을 고려하여 운영 서버를 통해 ssh 터널링으로 접속가능하도록 구축

![image 15](https://github.com/user-attachments/assets/1e281e56-e0cf-4d60-a179-ad7213c90d2c)


운영 서버

![image 16](https://github.com/user-attachments/assets/600d3fa0-7358-4f6b-a619-c62f177b87e0)


DB 서버

![image 17](https://github.com/user-attachments/assets/e2a0bc84-a3e3-411e-84d8-a91de6ef2b64)


putty 접속

![image 18](https://github.com/user-attachments/assets/8a405d92-44e0-475c-bcdf-9ca07212dd9f)


서버에서 DB 접속

- Github Webhook을 이용하여 해당 branch에 push하면 젠킨스 파이프라인이 트리거될 수 있도록 설정
- 젠킨스 파이프라인은 각각 백엔드, 프론트엔드로 나누어 관리
- 각각 프로젝트 안에 젠킨스 파일, 도커파일과 프론트에는 추가적으로 nginx 설정 파일을 작성

![image 19](https://github.com/user-attachments/assets/2df05d71-0c3a-4e7d-a2d4-826b5b12d12f)

![image 20](https://github.com/user-attachments/assets/637d26c0-fa0b-4bab-ad85-43108dbc894c)

![image 21](https://github.com/user-attachments/assets/dd9fec1e-a705-407b-8fff-6e512a037524)

![image](https://github.com/user-attachments/assets/7f9408ef-0c4e-44e6-baf2-cf427296ea03)

👍 **성과**

**테스트 진행 시 Github에서 클론하지 않아도 팀원 모두 외부에서 접속하여 결과물을 확인할 수 있게 됨.**
