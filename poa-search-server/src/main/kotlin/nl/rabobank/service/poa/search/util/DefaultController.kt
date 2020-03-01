package nl.rabobank.service.poa.search.util

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore
import java.io.IOException
import javax.servlet.http.HttpServletResponse


@RestController
@ApiIgnore
class DefaultController : ErrorController {

    override fun getErrorPath(): String {
        return "/error"
    }

    @RequestMapping("/error")
    @Throws(IOException::class)
    fun handleErrorWithRedirect(response: HttpServletResponse) {
        response.sendRedirect("/swagger-ui.html")
    }

    @RequestMapping("/")
    @Throws(IOException::class)
    fun redirect(response: HttpServletResponse) {
        response.sendRedirect("/swagger-ui.html")
    }

}