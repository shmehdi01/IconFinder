package syed.iconfinder.network.response

import syed.iconfinder.model.Icon

data class IconResponse(
    val icons: List<Icon>
) : BaseResponse()