package syed.iconfinder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import syed.iconfinder.ui.FullScreenViewActivity
import syed.iconfinder.ui.MainActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeFullScreenViewActivity(): FullScreenViewActivity
}