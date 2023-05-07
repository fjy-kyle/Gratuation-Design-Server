package com.example.util

import kotlinx.serialization.Serializable

/**
 * @param errorCode:0 成功
 * @param errorCode:1 出错
 */
@Serializable
data class BaseModel <T> (
    val `data` : T? = null,
    val errorCode: Int = 0,
    val errorMsg: String? = null
)
