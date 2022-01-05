## 환경 
- Database: h2
- Java: 16
- Spring boot: 2.6.2

## 테스트1
아무런 설정 없이 돌렸을 경우 잘못된 데이터가 들어간다. 

## 테스트2
- 서비스단의 deposit/withdraw 메소드에 isolation 레벨을 SERIALIZABLE 변경
- 잘못된 데이터를 확인

## 테스트3
- repository에서 @Lock(LockModeType.PESSIMISTIC_WRITE) 설정
- 입/출금의 시작은 PointRepository의 조회부터 시작하며
  - 이에 조회 쿼리를 만들고 lock annotation 추가
- 성공!
