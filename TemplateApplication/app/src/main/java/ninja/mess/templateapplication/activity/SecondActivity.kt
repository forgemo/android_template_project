package ninja.mess.templateapplication.activity

import android.os.Bundle
import android.util.Log
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import ninja.mess.templateapplication.R
import ninja.mess.templateapplication.service.backbone.BackboneConnector
import rx.android.schedulers.AndroidSchedulers

class SecondActivity: RxAppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            BackboneConnector.bindToLifecycle(BackboneConnector.BindParams(this), this).subscribe {
                it.subscribe({
                    val backbone = it
                    Log.d("Test", "New Backbone $backbone")

                    backbone.someData.propertyB.bindToLifecycle(textView).observeOn(AndroidSchedulers.mainThread()).subscribe {
                        textView.text = "Some Data: ${it}"
                    }
                    button.setOnClickListener {
                        backbone.someData.propertyB.onNext(backbone.someData.propertyB.value + 1)
                    }
                })
            }

        }
}



