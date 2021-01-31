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


}
