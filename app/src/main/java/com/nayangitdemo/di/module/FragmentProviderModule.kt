package com.nayangitdemo.di.module

import com.nayangitdemo.ui.fragment.RepoDetailFragment
import com.nayangitdemo.ui.fragment.PopularGitRepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentProviderModule {

    @ContributesAndroidInjector
    abstract fun provideIssueFragment(): PopularGitRepoFragment

    @ContributesAndroidInjector
    abstract fun provideCommentFragment(): RepoDetailFragment
}