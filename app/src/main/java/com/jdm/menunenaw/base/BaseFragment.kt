package com.jdm.menunenaw.base

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.jdm.menunenaw.common.progressdialog.IProgressDialog
import com.jdm.menunenaw.common.progressdialog.ProgressDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseFragment : Fragment(), IProgressDialog {
    protected val compositeDisposable = CompositeDisposable()
    private var progressDialog: ProgressDialog? = null
    protected var callBack: OnBackPressedCallback? = null
    protected var backPressedTime: Long = 0


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
        callBack?.remove()
        super.onDetach()
    }

}
