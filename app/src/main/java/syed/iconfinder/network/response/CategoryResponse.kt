package syed.iconfinder.network.response

import syed.iconfinder.model.Category

data class CategoryResponse(
    val categories: List<Category>
) : BaseResponse()