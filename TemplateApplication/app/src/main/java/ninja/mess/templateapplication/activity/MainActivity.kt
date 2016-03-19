package ninja.mess.templateapplication.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ninja.mess.templateapplication.R
import ninja.mess.templateapplication.model.MyModel
import ninja.mess.templateapplication.service.Backbone
import ninja.mess.templateapplication.service.IBackbone
import rx.functions.Action1
import rx.subjects.BehaviorSubject

class MainActivity: AppCompatActivity() {

    private val backbone = BehaviorSubject.create<IBackbone>()
    private val isBackboneBound = BehaviorSubject.create(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(View.OnClickListener {
            if (backbone.hasValue()) {
                backbone.value.setSomeData(MyModel(backbone.value.getSomeData().value.propertyA+1,4))
            }
        })

        backbone.subscribe(Action1 {
            it.getSomeData().subscribe( Action1 { textView.text = "${it.propertyA} ${Math.random()}"})
        })

    }

    override fun onStart() {
        super.onStart()

        // Bind to the service
        val intent = Intent(applicationContext, Backbone::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (isBackboneBound.value) {
            unbindService(serviceConnection);
            isBackboneBound.onNext(false);
        }
    }


    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as Backbone.LocalBinder
            backbone.value?.getSomeData()?.onCompleted()
            backbone.onNext(localBinder.getBackbone())
            isBackboneBound.onNext(true);
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBackboneBound.onNext(false);
        }
    }
}
