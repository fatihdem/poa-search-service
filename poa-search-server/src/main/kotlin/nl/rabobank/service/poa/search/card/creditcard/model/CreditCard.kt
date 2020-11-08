package nl.rabobank.service.poa.search.card.creditcard.model

import nl.rabobank.service.poa.search.card.common.CardStatus
import nl.rabobank.service.poa.search.card.common.CardType
import nl.rabobank.service.poa.search.card.common.expose
import nl.rabobank.service.poa.search.card.common.internalize
import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableCard
import nl.rabobank.service.poa.search.util.Exposable
import io.swagger.client.model.CreditCard as ExternalCreditCard
import nl.rabobank.service.poa.search.detailedpowerofattorney.Card as ExposedCreditCard

data class CreditCard(
        val id: String,
        val status: CardStatus,
        val cardNumber: Int,
        val sequenceNumber: Int,
        val cardHolder: String,
        val monthlyLimit: Int
) : Exposable<ExposedCreditCard> {

    constructor(creditCard: ExternalCreditCard) : this(
            id = creditCard.id,
            status = creditCard.status.internalize(),
            cardNumber = creditCard.cardNumber,
            sequenceNumber = creditCard.sequenceNumber,
            cardHolder = creditCard.cardHolder,
            monthlyLimit = creditCard.monthlyLimit
    )

    override fun expose(): ExposedCreditCard = if (this.status == CardStatus.ACTIVE) {
        ImmutableCard.builder()
                .id(this.id)
                .type(CardType.CREDIT_CARD.name)
                .status(this.status.expose())
                .cardNumber(this.cardNumber)
                .sequenceNumber(this.sequenceNumber)
                .cardHolder(this.cardHolder)
                .monthlyLimit(this.monthlyLimit)
                .build()
    } else
        ImmutableCard.builder()
                .id(this.id)
                .type(CardType.CREDIT_CARD.name)
                .status(this.status.expose())
                .build()
    }

}

fun ExternalCreditCard.internalize() = CreditCard(this)