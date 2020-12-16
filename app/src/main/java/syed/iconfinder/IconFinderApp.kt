package syed.iconfinder

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import syed.iconfinder.di.DaggerAppComponent

/****
 *  CREATED BY: Syed Hussain Mehdi at 11 Dec 2020
 */

class IconFinderApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}