package dev.murad.acorn

import auth.Acorn
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders
import java.lang.RuntimeException

@RestController
@RequestMapping("/registrations")
class RegistrationsController {


    @PostMapping("/eligible")
    fun eligible(@RequestHeader headers: HttpHeaders): MutableList<String>? {
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

        return (acorn.registrationManager.eligibleRegistrations)
    }
}