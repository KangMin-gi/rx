package com.flowerbun.flowable_and_observable;

import static com.flowerbun.util.DescUtils.printDesc;

import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableVSObservable {

  public static void main(String[] args) throws Exception {

  }

  public static void FlowableIs() throws Exception{
    Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
      @Override
      public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
        String[] datas = {"Heello", "RxJava!"};
        for(String data : datas) {
          if(emitter.isCancelled())
            return;
          emitter.onNext(data);
        }
        emitter.onComplete();
      }
    }, BackpressureStrategy.BUFFER);

    flowable.observeOn(Schedulers.computation())
        .subscribe(new Subscriber<String>() {

          private Subscription subscription;
          @Override
          public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscription.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(String data) {
            Logger.log(LogType.ON_NEXT, data);
          }

          @Override
          public void onError(Throwable t) {
            Logger.log(LogType.ON_ERROR, t);
          }

          @Override
          public void onComplete() {
            Logger.log(LogType.ON_COMPLETE);
          }
        });

    Thread.sleep(500L);

  }

  public static void FlowableLambda() throws Exception{
    Flowable<String> flowable = Flowable.create(emitter -> {
      String[] datas = {"Heello", "RxJava!"};
      for(String data : datas) {
        if(emitter.isCancelled())
          return;
        emitter.onNext(data);
      }
      emitter.onComplete();
    }, BackpressureStrategy.BUFFER);

    flowable.observeOn(Schedulers.computation())
        .subscribe(
            s -> Logger.log(LogType.ON_NEXT, s),
            error -> Logger.log(LogType.ON_ERROR, error),
            () -> Logger.log(LogType.ON_COMPLETE)
        );

    Thread.sleep(500L);

  }

  public static void ObservableIs() {
    Observable<String> observable =
        Observable.create(emitter -> {
          String[] datas = {"Heello", "RxJava!"};
          for(String data : datas) {
            if(emitter.isDisposed())
              return;
            emitter.onNext(data);
          }
          emitter.onComplete();
        });

    observable.subscribeOn(Schedulers.computation())
        .subscribe(
            s -> Logger.log(LogType.ON_NEXT, s),
            error -> Logger.log(LogType.ON_ERROR, error),
            () -> Logger.log(LogType.ON_COMPLETE)
        );
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
}
