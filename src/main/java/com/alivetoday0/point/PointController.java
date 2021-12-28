package com.alivetoday0.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/point")
@RequiredArgsConstructor
public class PointController {

  private final PointService pointService;

  @PostMapping("/deposit")
  public long deposit(@RequestBody PointRequestDto requestDto) {
    pointService.deposit(requestDto.getUserId(), requestDto.getPoint());

    return pointService.balance(requestDto.getUserId());
  }

  @Getter
  private static class PointRequestDto {
    private final long userId;
    private final long point;

    public PointRequestDto(long userId, long point) {
      this.userId = userId;
      this.point = point;
    }
  }
}
