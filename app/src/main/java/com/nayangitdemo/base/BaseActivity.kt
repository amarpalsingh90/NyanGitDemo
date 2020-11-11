package com.nayangitdemo.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {
    var manager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }

    fun replaceFragment(
        container: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false
    ) {
        if (addToBackStack) {
            manager.beginTransaction()
                .replace(container, fragment, tag)
                .addToBackStack(tag)
                .commit()
        } else {
            manager.beginTransaction()
                .replace(container, fragment, tag)
                .commit()
        }

    }

    abstract fun getLayoutRes(): Int
}