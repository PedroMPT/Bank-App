package com.example.sanbox_test_api.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    /**
     * Array os screens
     **/
    private val screens = arrayListOf<MainScreen>()

    /**
     * Screens setter
     * @param screens
     **/
    fun setItems(screens: List<MainScreen>) {
        this.screens.apply {
            clear()
            addAll(screens)
            notifyDataSetChanged()
        }
    }

    /**
     * Screens getter
     **/
    fun getItems(): List<MainScreen> {
        return screens
    }

    /**
     * Get screen by position
     * @param position
     **/
    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

    /**
     * Get screen size
     **/
    override fun getCount(): Int {
        return screens.size
    }

    /**
     * This method will be called for every fragment in the ViewPager
     **/
    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}