package com.flowerbun.backpressure;

import static com.flowerbun.util.DescUtils.printDesc;

import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import com.flowerbun.util.TimeUtil;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class BackPressureStrategyCHAP {

  public static void main(String[] args) throws Exception {

    /* BackPressure Error*/
    backPressureError();

    /* BackPressure 전략. DROP_LATEST*/
    backpressureBufferLatest();

    /* BackPressure 전략. DROP_OLDEST*/
    backpressureBufferOldest();

    /* BackPressure 전략, DROP*/
    backPressureDrop();

    /* BackPressure 전략, LATEST*/
    backPressureLatest();
  }

  public static void backPressureError() throws Exception {
    printDesc("배압 전략중 Error",
        "에러 전략, 아무것도 선언하지 않았을 떄? 3개의 Consumer 입력하는 subscribe 함수-> BackpressureKind.UNBOUNDED_IN 인데 뭔지 확인"); // TODO
    Flowable.interval(1L, TimeUnit.MILLISECONDS)
        .doOnNext(data -> Logger.log(LogType.DO_ON_NEXT, data))
        .observeOn(Schedulers.computation())
        .subscribe(
            data -> {
              Logger.log(LogType.PRINT, "# 소비자 처리 대기 중..");
              TimeUtil.sleep(1000L);
              Logger.log(LogType.ON_NEXT, data);
            },
            error -> Logger.log(LogType.ON_ERROR, error),
            () -> Logger.log(LogType.ON_COMPLETE)
        );

    Thread.sleep(2000L);
  }

  public static void backpressureBufferLatest() {
    printDesc("배압 전략중 DROP_LATEST", "배압 전략, 마지막으로 입력된놈 버리고 그자리에 입력된놈 넣음");

    System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
    Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .doOnNext(data -> Logger.log("#interval doOnNext()", data))
        .onBackpressureBuffer( // DropLatest 전략 구성
            2, // 버퍼 데이터 개수
            () -> Logger.log("overflow!"),
            BackpressureOverflowStrategy.DROP_LATEST
        )
        .doOnNext(data -> Logger.log("#onBackPressureBuffer doOnNext()", data))
        .observeOn(Schedulers.computation(), false, 1) // 처리하는 쓰레드 별도로 생성, bufferSize 는 요청하는 사이즈 개수
        .subscribe(
            data -> {
              TimeUtil.sleep(1000L);
              Logger.log(LogType.ON_NEXT, data);
            },
            error -> Logger.log(LogType.ON_ERROR, error)
        );

    TimeUtil.sleep(4000L);
  }

  public static void backpressureBufferOldest() {
    printDesc("배압 전략중 DROP_LATEST", "배압 전략, 마지막으로 입력된놈 버리고 그자리에 입력된놈 넣음");

    System.out.println("# start : " + TimeUtil.getCurrentTimeFormatted());
    Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .doOnNext(data -> Logger.log("#interval doOnNext()", data))
        .onBackpressureBuffer( // DropLatest 전략 구성
            2, // 버퍼 데이터 개수
            () -> Logger.log("overflow!"),
            BackpressureOverflowStrategy.DROP_OLDEST
        )
        .doOnNext(data -> Logger.log("#onBackPressureBuffer doOnNext()", data))
        .observeOn(Schedulers.computation(), false, 1) // 처리하는 쓰레드 별도로 생성, bufferSize 는 요청하는 사이즈 개수
        .subscribe(
            data -> {
              TimeUtil.sleep(1000L);
              Logger.log(LogType.ON_NEXT, data);
            },
            error -> Logger.log(LogType.ON_ERROR, error)
        );

    TimeUtil.sleep(4000L);
  }

  public static void backPressureDrop() {
    printDesc("배압 전략중 DROP", "처리되기 전에 들어온 친구들 다 버린다...?");

    Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .doOnNext(data -> Logger.log("#interval doOnNext()", data))
        .onBackpressureDrop(dropData -> Logger.log(LogType.PRINT, dropData + " Drop!"))
        .observeOn(Schedulers.computation(), false, 1)
        .subscribe(
            data -> {
              TimeUtil.sleep(1000L);
              Logger.log(LogType.ON_NEXT, data);
            },
            error -> Logger.log(LogType.ON_ERROR, error)
        );

    TimeUtil.sleep(5000L);
  }

  public static void backPressureLatest() {
    printDesc("배압 전략중 Latest", "DROP 과 차이는 가장 나중에 통지된 놈을 살린다는거..");

    Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .doOnNext(data -> Logger.log("#interval doOnNext()", data))
        .onBackpressureLatest()
        .observeOn(Schedulers.computation(), false, 1)
        .subscribe(
            data -> {
              TimeUtil.sleep(1000L);
              Logger.log(LogType.ON_NEXT, data);
            },
            error -> Logger.log(LogType.ON_ERROR, error)
        );

    TimeUtil.sleep(5500L);

  }


}
