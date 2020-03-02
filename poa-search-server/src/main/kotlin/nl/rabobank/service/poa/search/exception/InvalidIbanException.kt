package nl.rabobank.service.poa.search.exception

import nl.rabobank.service.poa.search.error.ErrorCodes

class InvalidIbanException(iban: String) : InternalSearchException("Invalid Iban: $iban", ErrorCodes.IBAN_FORMATTING_EXCEPTION)