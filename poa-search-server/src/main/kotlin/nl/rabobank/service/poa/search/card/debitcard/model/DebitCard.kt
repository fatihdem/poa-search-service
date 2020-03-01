package nl.rabobank.service.poa.search.card.debitcard.model

import nl.rabobank.service.poa.search.card.common.CardStatus
import nl.rabobank.service.poa.search.card.common.CardType
import nl.rabobank.service.poa.search.card.common.expose
import nl.rabobank.service.poa.search.card.common.internalize
import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableCard
import nl.rabobank.service.poa.search.util.Exposable
import io.swagger.client.model.DebitCard as ExternalDebitCard
import nl.rabobank.service.poa.search.detailedpowerofattorney.Card as ExposedDebitCard

data class DebitCard(
        val id: String,
        val status: CardStatus,
        val cardNumber: Int,
        val sequenceNumber: Int,
        val cardHolder: String,
        val atmLimit: Limit,
        val posLimit: Limit,
        val contactless: Boolean
) : Exposable<ExposedDebitCard> {
    constructor(debitCard: ExternalDebitCard) : this(
            id = debitCard.id,
            status = debitCard.status.internalize(),
            cardNumber = debitCard.cardNumber,
            sequenceNumber = debitCard.sequenceNumber,
            cardHolder = debitCard.cardHolder,
            atmLimit = debitCard.atmLimit.internalize(),
            posLimit = debitCard.posLimit.internalize(),
            contactless = debitCard.isContactless
    )

    override fun expose(): ExposedDebitCard = if (this.status == CardStatus.ACTIVE) {
        ImmutableCard.builder()
                .id(this.id)
                .type(CardType.DEBIT_CARD.name)
                .status(this.status.expose())
                .cardNumber(this.cardNumber)
                .sequenceNumber(this.sequenceNumber)
                .cardHolder(this.cardHolder)
                .atmLimit(this.atmLimit.expose())
                .posLimit(this.posLimit.expose())
                .contactless(this.contactless)
                .build()
    } else {
        ImmutableCard.builder()
                .id(this.id)
                .type(CardType.DEBIT_CARD.name)
                .status(this.status.expose())
                .build()
    }
}

fun ExternalDebitCard.internalize() = DebitCard(this)