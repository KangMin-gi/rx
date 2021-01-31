### BackPressure

`Flowable`과 `Observable`의 차이중 하나
 - `Flowable`는 배압기능을 제공하고 `Observable`는 배압기능을 제공하지 않는다.
 - `Subscription`으로 전달받는 데이터 개수를 제어할수 있냐 없냐도 여기서 나오는듯 하다.
배압기능
 - `Flowable`에서 데이터를 통지하는 속도가, `Subscriber`에서 통지된 데이터를 전달받아 처리하는 속도 보다 빠를떄 밸런스를 맞춤
 
 
배압 전략
 - 배압을 처리하는 전략을 말한다.
 - MISSING
    + 배압을 정하지 않고 나중에 onBackPressureXXX() 로 배압 적용이 가능하다.
 - Error
    + 그냥 MissingBackPressureException 통지
    + BackpressureKind.UNBOUNDED_IN 라는부분이 보이는데 확인이 필요( `subscribe(Consumer, Consumer, Consumer)`)
    + MissingBackPressure() 에서 보여지는 에러가 해당한다.
 - Buffer 전략 중 DROP_LATEST
    + 말 그대로 버퍼가 넘친 시점에, 마지막으로 통지된 DATA를 제거하고 빈 자리에 통지된 DATA를 입력한다. 
 - Buffer 전략 중 DROP_OLDEST
    + 말 그대로 버퍼가 넘친 시점에, 가장 오래전에 통지된 DATA를 제거하고 모든 데이터를 SHIFT 빈 자리에 통지된 DATA를 입력
    + LATEST와 정 반대, 그치만 빈 자리에 넣을때 SHIFT를 하고 넣게된다. (QUEUE는 유지?)
 - Drop 전략
    + 소비자 쪽에서 처리가 끝나기 전에, 통지된 데이터는 Drop 된다. 
    + Buffer 개념이 아니네..
    + 그래서 Buffer 전략 안에 들어가는게 아니다? 근데 왜 설명을 버퍼로 했을까
    + 버퍼에 데이터가 모두 채워진 상태 이후에 생성되는 데이터를 버린다.
    + 버퍼가 비워지는 시점에 Drop 되지 않은 데이터부터 다시 버퍼에 담는다.
    + LATEST 와 차이점은..? Ok.      
 - Latest 전략
    + 버퍼에 데이턱 ㅏ모두 채워진 상태가 되면, 버퍼가 비워질 떄까지 통지된 데이터는 버퍼 밖에서 대기하며, 버퍼가 비워지는 시점에 가장 나중에 통지된 데이터부터 버퍼에 담는다
    + 일단 느낌 자체는 Drop 에서 `가장 나중에 통지된` 놈만 살리는 느낌 --> 얼추 맞는듯
    + 드랍 전략과 반대로, 가장 나중에 통지된 데이터를 다시 소비자쪽에 통지를 한다.
    
    
    
    
    
    
    
    
    
    
=================    
    
###### ETC
 Drop 과 Latest 전략은 소비하는 데이터가 늘어났을때의 처리가 궁금하긴 하다. 
  - Latest 는 소비 데이터 개수가 많아지면 마지막 N개가 저장이 될거같긴 하다.
  - Drop 은 그냥 다 버리는건데, 버퍼라는 말이 나올 필요가 있는건지..? 잘 모르겠다 
  - 데이터 요청(소비)하는 개수가 늘어날때 마다 통지 하는게 한번에인지 아닌지.. 설정할수 있을려나
  - 39:44 였다
  - 인프런 `RXJava Kevin`님 강의. 
    