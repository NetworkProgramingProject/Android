package com.example.socketprogramming.ui.mypage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.socketprogramming.ui.buy.BuyFragment
import com.example.socketprogramming.ui.sell.SellFragment
import timber.log.Timber

class ViewPagerAdapter(fragmentManager: FragmentManager, items: List<String>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mItems = mutableListOf<String>()

    init {
        mItems.addAll(items)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BuyFragment()
            else -> SellFragment()
        }
    }

    override fun getCount() : Int {
        return mItems.size
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setItems(items: List<String>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }
}