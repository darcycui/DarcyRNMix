package com.darcy.skipads.app


object ReactNativeHelper {
    private var jsBundleFilePath: String? = null
    fun setJsBundleFilePath(path: String) {
        jsBundleFilePath = path
    }

    fun getJsBundleFilePath(): String? {
        return if (jsBundleFilePath?.isNotEmpty() == true) {
            jsBundleFilePath
        } else {
            null
        }
    }
}