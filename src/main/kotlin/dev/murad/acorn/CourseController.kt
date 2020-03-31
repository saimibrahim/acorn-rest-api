package dev.murad.acorn

import auth.Acorn
import entity.enrol.EnrolledCourse
import entity.plan.PlannedCourse
import java.lang.RuntimeException
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/courses")
class CourseController {

    @PostMapping("/enrolled")
    fun enrolled(@RequestHeader headers: HttpHeaders): MutableList<EnrolledCourse>? {
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

        return (acorn.courseManager.appliedCourses)
    }

    @PostMapping("/planned")
    fun planned(@RequestHeader headers: HttpHeaders): MutableList<PlannedCourse>? {
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

        return (acorn.courseManager.plannedCourses)
    }
}

