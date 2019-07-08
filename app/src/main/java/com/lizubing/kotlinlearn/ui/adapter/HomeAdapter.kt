package com.lizubing.kotlinlearn.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.glide.GlideApp
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.CommonAdapter
import com.lizubing.kotlinlearn.Constants
import com.lizubing.kotlinlearn.R
import com.lizubing.kotlinlearn.durationFormat
import io.reactivex.Observable

/**
 * 类的作用
 * @author: lizubing
 */
class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    var bannerItemSize = 0

    companion object {
        private const val ITEM_TYPE_BANNER = 1//Banner类型
        private const val ITEM_TYPE_TEXT_HRADER = 2 //textHeader
        private const val ITEM_TYPE_CONTENT = 3 //普通的Item
    }

    /**
     * 设置BannerSize
     */
    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    /**
     * 添加更多的数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    /**
     *设置Item类型
     */
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HRADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    /**
     *设置数据类型
     */
    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize ->
                mData.size - bannerItemSize + 1
            mData.isEmpty() ->
                0
            else ->
                1
        }
    }


    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> =
                    mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //获取图片和标题
                Observable.fromIterable(bannerItemData)
                    .subscribe { list ->
                        bannerFeedList.add(list.data?.cover?.feed ?: "")
                        bannerTitleList.add(list.data?.title ?: "")
                    }
                with(holder) {
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        //没有使用到的参数在 kotlin 中用"_"代替
                        setAdapter { banner, _, feedImageUrl, position ->
                            GlideApp.with(mContext)
                                .load(feedImageUrl)
                                .transition(DrawableTransitionOptions().crossFade())
                                .placeholder(R.drawable.placeholder_banner)
                                .into(banner.getItemImageView(position))
                        }
                    }
                }
                holder.getView<BGABanner>(R.id.banner)
                    .setDelegate { _, itemView, _, position ->
                        goToVideoPlayer(mContext as Activity, itemView, bannerItemData[position])

                    }

            }
            ITEM_TYPE_TEXT_HRADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text ?: "")
            }

            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HRADER ->
                ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }

    }

    private fun inflaterView(layoutId: Int, parent: ViewGroup): View {
        val view = mInflater?.inflate(layoutId, parent, false)
        return view ?: View(parent.context)
    }


    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        //加载封面图
        GlideApp.with(mContext)
            .load(cover)
            .placeholder(R.drawable.placeholder_banner)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.getView(R.id.iv_cover_feed))
        if (avatar.isNullOrEmpty()) {
            GlideApp.with(mContext)
                .load(defAvatar)
                .placeholder(R.drawable.placeholder_banner).circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getView(R.id.iv_avatar))
        } else {
            GlideApp.with(mContext)
                .load(avatar)
                .placeholder(R.drawable.placeholder_banner).circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.getView(R.id.iv_avatar))
        }

        holder.setText(R.id.tv_title, itemData?.title ?: "")

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }

        val timeFormat = durationFormat(itemData?.duration)
        tagText += timeFormat
        holder.setText(R.id.tv_tag, tagText!!)
        holder.setText(R.id.tv_category, "#" + itemData?.category)

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })

    }

    private fun goToVideoPlayer(activity: Activity, itemView: View, item: HomeBean.Issue.Item) {
        /*var intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, item)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(itemView, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair
            )
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }*/

    }

}