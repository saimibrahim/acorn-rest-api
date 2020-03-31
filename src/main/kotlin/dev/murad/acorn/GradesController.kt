package dev.murad.acorn

import auth.Acorn

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders
import java.lang.RuntimeException



@RestController
@RequestMapping("/grades")
class GradesController {

    @PostMapping("/transcript")
    fun transcript(@RequestHeader headers: HttpHeaders): String? {
        val encodedCredentials = headers.getFirst(HttpHeaders.AUTHORIZATION)?.substring(6)
        if (encodedCredentials == null) {
            throw AuthException("Authentication Error")
        }

        val credentials = getCredentials(encodedCredentials)
        val utorid = credentials[0]
        val pass = credentials[1]

        val acorn = Acorn(utorid, pass)

        try {
            acorn.doLogin()
        } catch (e: RuntimeException) {
            throw AuthException("Authentication Error")
        }

        return (acorn.gradeManager.gradeHtml)
    }


}