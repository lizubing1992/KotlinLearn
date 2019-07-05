package com.lizubing.kotlinlearn

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.hazz.kotlinmvp.utils.DisplayManager
import com.orhanobut.logger.PrettyFormatStrategy
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * 类的作用
 * @author: lizubing
 */
class MyApplication : Application() {

    private var refWatcher: RefWatcher? = null

    companion object {
        private var TAG = "MyApplication"
        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as MyApplication
            return myApplication.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallBacks)

    }

    private fun initConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(7)
            .tag("kotlin")
            .build()

    }

    private val mActivityLifecycleCallBacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

        override fun onActivityPaused(activity: Activity?) {
        }

    }


}