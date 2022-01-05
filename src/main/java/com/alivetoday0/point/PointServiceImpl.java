package com.alivetoday0.point;

import com.alivetoday0.exception.NotExistsUserPoint;
import com.alivetoday0.exception.NotWithdrawPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;

  @Override
  @Transactional(readOnly = true)
  public long balance(long userId) {
    return pointRepository.findById(userId).map(PointEntity::getBalance).orElse(0L);
  }

  @Override
  @Transactional
  public void deposit(final long userId, final long point) {
    PointEntity existedPoint = pointRepository.findByUserId(userId)
        .orElseGet(() -> new PointEntity.FirstPointEntity(userId, point).create());

    if (existedPoint.isFirst()) {
      PointHistoryEntity newlyPointHistory =
          new PointHistoryEntity.FirstPointHistoryEntity(userId, point).create();
      pointHistoryRepository.save(newlyPointHistory);
      pointRepository.save(existedPoint);
      return;
    }

    PointHistoryEntity newlyPointHistory =
        PointHistoryEntity.deposit(userId, existedPoint.getBalance(),
            point, existedPoint.getPointHistoryId());
    PointHistoryEntity savedPointHistory = pointHistoryRepository.save(newlyPointHistory);

    PointEntity newlyPoint = PointEntity.create(userId, savedPointHistory.getBalance(),
        savedPointHistory.getPointHistoryId());
    pointRepository.save(newlyPoint);
  }



  @Override
  @Transactional
  public void withdraw(long userId, long point) throws NotExistsUserPoint, NotWithdrawPoint {
    PointEntity existedPoint = pointRepository.findByUserId(userId)
        .orElseThrow(() -> new NotExistsUserPoint("저장된 포인트가 없습니다."));

    long balance = existedPoint.getBalance() - point;
    if (0 > balance) {
      throw new NotWithdrawPoint(existedPoint.getBalance(), point);
    }

    PointHistoryEntity newlyPointHistory =
        PointHistoryEntity.withdraw(userId, existedPoint.getBalance(),
            point, existedPoint.getPointHistoryId());
    PointHistoryEntity savedPointHistory = pointHistoryRepository.save(newlyPointHistory);

    PointEntity newlyPoint = PointEntity.create(userId, savedPointHistory.getBalance(),
        savedPointHistory.getPointHistoryId());
    pointRepository.save(newlyPoint);
  }
}
