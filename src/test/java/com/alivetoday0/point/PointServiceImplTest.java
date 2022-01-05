package com.alivetoday0.point;

import com.alivetoday0.exception.NotExistsUserPoint;
import com.alivetoday0.exception.NotWithdrawPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PointServiceImplTest {

  @Autowired
  private PointRepository pointRepository;

  @Autowired
  private PointHistoryRepository pointHistoryRepository;

  private PointServiceImpl pointService;

  @BeforeEach
  void setup() {
    pointService = new PointServiceImpl(pointRepository, pointHistoryRepository);
  }

  @Test
  @DisplayName("입금 테스트")
  void deposit() {
    final long userId = 100L;
    pointService.deposit(userId, 100);
    Assertions.assertEquals(100L, pointService.balance(userId));

    pointService.deposit(userId, 100);
    pointService.deposit(userId, 100);
    pointService.deposit(userId, 100);
    pointService.deposit(userId, 100);
    pointService.deposit(userId, 100);
    pointService.deposit(userId, 100);
    Assertions.assertEquals(700L, pointService.balance(userId));

  }



  @Nested
  @DisplayName("포인트 출금 시")
  class DescribePoint {

    @Nested
    @DisplayName("올바르게 동작하면")
    class ContextWithdraw {

      @Test
      @DisplayName("잘 출금된다.")
      void withdraw() throws NotExistsUserPoint, NotWithdrawPoint {
        final long userId = 100L;
        pointService.deposit(userId, 1000L);

        pointService.withdraw(userId, 100);
        pointService.withdraw(userId, 100);
        pointService.withdraw(userId, 100);
        pointService.withdraw(userId, 100);
        pointService.withdraw(userId, 100);
        pointService.withdraw(userId, 100);
        Assertions.assertEquals(400L, pointService.balance(userId));
      }
    }

    @Nested
    @DisplayName("사용자 포인트가 없을 경우")
    class ContextWithdrawDoNotHavePoint {

      @Test
      @DisplayName("예외를 내보낸다.")
      void withdraw() {
        final long userId = 100L;
        Assertions.assertThrows(NotExistsUserPoint.class,
            () -> pointService.withdraw(userId, 100));
      }
    }

    @Nested
    @DisplayName("사용자 포인트 차감 시 음수가 되면")
    class ContextWithdrawMinusPoint {

      @Test
      @DisplayName("예외를 내보낸다.")
      void withdraw() throws NotExistsUserPoint, NotWithdrawPoint {
        final long userId = 100L;
        pointService.deposit(userId, 1000L);

        pointService.withdraw(userId, 1000L);;
        Assertions.assertThrows(NotWithdrawPoint.class,
            () -> pointService.withdraw(userId, 1));
      }
    }
  }
}
