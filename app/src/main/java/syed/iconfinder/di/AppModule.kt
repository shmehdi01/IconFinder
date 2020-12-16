package syed.iconfinder.di

import dagger.Module
import dagger.Provides
import syed.iconfinder.network.ApiService
import syed.iconfinder.ui.category.CategoryRepository
import syed.iconfinder.ui.icons.IconRepository
import syed.iconfinder.ui.iconset.IconSetRepository
import syed.iconfinder.ui.search.SearchRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideCategoryRepository(apiService: ApiService) = CategoryRepository(apiService)

    @Singleton
    @Provides
    fun provideIconSetRepository(apiService: ApiService) = IconSetRepository(apiService)

    @Singleton
    @Provides
    fun provideIconRepository(apiService: ApiService) = IconRepository(apiService)

    @Singleton
    @Provides
    fun provideSearchRepository(apiService: ApiService) = SearchRepository(apiService)
}