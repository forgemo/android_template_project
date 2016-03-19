package ninja.mess.templateapplication.service

import ninja.mess.templateapplication.model.MyModel
import rx.subjects.BehaviorSubject

/**
 * Created by elbatya on 19.03.16.
 */

interface IBackbone {

    fun getSomeData(): BehaviorSubject<MyModel>
    fun setSomeData(model: MyModel)

}