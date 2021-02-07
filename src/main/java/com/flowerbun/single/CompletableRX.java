package com.flowerbun.single;

import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import com.flowerbun.util.TimeUtil;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CompletableRX {

  public static void main(String[] args) throws Exception {
    completableIs();
  }

  public static void completableIs() {
    Completable completable = Completable.create(emitter -> {
      int sum = 0;
      for(int i = 0 ; i < 100; ++i ) {
        sum+= i;
      }
      Logger.log(LogType.PRINT, "# 합계 : " + sum);

      emitter.onComplete();
    });

    completable.subscribeOn(Schedulers.computation())
        .subscribe(
            () -> Logger.log(LogType.ON_COMPLETE),
            error -> Logger.log(LogType.ON_ERROR, error)
        );

    TimeUtil.sleep(1000L);

  }

}
