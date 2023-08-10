package im.script.gateway.conguration

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
suspend fun retryWithExceptions(
    actionName: String,
    maxRetries: Int = 5,
    retryDelayMillis: Long = 10000,
    logger: Logger,
    action: suspend () -> Unit
): Boolean {
    var success = false
    var attempts = 0
    @Suppress("TooGenericExceptionCaught")
    while (attempts < maxRetries && !success) {
        try {
            logger.info("////////////////////////////////////////////////////")
            logger.info("$actionName (attempt $attempts of ${maxRetries})")
            logger.info("////////////////////////////////////////////////////")
            action()
            success = true
        } catch (ex: Exception) {
            attempts++
            logger.error("$actionName Failed to execute (attempt $attempts of ${maxRetries}). Retrying...", ex)

            if (attempts >= maxRetries) {
                logger.error("$actionName Failed to execute init after ${maxRetries} attempts.")
                break
            }

            delay(retryDelayMillis) // Wait for the specified time before retrying
        }
    }
    return success
}