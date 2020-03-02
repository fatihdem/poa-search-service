package nl.rabobank.service.poa.search.powerofattorney.model

import io.swagger.client.model.CardType
import io.swagger.client.model.CardReference as ExternalCard

data class Card(
        val id: String,
        val isDebitCard: Boolean
)

fun ExternalCard.internalize(): Card = Card(this.id, this.type == CardType.DEBIT_CARD)