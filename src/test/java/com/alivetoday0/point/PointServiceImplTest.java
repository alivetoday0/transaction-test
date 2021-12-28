package com.alivetoday0.point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
}
