package nl.rabobank.service.poa.search.card.common

import io.swagger.client.model.Status as ExternalStatus
import nl.rabobank.service.poa.search.detailedpowerofattorney.Status as ExposedStatus

enum class CardStatus {
    ACTIVE, BLOCKED;
}

fun ExternalStatus.internalize(): CardStatus {
    return CardStatus.valueOf(this.value)
}

fun CardStatus.expose(): ExposedStatus {
    return ExposedStatus.valueOf(this.name)
}

