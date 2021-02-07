##### Single, Maybe, COmpleatable

Single
 - 데이터를 1건만 통지하거나 에러 통지,
 - onNext() , onComplete 가 없으며 이를 합친 onSuccess
 - 결국 톰캣이 이거랑 똑같은거네 (C/S 통신 사이)
 - 소비자는 SingleObserver
 - Just 는 무엇인가.. 
 
 
Maybe
 - 데이터를 (0~1) 통지 완료 또는 에러통지
 - 0건 통지할 경우 완료 통지
 - 소비자는 MaybeObserver
 
Completable
 - 데이터 한건도 통지하지 않고, 완료 또는 에러 통지
 - 데이터 통지 대신에 Completable 내에서 특정 작업 수행후, 해당 처리가 끝남 통지
 - Compleatable 그거는 CompletableObserver
 
 
 
 