package com.bennyhuo.github.settings

import com.bennyhuo.common.log.logger
import com.bennyhuo.github.AppContext
import com.bennyhuo.github.utils.deviceId

object Configs {

    object Account{
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "Iv1.4d0db35b1b9e9182"
        const val clientSecret = "06a4191b1140e0ce5a86b83c22adfc29ae1d1135"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: "+it) }
        }
    }

}