package com.alivetoday0.point;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

  private final PointRepository pointRepository;
  private final PointHistoryRepository pointHistoryRepository;

  @Override
  public long balance(long userId) {
    return pointRepository.findById(userId).map(PointEntity::getBalance).orElse(0L);
  }

  @Override
  public void deposit(final long userId, final long point) {
    PointEntity existedPoint = pointRepository.findById(userId)
        .orElseGet(() -> new PointEntity.FirstPointEntity(userId, point).create());

    if (existedPoint.isFirst()) {
      PointHistoryEntity newlyPointHistory =
          new PointHistoryEntity.FirstPointHistoryEntity(userId, point).create();
      pointHistoryRepository.save(newlyPointHistory);
      pointRepository.save(existedPoint);
      return;
    }

    PointHistoryEntity existPointHistory =
        pointHistoryRepository.getById(existedPoint.getPointHistoryId());
    PointHistoryEntity newlyPointHistory =
        PointHistoryEntity.deposit(userId, existedPoint.getBalance(),
            point, existPointHistory.getPointHistoryId());
    PointHistoryEntity savedPointHistory = pointHistoryRepository.save(newlyPointHistory);

    PointEntity newlyPoint = PointEntity.create(userId, savedPointHistory.getBalance(),
        savedPointHistory.getPointHistoryId());
    pointRepository.save(newlyPoint);
  }



  @Override
  public void withdraw(long userId, long point) {

  }
}
