package com.flowerbun.single;

import com.flowerbun.util.DateUtil;
import com.flowerbun.util.LogType;
import com.flowerbun.util.Logger;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class MaybeRx {

  public static void main(String[] args) throws Exception {
    System.out.println("===============");
    maybeIs();
    System.out.println("===============");
    maybeJust();
    System.out.println("===============");
    maybeEmpty();
    System.out.println("===============");
    maybeFromSingle();
  }

  public static void maybeIs() throws Exception {
    Maybe<String> maybe = Maybe.create(emitter -> {
          emitter.onSuccess(DateUtil.getNowDate());
          emitter.onComplete();
        }
    );
    maybe.subscribe(
        s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
        err-> Logger.log(LogType.ON_ERROR, err),
        () -> Logger.log(LogType.ON_COMPLETE)
    );
  }

  public static void maybeJust() throws Exception {
    Maybe.just(DateUtil.getNowDate())
        .subscribe(
            s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
            err-> Logger.log(LogType.ON_ERROR, err),
            () -> Logger.log(LogType.ON_COMPLETE)
        );
  }

  public static void maybeEmpty() throws Exception {
    Maybe.empty()
        .subscribe(
            s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
            err-> Logger.log(LogType.ON_ERROR, err),
            () -> Logger.log(LogType.ON_COMPLETE)
        );
  }

  public static void maybeFromSingle() {
    Single<String> single = Single.just(DateUtil.getNowDate());
    Maybe.fromSingle(single)
        .subscribe(
            s -> Logger.log(LogType.ON_SUCCESS, "날짜 시각: " + s),
            err-> Logger.log(LogType.ON_ERROR, err),
            () -> Logger.log(LogType.ON_COMPLETE)
        );
  }
}
