package nl.rabobank.service.poa.search.powerofattorney

import io.swagger.client.api.PowerOfAttorneyApi
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.powerofattorney.model.PowerOfAttorney
import nl.rabobank.service.poa.search.powerofattorney.model.internalize
import org.springframework.stereotype.Component

@Component
class PowerOfAttorneyAdapter(private val powerOfAttorneyApi: PowerOfAttorneyApi) {
    fun getAllPowerOfAttorneyReferences(): List<String> {
        return try {
            powerOfAttorneyApi.allPowerOfAttorneys.map { it.id }
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