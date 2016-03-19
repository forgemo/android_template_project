package ninja.mess.templateapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import ninja.mess.templateapplication.model.MyModel
import rx.subjects.BehaviorSubject

class Backbone : Service(), IBackbone {

    // =============================================
    // Boilerplate
    // =============================================

    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    inner class LocalBinder: Binder() {
        fun  getBackbone(): IBackbone {
            return this@Backbone
        }
    }

    // =============================================
    // Backbone Actions
    // =============================================

    var myModelBehaviorSubject = BehaviorSubject.create<MyModel>(MyModel(1,2))

    override fun getSomeData(): BehaviorSubject<MyModel> {
        return myModelBehaviorSubject
    }

    override fun setSomeData(model: MyModel) {
        myModelBehaviorSubject.onNext(model)
    }

}
