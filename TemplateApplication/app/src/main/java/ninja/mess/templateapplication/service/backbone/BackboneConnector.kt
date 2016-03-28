package ninja.mess.templateapplication.service.backbone

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.kotlin.bindUntilEvent
import rx.subjects.BehaviorSubject

/**
 * Created by elbatya on 24.03.16.
 */
class BackboneConnector(){

    companion object {
        fun bindToLifecycle(bindParams: BindParams,lifecycleProvider: ActivityLifecycleProvider) : BehaviorSubject<BehaviorSubject<Backbone>>{
            Log.d("BackboneManager", "bindToLifecycle")

            val backboneSubjectStream = BehaviorSubject.create(BehaviorSubject.create<Backbone>())

            lifecycleProvider.lifecycle()
            .bindUntilEvent(lifecycleProvider, ActivityEvent.DESTROY)
            .filter({ it== ActivityEvent.START })
            .subscribe{

                Log.d("BackboneManager", "bindBackbone")
                val intent = Intent(bindParams.bindSource, BackboneService::class.java)
                val serviceConnection = newServiceConnection(backboneSubjectStream)
                bindParams.bindSource.bindService(intent, serviceConnection, bindParams.bindServiceFlags)

                lifecycleProvider.lifecycle()
                .bindUntilEvent(lifecycleProvider, ActivityEvent.DESTROY)
                .first { it== ActivityEvent.STOP }
                .subscribe{
                    Log.d("BackboneManager", "unbindBackbone")
                    backboneSubjectStream.value.onCompleted()
                    bindParams.bindSource.unbindService(serviceConnection)
                }
            }


            lifecycleProvider.lifecycle()
            .filter { it== ActivityEvent.DESTROY }
            .subscribe{
                backboneSubjectStream.onCompleted()
            }

            return backboneSubjectStream
        }

        private fun newServiceConnection(backboneSubjectStream: BehaviorSubject<BehaviorSubject<Backbone>>): ServiceConnection {
            Log.d("BackboneManager", "newServiceConnection")

            return object: ServiceConnection {

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.d("BackboneManager", "onServiceConnected")
                    backboneSubjectStream.onNext(BehaviorSubject.create((service as BackboneService.BackboneBinder).backbone))
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.d("BackboneManager", "onServiceDisconnected")
                }

                protected fun finalize(){
                    Log.d("BackboneManager", "ServiceConnection.finalize()")
                }

            }

        }
    }


    protected fun finalize(){
        Log.d("BackboneManager", "finalize() $this")
    }

    class BindParams(val bindSource: Context, val bindServiceFlags: Int = Context.BIND_AUTO_CREATE)
}

