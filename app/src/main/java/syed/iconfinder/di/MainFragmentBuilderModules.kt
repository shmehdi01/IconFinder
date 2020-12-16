package syed.iconfinder.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import syed.iconfinder.ui.category.CategoryFragment
import syed.iconfinder.ui.icons.IconsFragment
import syed.iconfinder.ui.iconset.IconSetFragment
import syed.iconfinder.ui.search.SearchFragment

@Module
abstract class MainFragmentBuilderModules {

    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeIconSetFragment(): IconSetFragment

    @ContributesAndroidInjector
    abstract fun contributeIconsFragment(): IconsFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}