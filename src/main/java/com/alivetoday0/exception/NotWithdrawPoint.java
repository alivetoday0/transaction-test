package com.alivetoday0.exception;

public class NotWithdrawPoint extends Exception {

  public NotWithdrawPoint(long beforeBalance, long requestPoint) {
    super("포인트를 차감할 수 없습니다. 현재 포인트: " + beforeBalance +
        ", 차가 요청 포인트: " + requestPoint);
  }
}
