package com.flowerbun.flowable_and_observable;

import static com.flowerbun.util.DescUtils.printDesc;

public class FlowableVSObservable {

  public static void main(String[] args) {

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
