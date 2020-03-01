package nl.rabobank.service.poa.search.powerofattorney.model

import io.swagger.client.model.Authorization as ExternalAuthorization
import nl.rabobank.service.poa.search.detailedpowerofattorney.Authorization as ExposedAuthorization

enum class Authorization {
    PAYMENT, VIEW, CREDIT_CARD, DEBIT_CARD
}

fun Authorization.expose(): ExposedAuthorization {
    return ExposedAuthorization.valueOf(this.name)
}

fun ExternalAuthorization.internalize(): Authorization {
    return Authorization.valueOf(this.name)
}