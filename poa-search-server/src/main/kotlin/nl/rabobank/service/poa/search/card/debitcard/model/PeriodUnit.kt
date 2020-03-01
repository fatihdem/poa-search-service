package nl.rabobank.service.poa.search.card.debitcard.model

import io.swagger.client.model.PeriodUnit as ExternalPeriodUnit
import nl.rabobank.service.poa.search.detailedpowerofattorney.PeriodUnit as ExposedPeriodUnit

enum class PeriodUnit {
    PER_DAY, PER_WEEK, PER_MONTH
}

fun PeriodUnit.expose(): ExposedPeriodUnit {
    return ExposedPeriodUnit.valueOf(this.name)
}

fun ExternalPeriodUnit.internalize(): PeriodUnit {
    return PeriodUnit.valueOf(this.value)
}
