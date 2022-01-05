package com.alivetoday0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class executor {
  private static WebClient webClient = WebClient.builder()
      .baseUrl("http://localhost:8080")
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .build();

  private static Executor executor = Executors.newWorkStealingPool();

  public static void main(String[] args) throws InterruptedException {
    long userId = 100;
    depositPoint(userId, 10000);

    for (int idx = 1, len = 100; idx < len; idx++) {
      executor.execute(() -> withdrawPoint(userId, 20));
    }

    Thread.sleep(1500L);
    log.info("current balance: {}", balance(userId));
  }

  private static void depositPoint(long userId, long point) {
    webClient.post()
        .uri("/v1/point/deposit")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(Body.builder().userId(userId).point(point).build()), Body.class)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  private static void withdrawPoint(long userId, long point) {
    webClient.post()
        .uri("/v1/point/withdraw")
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(Body.builder().userId(userId).point(point).build()), Body.class)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  private static long balance(long userId) {
    return webClient.get()
        .uri("/v1/point/" + userId)
        .retrieve()
        .bodyToMono(Long.class).block().longValue();
  }

  @Getter
  private static class Body {
    private final long userId;
    private final long point;

    @Builder
    @JsonCreator
    public Body(@JsonProperty("userId") long userId,
                @JsonProperty("point") long point) {
      this.userId = userId;
      this.point = point;
    }
  }
}
