package com.lizubing.kotlinlearn.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * 类的作用
 * @author: lizubing
 */

class HomeModel {

    fun requestHomeData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}