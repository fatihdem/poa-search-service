package nl.rabobank.service.poa.search.powerofattorney

import io.swagger.client.api.PowerOfAttorneyApi
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.powerofattorney.model.PowerOfAttorney
import nl.rabobank.service.poa.search.powerofattorney.model.PowerOfAttorneyReference
import nl.rabobank.service.poa.search.powerofattorney.model.internalize
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class PowerOfAttorneyAdapter(private val powerOfAttorneyApi: PowerOfAttorneyApi) {
    private val LOG = LoggerFactory.getLogger(PowerOfAttorneyAdapter::class.java)
    fun getAllPowerOfAttorneyReferences(): List<PowerOfAttorneyReference> {
        return try {
            powerOfAttorneyApi.allPowerOfAttorneys.map { it.internalize() }.toList()
        } catch (e: Exception) {
            throw DependencyFailedException("Power of Attorneys Service failed while getting all power of attorneys.", e)
        }
    }

    fun getPowerOfAttorneyById(id: String): PowerOfAttorney {
        try {
            return powerOfAttorneyApi.getPowerOfAttorneyDetail(id).internalize()
        } catch (e: Exception) {
            throw DependencyFailedException("Power of Attorneys Service failed while getting power of atterney with id $id.", e)
        }
    }
}