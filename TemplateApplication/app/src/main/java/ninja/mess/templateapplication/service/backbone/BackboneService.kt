package ninja.mess.templateapplication.service.backbone

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BackboneService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return BackboneBinder(Backbone())
    }

    inner class BackboneBinder(val backbone: Backbone) : Binder()
}

