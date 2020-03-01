package nl.rabobank.service.poa.search.powerofattorney.model

import io.swagger.client.model.Direction as ExternalDirection
import nl.rabobank.service.poa.search.detailedpowerofattorney.Direction as ExposedDirection

enum class Direction {
    GIVEN, RECEIVED
}

fun Direction.expose(): ExposedDirection {
    return ExposedDirection.valueOf(this.name)
}

fun ExternalDirection.internalize(): Direction {
    return Direction.valueOf(this.name)
}