package syed.iconfinder.ui.iconset

import syed.iconfinder.base.BaseRepository
import syed.iconfinder.network.ApiService
import javax.inject.Inject

class IconSetRepository @Inject constructor(private val apiService: ApiService): BaseRepository() {

    suspend fun loadIconSet(category: String, count: Int, premium: Boolean) = apiService.getIconSetOfCategories(
        category = category,
        count = count,
        premium = premium
    ).asyncExecute()
}