package syed.iconfinder.ui.icons

import syed.iconfinder.base.BaseRepository
import syed.iconfinder.network.ApiService
import javax.inject.Inject

class IconRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun loadIcons(iconSetId: Int, count: Int) =
        apiService.getIconsList(iconSetId, count).asyncExecute()
}