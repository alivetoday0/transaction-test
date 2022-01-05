package com.alivetoday0.point;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class PointEntity {

  @Id
  private Long userId;

  private long balance;

  private long pointHistoryId;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

  @Transient
  private boolean isFirst;

  @Builder
  PointEntity(Long userId,
              long balance,
              long pointHistoryId,
              LocalDateTime createAt,
              LocalDateTime updateAt,
              boolean isFirst) {
    this.userId = userId;
    this.balance = balance;
    this.pointHistoryId = pointHistoryId;
    this.createAt = createAt;
    this.updateAt = updateAt;
    this.isFirst = isFirst;
  }

  static PointEntity create(long userId,
                            long balance,
                            long pointHistoryId) {
    return PointEntity.builder()
        .userId(userId)
        .balance(balance)
        .pointHistoryId(pointHistoryId)
        .updateAt(LocalDateTime.now())
        .isFirst(false)
        .build();
  }

  static class FirstPointEntity extends PointEntity {
    FirstPointEntity(long userId, long point) {
      super(userId, point, 0, LocalDateTime.now(), LocalDateTime.now(), true);
    }

    PointEntity create() {
      return PointEntity.builder()
          .userId(super.getUserId())
          .balance(super.getBalance())
          .pointHistoryId(super.getPointHistoryId())
          .createAt(super.getCreateAt())
          .updateAt(super.getUpdateAt())
          .isFirst(true)
          .build();
    }
  }
}
