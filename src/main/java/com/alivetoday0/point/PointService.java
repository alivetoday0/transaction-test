package com.alivetoday0.point;

import com.alivetoday0.exception.NotExistsUserPoint;
import com.alivetoday0.exception.NotWithdrawPoint;
import javax.transaction.Transactional;

public interface PointService {

  long balance(long userId);

  /**
   * 입금
   */
  void deposit(long userId, long point);

  /**
   * 출금
   */
  @Transactional
  void withdraw(long userId, long point) throws NotExistsUserPoint, NotWithdrawPoint;
}
