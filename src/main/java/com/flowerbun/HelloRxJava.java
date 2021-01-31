package com.flowerbun;

import static com.flowerbun.util.DescUtils.printDesc;

import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import com.flowerbun.util.TimeUtil;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public class HelloRxJava {

  public static void main(String[] args) throws Exception {
    /* HelloRx */
//    HelloRX();

    /* ColdPub */
//    ColdPub();

    /* HotPub */
//    HotPub();;

    /* BackPressure Error*/
//    MissingBackPressure();

    /* BackPressure 전략. DROP_LATEST*/
//    DropBackpressureBuffer();

    /* BackPressure 전략. DROP_OLDEST*/
//    DropBackpressureBufferOldest();

    /* BackPressure 전략, DROP*/
    BackPressureDrop();

    /* BackPressure 전략, LATEST*/
    BackPressureLatest();
  }

  // 1장 Hello Rx
  public static void HelloRX() {
//    생산자:
//    데이터 통지하는 놈;
//    소비자:
//    데이터 소비하는 놈;
    printDesc("HelloRx", "통지 그리고 소비");

    Observable<String> observable = Observable.just("Hello", "Rx", "Java");
    observable.subscribe(System.out::println);
  }

  public static void ColdPub() {
    printDesc("ColdPub 콜드퍼블리셔", "구독 시점과 상관 없이 발행된 데이터 전체를 통지받는다.");

//    Cold Publisher : 구독 시점에 상관없이 처음부터 끝까지 데이터를 통지 , Observer 와 Flowable ?

    Flowable<Integer> flowable = Flowable.just(1, 3, 5, 7, 10, 11, 12, 13, 14, 15);
    flowable.subscribe(v -> System.out.println("구독자 1 -> " + v));
    flowable.subscribe(v -> System.out.println("구독자 2 -> " + v));
  }

  public static void HotPub() {
    printDesc("HotPub 핫 퍼블리셔", "잡지와 같은 느낌, 구독한 시점 이후에 발행된 데이터만 통지받는다.");

    PublishProcessor<Integer> processor = PublishProcessor.create();
    processor.subscribe(v -> System.out.println("구독자 1 -> " + v));
    processor.onNext(1);
    processor.onNext(3);
    processor.onNext(5);

    processor.subscribe(v -> System.out.println("구독자 2 -> " + v));
    processor.onNext(7);
    processor.onNext(9);

    processor.onComplete();

  }


  public static void Flowable_VS_Observable() {
    printDesc("Flowable vs Observable", "좌측이 Flowable , 우측이 Observable");
    System.out.println(
        "Streams 의 인터페이스를 구현 ||| 다른 인터페이스 구현 rxjava3.0에서 독자적 제공"); //rxjava-streams 는 누구꺼? TODO
    System.out
        .println("Subscriber(reactive-streams 기본 스펙) 에서 데이터 처리 ||| Observer(rx 독자적 제공) 에서 데이터 처리");
    System.out.println("데이터 개수 제어하는 배압 기능 있음  ||| 데이터 개수 제어하는 배압기능 없음");
    System.out.println("Subscription으로 전달받는 데이터 개수 제어할 수 있다 ||| 데이터 개수 제어 불가(배압과 관련)");
    System.out.println("Subscription으로 구독 해지 ||| Dispoasable로 구독 해지");

  }

  public static void MissingBackPressure() throws Exception {
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

  public static void DropBackpressureBufferLatest() {
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

  public static void DropBackpressureBufferOldest() {
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

  public static void BackPressureDrop() {
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

  public static void BackPressureLatest() {
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
