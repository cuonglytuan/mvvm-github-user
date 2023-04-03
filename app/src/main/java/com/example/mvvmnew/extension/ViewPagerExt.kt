package com.example.mvvmnew.extension

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPageSelected(action: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(p: Int, pOffset: Float, pOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            action(position)
        }
    })
}

fun ViewPager.onPageScrolled(action: (Int, Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(p: Int, pOffset: Float, pOffsetPixels: Int) {
            action(p, pOffsetPixels)
        }

        override fun onPageSelected(position: Int) {}
    })
}

fun ViewPager.onPage(action: (Int) -> Unit) {
    val listener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(p: Int, pOffset: Float, pOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            action(position)
        }
    }

    addOnPageChangeListener(listener)
    post { listener.onPageSelected(currentItem) }
}

fun ViewPager.onScrollStateChanged(action: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            action(state)
        }

        override fun onPageScrolled(p: Int, pOffset: Float, pOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {}
    })
}