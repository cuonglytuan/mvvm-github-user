package com.example.mvvmnew.extension

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val manager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.showSoftInput(this, 0)
}

fun View.hapticFeedback(isTick: Boolean = false) {
    val feedback = if (isTick) {
        HapticFeedbackConstants.CLOCK_TICK
    } else {
        HapticFeedbackConstants.CONTEXT_CLICK
    }
    performHapticFeedback(feedback)
}

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.visibleOrGone(visible: () -> Boolean) {
    visibility = if (visible()) View.VISIBLE else View.GONE
}

fun View.switchVisibility() {
    visibility = if (visibility == View.GONE) View.VISIBLE else View.GONE
}

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.onLayoutChanged(
    action:
        (
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) -> Unit
) {
    val view = this
    addOnLayoutChangeListener(
        object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                action(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom)
                view.removeOnLayoutChangeListener(this)
            }
        }
    )
}

fun View.resetFocus() {
    this.isFocusable = false
    this.isFocusableInTouchMode = false
    this.isFocusable = true
    this.isFocusableInTouchMode = true
}