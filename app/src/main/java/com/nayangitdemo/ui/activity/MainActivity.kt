package com.nayangitdemo.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nayangitdemo.R
import com.nayangitdemo.base.BaseActivity
import com.nayangitdemo.callback.IFragmentChangeCallback
import com.nayangitdemo.ui.fragment.PopularGitRepoFragment

class MainActivity : BaseActivity(), IFragmentChangeCallback {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openIssueFragment()
    }

    private fun openIssueFragment() {
        replaceFragment(
            R.id.container,
            PopularGitRepoFragment.newInstance(),
            PopularGitRepoFragment.TAG
        )
    }


    override fun onFragmentChange(fragment: Fragment, tag: String) {
        replaceFragment(R.id.container, fragment, tag, true)
    }
}
