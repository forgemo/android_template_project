package ninja.mess.templateapplication.service.backbone

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BackboneService : Service() {

    val backbone = Backbone()

    override fun onBind(intent: Intent): IBinder? {
        return BackboneBinder(backbone)
    }

    inner class BackboneBinder(val backbone: Backbone) : Binder()
}

