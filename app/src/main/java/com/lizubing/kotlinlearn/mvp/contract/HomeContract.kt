package com.lizubing.kotlinlearn.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

/**
 * 类的作用
 * @author: lizubing
 */
interface HomeContract {
    interface View : IBaseView {
        /**
         * 数据刷新
         */
        fun setHomeData(homeBean: HomeBean)

        /**
         * 加载更多数据
         */
        fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)


        /**
         * 显示错误信息
         */
        fun showError(msg: String, errorCode: Int)

    }

    interface Presenter {

        fun requestHomeData(num: Int)

        fun loadMoreData()

    }
}