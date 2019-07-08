package com.lizubing.kotlinlearn.ui.fragment

import android.os.Bundle
import com.hazz.kotlinmvp.base.BaseFragment
import com.lizubing.kotlinlearn.R

/**
 * 类的作用
 * @author: lizubing
 */
class HotFragment : BaseFragment() {

    private var title: String? = null

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

}