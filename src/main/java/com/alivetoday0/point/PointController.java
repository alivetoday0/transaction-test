package com.alivetoday0.point;

import com.alivetoday0.exception.NotExistsUserPoint;
import com.alivetoday0.exception.NotWithdrawPoint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/point")
@RequiredArgsConstructor
public class PointController {

  private final PointService pointService;

  @GetMapping("/{userId}")
  public long balance(@PathVariable("userId") long userId) {
    return pointService.balance(userId);
  }

  @PostMapping("/deposit")
  public long deposit(@RequestBody PointRequestDto requestDto) {
    pointService.deposit(requestDto.getUserId(), requestDto.getPoint());

    return pointService.balance(requestDto.getUserId());
  }

  @PostMapping("/withdraw")
  public long withdraw(@RequestBody PointRequestDto requestDto)
      throws NotExistsUserPoint, NotWithdrawPoint {
    pointService.withdraw(requestDto.getUserId(), requestDto.getPoint());

    return pointService.balance(requestDto.getUserId());
  }

  @ExceptionHandler({NotExistsUserPoint.class, NotWithdrawPoint.class})
  @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
  public String exceptionHandler(Exception e) {
    return e.getMessage();
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
