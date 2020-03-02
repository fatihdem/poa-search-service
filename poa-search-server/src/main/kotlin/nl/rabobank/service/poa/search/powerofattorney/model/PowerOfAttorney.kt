package nl.rabobank.service.poa.search.powerofattorney.model

import io.swagger.client.model.PowerOfAttorney as ExternalPowerOfAttorney

data class PowerOfAttorney(
        val id: String,
        val grantor: String,
        val grantee: String,
        val accountIban: String,
        val direction: Direction,
        val authorizations: List<Authorization>,
        val cards: List<Card>
)

fun ExternalPowerOfAttorney.internalize() = PowerOfAttorney(
        id = this.id,
        grantor = this.grantor,
        grantee = this.grantee,
        accountIban = this.account,
        direction = this.direction.internalize(),
        authorizations = this.authorizations.map { it.internalize() }.toList(),
        cards = this.cards?.map { it.internalize() }?.toList() ?: listOf()
)