package nl.rabobank.service.poa.search.powerofattorney

import nl.rabobank.service.poa.search.powerofattorney.model.PowerOfAttorney
import nl.rabobank.service.poa.search.util.isValidIban
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class PowerOfAttorneyService(private val powerOfAttorneyAdapter: PowerOfAttorneyAdapter) {
    fun getPowerOfAttorneysByClientName(clientName: String): List<PowerOfAttorney> {
        val allPowerOfAttorneyIds = powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()

        return allPowerOfAttorneyIds.parallelStream().map { powerOfAttorneyAdapter.getPowerOfAttorneyById(it.id) }
                .filter { it.grantee == clientName || it.grantor == clientName }
                .filter { isPowerOfAttorneyValid(it) }
                .toList()
    }

    private fun isPowerOfAttorneyValid(powerOfAttorney: PowerOfAttorney): Boolean {
        return powerOfAttorney.accountIban.isValidIban()
                && powerOfAttorney.grantor != powerOfAttorney.grantee
                && powerOfAttorney.authorizations.isNotEmpty()
    }
}