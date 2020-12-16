package syed.iconfinder.ui.search

import syed.iconfinder.base.BaseRepository
import syed.iconfinder.network.ApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val apiService: ApiService): BaseRepository() {

    suspend fun search(query: String, count: Int, premium: Boolean = false)
            = apiService.search(query, count, premium).asyncExecute()
}
