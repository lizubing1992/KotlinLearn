package com.lizubing.kotlinlearn.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.hazz.kotlinmvp.base.BaseActivity
import com.hazz.kotlinmvp.utils.AppUtils
import com.lizubing.kotlinlearn.MyApplication
import com.lizubing.kotlinlearn.R
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * 欢迎页面
 * @author: lizubing
 */

class SplashActivity : BaseActivity() {
    private var textTypeFace: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")

    }

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
    }

    override fun initView() {
        tv_app_name.typeface = textTypeFace
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "V${AppUtils.getVerName(MyApplication.context)}"
        alphaAnimation = AlphaAnimation(0.2f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                redirectTo()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        checkPermission()

    }

    private fun redirectTo() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun checkPermission() {
        var perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "应用需要以下权限，请允许！", 0, *perms)

    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE) &&
                    perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }


    override fun start() {
    }

}
