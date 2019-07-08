package com.lizubing.kotlinlearn.ui.fragment

import android.os.Bundle
import com.hazz.kotlinmvp.base.BaseFragment
import com.lizubing.kotlinlearn.R

/**
 * 类的作用
 * @author: lizubing
 */
class MineFragment : BaseFragment(){

    private var title: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}