# 포인트 차감을 통한 트랜잭션 확인

## 기능 
### 입금(deposit)
- 첫 입금이면  
  - point, point_history에 입금 금액을 넣는다.
- point 테이블에서 userId 기준으로 조회
- point_history 테이블에 데이터 등록 
- point 테이블에 데이터 등록

### 출금(withdraw)
- point 테이블에 userId 기준 데이터가 없다면 예외 발생
- 마이너스 금액이 발생하면 예외 발생
- 이 후 과정은 입금과 동일

## 테스트
[테스트](./TransactionTest.md)

## 테이블
### h2 console
- url : http://localhost:8080/h2-console
- jdbc : jdbc:h2:mem:point
### point
| 컬럼명           | 설명 |데이터유형|
|------------------|----|---|
| userId           | 사용자번호 |bigint unsinged|
| balance          | 잔액 |bigint unsigned|
| point_history_id | 포인트 이력 ID |bigint unsigned|
| created_at|생성일자|timestamp|
|modified_at|수정일자|timestamp|
```sql
create table point (
    user_id bigint unsinged not null auto_increment primary key comment '사용자 번호',
    balance bigint unsigned not null comment '잔액',
    point_history_id not null comment '포인트 이력 ID',
    created_at timestamp not null default current_timestamp  comment '생성일자',
    modified_at timestamp not null default current_timestamp on update current_timestamp comment '수정일자'
) comment '포인트'
```
### point_history
| 컬럼명              | 설명           |데이터유형|
|------------------|--------------|---|
| point_history_id | 포인트 이력 ID    |bigint unsinged|
| userId           | 사용자번호        |bigint unsinged|
| balance          | 잔액           |bigint unsigned|
| point            | 입/출금액        |bigint unsigned|
| parent_point_history_id| 전 입/출금 이력 ID | bigint unsigned|
| transaction_type| 트랜잭션 타입      |varchar(16)|
| created_at       | 생성일자         |timestamp|
| modified_at      | 수정일자         |timestamp|
```sql
create table point_history (
point_history_id unsinged not null auto_increment primary key comment '포인트 이력 ID'
user_id bigint unsinged not null comment '사용자 번호',
balance bigint unsigned not null comment '잔액',
point bigint unsigned not null comment '입/출금액',
parent_point_history_id not null comment '전 입/출금 이력 ID',
transaction_type varchar(16) not null comment '트랙잭션 타입',
created_at timestamp not null default current_timestamp  comment '생성일자',
modified_at timestamp not null default current_timestamp on update current_timestamp comment '수정일자'
) comment '포인트 이력'
```
