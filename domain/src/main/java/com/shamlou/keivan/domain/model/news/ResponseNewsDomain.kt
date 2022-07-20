package com.shamlou.keivan.domain.model.news

import com.google.gson.annotations.SerializedName
import com.shamlou.keivan.domain.model.news.ArticlesDomain


data class ResponseNewsDomain (

  @SerializedName("status"       ) var status       : String?             = null,
  @SerializedName("totalResults" ) var totalResults : Int?                = null,
  @SerializedName("articles"     ) var articles     : ArrayList<ArticlesDomain> = arrayListOf()

)