package com.alivetoday0.point;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<PointEntity> findByUserId(long userId);
}
