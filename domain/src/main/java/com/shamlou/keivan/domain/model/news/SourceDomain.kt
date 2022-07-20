package com.shamlou.keivan.domain.model.news

import com.google.gson.annotations.SerializedName


data class SourceDomain (

  @SerializedName("id"   ) var id   : String? = null,
  @SerializedName("name" ) var name : String? = null

)