package com.jdm.garam.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jdm.garam.IProgressDialog
import com.jdm.garam.ProgressDialog
import com.jdm.menunenaw.R
import com.jdm.menunenaw.common.appbar.BaseAppBar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class ActivityBase : AppCompatActivity(), IProgressDialog {
    private val compositeDisposable = CompositeDisposable()
    private var progressDialog: ProgressDialog? = null
    private var baseAppBar: BaseAppBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    fun setBaseAppBar(title: String = "") {
        if(supportActionBar == null)
            throw IllegalStateException("Can not found supportActionBar")

        baseAppBar = BaseAppBar(this, supportActionBar)
        baseAppBar?.setUpActionBar()
        setAppBarTitle(title)
    }
    fun setAppBarTitle(title: String) {
        baseAppBar?.setUpActionBar()
        if(!title.isNullOrEmpty()) {
            baseAppBar?.setTitle(title)
        }
    }
    fun setAppBarColor(color: String) {
        baseAppBar?.setBackgroundColor(color)
    }
    fun appBarLeftButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.leftButtonClickListener = callback
    }
    fun appBarRightButtonClicked(callback: (View) -> Unit) {
        baseAppBar?.rightButtonClickListener = callback
    }
    fun setBackKey() {
        baseAppBar?.setLeftButtonDrawable(R.drawable.ic_chevron_left)
        appBarLeftButtonClicked {
            onBackPressed()
        }
    }

    fun addDisposable(vararg disposables: Disposable) {
        compositeDisposable.addAll(*disposables)
    }

    fun showFailToastMessage(message: String = "") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    override fun showProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = ProgressDialog(this, "")
        progressDialog?.show()
    }

    override fun hideProgressDialog() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}
