package ninja.mess.templateapplication.model

import rx.subjects.BehaviorSubject

/**
 * Created by elbatya on 19.03.16.
 */
class MyModel(val propertyA: BehaviorSubject<Int> = BehaviorSubject.create(0), val propertyB: BehaviorSubject<Int> =  BehaviorSubject.create(0)){

}