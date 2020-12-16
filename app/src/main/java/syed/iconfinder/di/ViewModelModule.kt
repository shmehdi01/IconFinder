package syed.iconfinder.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import syed.iconfinder.ui.category.CategoryViewModel
import syed.iconfinder.ui.icons.IconsViewModel
import syed.iconfinder.ui.iconset.IconSetViewModel
import syed.iconfinder.ui.search.SearchViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IconSetViewModel::class)
    abstract fun bindIconSetViewModel(postViewModel: IconSetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IconsViewModel::class)
    abstract fun bindIconViewModel(postViewModel: IconsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(postViewModel: CategoryViewModel): ViewModel

}