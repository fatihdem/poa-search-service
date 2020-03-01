package nl.rabobank.service.poa.search.powerofattorney.model

import io.swagger.client.model.PowerOfAttorneyReference as ExternalPowerAttorneyReference

data class PowerOfAttorneyReference (val id: String)

fun ExternalPowerAttorneyReference.internalize() = PowerOfAttorneyReference(this.id)