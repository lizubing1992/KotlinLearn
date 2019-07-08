package com.lizubing.kotlinlearn.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hazz.kotlinmvp.base.BaseActivity
import com.hazz.kotlinmvp.mvp.model.bean.TabEntity
import com.lizubing.kotlinlearn.R
import com.lizubing.kotlinlearn.showToast
import com.lizubing.kotlinlearn.ui.fragment.DiscoveryFragment
import com.lizubing.kotlinlearn.ui.fragment.HomeFragment
import com.lizubing.kotlinlearn.ui.fragment.HotFragment
import com.lizubing.kotlinlearn.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")

    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(
        R.mipmap.ic_home_normal,
        R.mipmap.ic_discovery_normal,
        R.mipmap.ic_hot_normal,
        R.mipmap.ic_mine_normal
    )
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(
        R.mipmap.ic_home_selected,
        R.mipmap.ic_discovery_selected,
        R.mipmap.ic_hot_selected,
        R.mipmap.ic_mine_selected
    )

    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var homeFragment: HomeFragment? = null

    private var discoveryFragment: DiscoveryFragment? = null

    private var hotFragment: HotFragment? = null

    private var mineFragment: MineFragment? = null

    private var mIndex = 0


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    //切换Fragment
    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when (index) {
            0
            -> homeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[index]).let {
                homeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1
            -> discoveryFragment?.let {
                transaction.show(it)
            } ?: DiscoveryFragment.getInstance(mTitles[index]).let {
                discoveryFragment = it
                transaction.add(R.id.fl_container, it, "discovery")
            }
            2
            -> hotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance(mTitles[index]).let {
                hotFragment = it
                transaction.add(R.id.fl_container, it, "hot")
            }
            3
            -> mineFragment?.let {
                transaction.show(it)
            } ?: MineFragment.getInstance(mTitles[index]).let {
                mineFragment = it
                transaction.add(R.id.fl_container, it, "mine")
            }

            else -> {
            }
        }

        mIndex = index
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (tab_layout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
        discoveryFragment?.let { transaction.hide(it) }
        hotFragment?.let { transaction.hide(it) }
        mineFragment?.let { transaction.hide(it) }

    }

    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }
        //tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序！")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
