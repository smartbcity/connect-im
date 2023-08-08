package i2.config.api.auth.config

import city.smartb.im.commons.utils.ParserUtils
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

const val FILE = "file:"

class KeycloakConfigParser {

    fun getConfiguration(configPath: String)
        = ParserUtils.getConfiguration(configPath, KeycloakConfigProperties::class.java)

}
