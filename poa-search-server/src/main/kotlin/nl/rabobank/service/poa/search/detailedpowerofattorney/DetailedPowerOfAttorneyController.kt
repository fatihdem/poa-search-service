package nl.rabobank.service.poa.search.detailedpowerofattorney

import nl.rabobank.service.poa.search.detailedpowerofattorney.model.DetailedPowerOfAttorneys
import nl.rabobank.service.poa.search.endpoint.DetailedPowerOfAttorneyController
import nl.rabobank.service.poa.search.exception.UnauthorizedResourceException
import nl.rabobank.service.poa.search.util.isSecurityEnabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.support.SecurityContextProvider
import org.springframework.core.env.Environment
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorneys as ExternalDetailedPowerOfAttorneys

@RestController
class DetailedPowerOfAttorneyController(
        private val detailedPowerOfAttorneyService: DetailedPowerOfAttorneyService,
        private val environment: Environment) : DetailedPowerOfAttorneyController {

    override fun getDetailedPowerOfAttorneyForClient(clientName: String): ExternalDetailedPowerOfAttorneys {
        validateAuthorization(clientName)

        return DetailedPowerOfAttorneys(detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)).expose()
    }

    private fun validateAuthorization(clientName: String) {
        if (environment.isSecurityEnabled() &&
                clientName != SecurityContextHolder.getContext().authentication.name)
            throw UnauthorizedResourceException(SecurityContextHolder.getContext().authentication.name, clientName)
    }
}