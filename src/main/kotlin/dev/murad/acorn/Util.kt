
package dev.murad.acorn

import java.util.Base64

fun getCredentials(encodedCredentials: String): List<String> {
    val decoded = Base64.getDecoder().decode(encodedCredentials)
    val decodedString = String(decoded)
    return decodedString.split(":")
}