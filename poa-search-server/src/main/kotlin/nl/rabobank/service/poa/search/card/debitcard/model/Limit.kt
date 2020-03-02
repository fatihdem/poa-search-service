package nl.rabobank.service.poa.search.card.debitcard.model

import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableLimit
import nl.rabobank.service.poa.search.util.Exposable
import io.swagger.client.model.Limit as ExternalLimit
import nl.rabobank.service.poa.search.detailedpowerofattorney.Limit as ExposedLimit

data class Limit(
        val amount: Int,
        val periodUnit: PeriodUnit
) : Exposable<ExposedLimit> {

    constructor(limit: ExternalLimit) : this(
            amount = limit.limit,
            periodUnit = limit.periodUnit.internalize()
    )

    override fun expose(): ExposedLimit = ImmutableLimit.builder()
            .limit(this.amount)
            .periodUnit(this.periodUnit.expose())
            .build()
}

fun ExternalLimit.internalize(): Limit = Limit(this)
