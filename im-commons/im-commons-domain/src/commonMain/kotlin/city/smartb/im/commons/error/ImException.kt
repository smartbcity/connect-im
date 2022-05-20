package city.smartb.im.commons.error

class ImException(
    val error: ImError,
    val from: Throwable? = null
): Exception(error.description, from)

fun ImError.asI2Exception(from: Throwable? = null): ImException = ImException(this, from)
