package syed.iconfinder.network.response

import com.google.gson.annotations.SerializedName
import syed.iconfinder.model.IconSet

data class IconSetResponse(
    @SerializedName("iconsets")
    val iconSets: List<IconSet>
): BaseResponse()