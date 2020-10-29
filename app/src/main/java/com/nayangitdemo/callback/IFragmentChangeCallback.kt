package com.nayangitdemo.callback

import androidx.fragment.app.Fragment

interface IFragmentChangeCallback {
    fun onFragmentChange(fragment: Fragment, tag: String)
}