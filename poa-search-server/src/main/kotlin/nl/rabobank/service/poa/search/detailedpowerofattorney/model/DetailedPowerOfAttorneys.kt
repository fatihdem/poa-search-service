package nl.rabobank.service.poa.search.detailedpowerofattorney.model

import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableDetailedPowerOfAttorneys
import nl.rabobank.service.poa.search.util.Exposable
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorneys as ExposedDetailedPowerOfAttorneys

class DetailedPowerOfAttorneys(
        private val detailedPowerOfAttorneys: List<DetailedPowerOfAttorney>
) : Exposable<ExposedDetailedPowerOfAttorneys> {

    override fun expose(): nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorneys =
            ImmutableDetailedPowerOfAttorneys.builder()
                    .addAllDetailedPowerOfAttorneys(this.detailedPowerOfAttorneys.map { it.expose() })
                    .build()
}