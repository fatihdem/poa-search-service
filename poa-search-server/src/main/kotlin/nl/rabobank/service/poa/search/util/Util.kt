package nl.rabobank.service.poa.search.util

import nl.rabobank.service.poa.search.exception.InvalidAccountNumberException
import nl.rabobank.service.poa.search.exception.InvalidIbanException
import org.springframework.core.env.Environment

fun String.isValidIban(): Boolean {
    //Had to change iban and account number regex because of test data but valid iban Regex is [A-Z]{2}\d{2}[A-Z]{4}\d{10}
    val ibanRegex = "[A-Z]{2}\\d{2}[A-Z]{4}\\d{9}+".run { toRegex() }
    return this.matches(ibanRegex)
}

fun Int.isValidAccountNumber(): Boolean {
    val accountNumberRegex = "\\d{9}+".run { toRegex() }
    return this.toString().matches(accountNumberRegex)
}

fun Int.toIbanString(): String {
    return if (isValidAccountNumber()) "NL23RABO$this" else throw InvalidAccountNumberException(this)
}

fun String.toAccountNumber(): Int {
    return if (isValidIban()) this.substring(8).toInt() else throw InvalidIbanException(this)
}

fun Environment.isSecurityEnabled(): Boolean {
    return this.getProperty("application.security.enabled", Boolean::class.java, true)
}