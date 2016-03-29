package ninja.mess.templateapplication.activity

import android.os.Bundle
import android.util.Log
import com.trello.rxlifecycle.components.support.RxFragmentActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.two_fragments_activity.*
import ninja.mess.templateapplication.R
import ninja.mess.templateapplication.service.backbone.BackboneConnector
import rx.android.schedulers.AndroidSchedulers

class TwoFragmentsActivity: RxFragmentActivity(){

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.two_fragments_activity)

            BackboneConnector.bindToLifecycle(BackboneConnector.BindParams(this), this).subscribe {
                it.subscribe({
                    val backbone = it
                    Log.d("Test", "New Backbone $backbone")

                    backbone.someData.propertyA.bindToLifecycle(textView).observeOn(AndroidSchedulers.mainThread()).subscribe {
                        textView.text = "Some Data: ${it}"
                    }
                    button.setOnClickListener {
                        backbone.someData.propertyA.onNext(backbone.someData.propertyA.value + 1)
                    }
                })
            }

        }

}




