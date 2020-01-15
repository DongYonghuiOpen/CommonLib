package com.dyh.common.lib.mvp.impl

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import com.dyh.common.lib.easy.EasyToast
import com.dyh.common.lib.mvp.MVPView
import com.dyh.common.lib.safe.SafeDialogHandle

/**
 * 为了使Activity与其绑定的所有Fragment均享有同样的基础展示逻辑。
 * 故在此提供此基础实现类。自动绑定给使用的Fragment/Activity进行使用
 * @author haoge on 2018/8/31
 */
class MVPViewImpl(private val activity:Activity):MVPView {
    // 加载中的提示Dialog
    @Suppress("DEPRECATION")
    private val progressDialog: Dialog by lazy {
        val dialog = ProgressDialog(activity)
        dialog.setMessage("加载中...")
        return@lazy dialog
    }
    private val toast = EasyToast.DEFAULT

    // 基础实现。
    override fun getHostActivity() = activity
    override fun showLoadingDialog() = SafeDialogHandle.safeShowDialog(progressDialog)
    override fun hideLoadingDialog() = SafeDialogHandle.safeDismissDialog(progressDialog)
    override fun toastMessage(message: String) = toast.show(message)
    override fun toastMessage(resId: Int) = toast.show(resId)
}