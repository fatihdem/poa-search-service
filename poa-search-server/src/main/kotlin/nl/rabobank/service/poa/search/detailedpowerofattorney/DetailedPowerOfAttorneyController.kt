package nl.rabobank.service.poa.search.detailedpowerofattorney

import nl.rabobank.service.poa.search.detailedpowerofattorney.model.DetailedPowerOfAttorneys
import nl.rabobank.service.poa.search.endpoint.DetailedPowerOfAttorneyController
import nl.rabobank.service.poa.search.exception.UnauthorizedResourceException
import nl.rabobank.service.poa.search.util.isSecurityEnabled
import org.springframework.core.env.Environment
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorneys as ExternalDetailedPowerOfAttorneys
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorney as ExternalDetailedPowerOfAttorney
import nl.rabobank.service.poa.search.detailedpowerofattorney.model.DetailedPowerOfAttorney

@RestController
class DetailedPowerOfAttorneyController(
        private val detailedPowerOfAttorneyService: DetailedPowerOfAttorneyService,
        private val environment: Environment) : DetailedPowerOfAttorneyController {

    override fun getDetailedPowerOfAttorneyForClient(clientName: String): ExternalDetailedPowerOfAttorneys {
        validateAuthorization(clientName = clientName)

        return DetailedPowerOfAttorneys(detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)).expose()
    }

    override fun getDetailedPowerOfAttorneyByPowerOfAttorneyId(powerOfAttorneyId: String): ExternalDetailedPowerOfAttorney {
        val detailedPowerOfAttorney = detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByPowerOfAttorneyId(powerOfAttorneyId)

        validateAuthorization(detailedPowerOfAttorney)

        return detailedPowerOfAttorney.expose()
    }

    private fun validateAuthorization(clientName: String) {
        if (environment.isSecurityEnabled() &&
                clientName != SecurityContextHolder.getContext().authentication.name)
            throw UnauthorizedResourceException(SecurityContextHolder.getContext().authentication.name, clientName)
    }

    private fun validateAuthorization(detailedPowerOfAttorney: DetailedPowerOfAttorney) {
        if (environment.isSecurityEnabled() &&
                detailedPowerOfAttorney.grantee != SecurityContextHolder.getContext().authentication.name
                && detailedPowerOfAttorney.grantor != SecurityContextHolder.getContext().authentication.name)
            throw UnauthorizedResourceException(SecurityContextHolder.getContext().authentication.name, detailedPowerOfAttorney.grantor)
    }
}