package com.jdm.menunenaw.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.jdm.garam.IProgressDialog
import com.jdm.garam.ProgressDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseFragment : Fragment(), IProgressDialog {
    protected val compositeDisposable = CompositeDisposable()
    private var progressDialog: ProgressDialog? = null
    protected lateinit var callBack: OnBackPressedCallback
    protected var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun showProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = ProgressDialog(requireContext(), "")
        progressDialog?.show()
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun onDestroy() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onDetach() {
        callBack.remove()
        super.onDetach()
    }

}
