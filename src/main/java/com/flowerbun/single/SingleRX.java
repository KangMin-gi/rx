package com.flowerbun.single;

import com.flowerbun.util.DateUtil;
import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import io.reactivex.rxjava3.core.Single;

public class SingleRX {

  public static void main(String[] args) throws Exception {
    singleIs();
  }

  public static void singleIs() throws Exception {
    Single<String> stringSingle = Single.create(emitter -> {
      emitter.onSuccess(DateUtil.getNowDate());
    });

    stringSingle.subscribe(
        s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
        err-> Logger.log(LogType.ON_ERROR, err)
    );
  }

  public static void singleIsWithJust() throws Exception {
    Single.just(DateUtil.getNowDate())
        .subscribe(
            s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
            err-> Logger.log(LogType.ON_ERROR, err)
        );
  }
}
