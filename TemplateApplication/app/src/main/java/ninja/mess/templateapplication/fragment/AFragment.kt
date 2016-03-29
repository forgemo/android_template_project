package ninja.mess.templateapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.fragment_a.*
import ninja.mess.templateapplication.R
import ninja.mess.templateapplication.service.backbone.BackboneConnector

class AFragment : RxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AFragment", "$activity");
        BackboneConnector.bindToLifecycle(BackboneConnector.BindParams(activity),this).subscribe{
            it.subscribe{
                it.someData.propertyA.bindToLifecycle(fragmentTextView).subscribe{
                    fragmentTextView.text="Fragment - Value : $it"
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       return inflater!!.inflate(R.layout.fragment_a, container, false)
    }

}