package nl.rabobank.service.poa.search.detailedpowerofattorney.model

import nl.rabobank.service.poa.search.account.model.Account
import nl.rabobank.service.poa.search.card.creditcard.model.CreditCard
import nl.rabobank.service.poa.search.card.debitcard.model.DebitCard
import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableDetailedPowerOfAttorney
import nl.rabobank.service.poa.search.powerofattorney.model.Authorization
import nl.rabobank.service.poa.search.powerofattorney.model.Direction
import nl.rabobank.service.poa.search.powerofattorney.model.expose
import nl.rabobank.service.poa.search.util.Exposable
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorney as ExposedClientPoaDetail

data class DetailedPowerOfAttorney(
        val id: String,
        val grantor: String,
        val grantee: String,
        val direction: Direction,
        val authorizations: List<Authorization>,
        val account: Account,
        val creditCards: List<CreditCard>,
        val debitCards: List<DebitCard>
): Exposable<ExposedClientPoaDetail> {
    override fun expose()= ImmutableDetailedPowerOfAttorney.builder()
            .id(id)
            .grantor(grantor)
            .grantee(grantee)
            .account(account.expose())
            .direction(direction.expose())
            .addAllAuthorizations(authorizations.map{it.expose()})
            .addAllCards((creditCards.map { it.expose() } + debitCards.map { it.expose() }))
            .build()
}