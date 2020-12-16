package syed.iconfinder.ui.category

import syed.iconfinder.base.BaseRepository
import syed.iconfinder.network.ApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val apiService: ApiService): BaseRepository() {

    suspend fun getCategory(count: Int) = apiService.getCategories(count).asyncExecute()
}