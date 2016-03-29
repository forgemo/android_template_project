package ninja.mess.templateapplication.activity

import android.os.Bundle
import android.util.Log
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import ninja.mess.templateapplication.R
import ninja.mess.templateapplication.service.backbone.BackboneConnector
import rx.android.schedulers.AndroidSchedulers

class MainActivity: RxAppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            BackboneConnector.bindToLifecycle(BackboneConnector.BindParams(this),this).subscribe{
                it.subscribe({
                    val backbone = it
                    Log.d("Test", "New Backbone $backbone")
                    it.someData.propertyA

                    backbone.someData.propertyA.bindToLifecycle(textView).observeOn(AndroidSchedulers.mainThread()).subscribe{
                        textView.text = "Some Data: ${it}"
                    }
                    button.setOnClickListener{
                        backbone.someData.propertyA.onNext(backbone.someData.propertyA.value+1)
                    }
                },{
                    Log.d("Test", "Backbone Error $it")
                },{
                    Log.d("Test", "Backbone Disconnected $it")
                })
            }


            lifecycle().subscribe({Log.d("Test", "Success: $it")},{Log.e("Test: ", it.message, it)})
        }
}



