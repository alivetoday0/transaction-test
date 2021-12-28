package com.alivetoday0.point;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "point_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PointHistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pointHistoryId;

  private long userId;

  private long balance;

  private long point;

  private long parentPointHistoryId;

  private TransactionType transactionType;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @Builder
  PointHistoryEntity(long userId,
                     long balance,
                     long point,
                     long parentPointHistoryId,
                     TransactionType transactionType,
                     LocalDateTime createAt,
                     LocalDateTime updateAt) {
    this.userId = userId;
    this.balance = balance;
    this.point = point;
    this.parentPointHistoryId = parentPointHistoryId;
    this.transactionType = transactionType;
    this.createAt = createAt;
    this.updateAt = updateAt;
  }

  static PointHistoryEntity deposit(long userId, long postBalance,
                                    long point, long parentPointHistoryId) {
    return PointHistoryEntity.builder()
        .userId(userId)
        .balance(postBalance + point)
        .point(point)
        .parentPointHistoryId(parentPointHistoryId)
        .transactionType(TransactionType.DEPOSIT)
        .createAt(LocalDateTime.now())
        .updateAt(LocalDateTime.now())
        .build();
  }

  static PointHistoryEntity withdraw(long userId, long postBalance,
                                    long point, long parentPointHistoryId) {
    return PointHistoryEntity.builder()
        .userId(userId)
        .balance(postBalance - point)
        .point(point)
        .parentPointHistoryId(parentPointHistoryId)
        .transactionType(TransactionType.WITHDRAW)
        .createAt(LocalDateTime.now())
        .updateAt(LocalDateTime.now())
        .build();
  }

  static class FirstPointHistoryEntity extends PointHistoryEntity {

    FirstPointHistoryEntity(long userId, long point) {
      super(userId, point, point, 0L, TransactionType.DEPOSIT, LocalDateTime.now(), LocalDateTime.now());
    }

    PointHistoryEntity create() {
      return PointHistoryEntity.builder()
          .userId(super.getUserId())
          .balance(super.getBalance())
          .point(super.getPoint())
          .parentPointHistoryId(super.getParentPointHistoryId())
          .transactionType(super.getTransactionType())
          .createAt(super.getCreateAt())
          .updateAt(super.getUpdateAt())
          .build();
    }
  }
}
