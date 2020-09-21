/*
 * Copyright (C) 2018 Haoge https://github.com/yjfnypeu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dyh.common.lib.mvp

import android.app.Activity

/**
 * View层：基础通信协议接口
 * @author haoge on 2018/5/29
 */
interface MVPView {
    fun getHostActivity(): Activity
    fun showLoadingView(massage: String?)
    fun showNetworkErrorView(massage: String?)
    fun showErrorView(massage: String?)
    fun showEmptyView(massage: String?)
    fun hideLoadingView()
    fun toastMessage(message: String)
    fun toastMessage(resId: Int)
}