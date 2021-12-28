package com.alivetoday0.point;

import com.alivetoday0.exception.NotExistsUserPoint;
import javax.transaction.Transactional;

public interface PointService {

  long balance(long userId);

  /**
   * 입금
   */
  @Transactional
  void deposit(long userId, long point);

  /**
   * 출금
   */
  @Transactional
  void withdraw(long userId, long point);
}
