package nl.rabobank.service.poa.search.exception

import nl.rabobank.service.poa.search.error.ErrorCodes

class InvalidAccountNumberException(accountNumber: Int) : InternalSearchException("Invalid account number: $accountNumber", ErrorCodes.ACCOUNT_NUMBER_FORMATTING_EXCEPTION)