package com.nayangitdemo.di.module

import com.nayangitdemo.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [FragmentProviderModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

}