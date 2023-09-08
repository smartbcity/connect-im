package city.smartb.im.commons.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <T, R> Collection<T>.mapAsyncDeferred(transform: suspend (T) -> R): List<Deferred<R>> = coroutineScope {
    map {
        async {
            transform(it)
        }
    }
}

suspend fun <T, R> Collection<T>.mapAsync(transform: suspend (T) -> R): List<R> = mapAsyncDeferred(transform).awaitAll()
