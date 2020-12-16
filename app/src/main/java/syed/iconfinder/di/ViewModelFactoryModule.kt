package syed.iconfinder.di


import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import syed.iconfinder.factory.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}