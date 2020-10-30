package com.nayangitdemo.di.component


import com.nayangitdemo.di.module.ActivityBindingModule
import com.nayangitdemo.di.module.ApiModule
import com.nayangitdemo.di.module.ViewModelModule
import com.nayangitdemo.di.scope.AppScope
import com.nayangitdemo.GithubApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        ApiModule::class]
)
interface ApplicationComponent : AndroidInjector<GithubApplication> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<GithubApplication>
}