package nl.rabobank.service.poa.search.util

import java.time.LocalDate
import io.swagger.client.model.Account as ExternalAccount
import io.swagger.client.model.Authorization as ExternalAuthorization
import io.swagger.client.model.CardReference as ExternalCardReference
import io.swagger.client.model.CardType as ExternalCardType
import io.swagger.client.model.CreditCard as ExternalCreditCard
import io.swagger.client.model.DebitCard as ExternalDebitCard
import io.swagger.client.model.Direction as ExternalDirection
import io.swagger.client.model.Limit as ExternalLimit
import io.swagger.client.model.PeriodUnit as ExternalPeriodUnit
import io.swagger.client.model.PowerOfAttorney as ExternalPowerOfAttorney
import io.swagger.client.model.Status as ExternalStatus
import nl.rabobank.service.poa.search.account.model.Account as InternalAccount
import nl.rabobank.service.poa.search.powerofattorney.model.Authorization as InternalAuthorization
import nl.rabobank.service.poa.search.powerofattorney.model.Card as InternalCardReference
import nl.rabobank.service.poa.search.powerofattorney.model.PowerOfAttorney as InternalPowerOfAttorney

fun validExternalAccount(): ExternalAccount = ExternalAccount().id(123).balance(100).created("10-10-2010").owner("Test")

fun validInternalAccount(): InternalAccount = InternalAccount(123456789, "NL23RABO123456789", "Test", 100, LocalDate.of(2010, 10, 10), null)

fun validExternalCreditCard(): ExternalCreditCard = ExternalCreditCard().id("1111").cardNumber(11111).sequenceNumber(1).status(ExternalStatus.ACTIVE).cardHolder("Test").monthlyLimit(1000)

fun validExternalDebitCard(): ExternalDebitCard = ExternalDebitCard().id("2222").cardNumber(22222).sequenceNumber(2).status(ExternalStatus.ACTIVE).cardHolder("Test").atmLimit(ExternalLimit().limit(100).periodUnit(ExternalPeriodUnit.DAY)).posLimit(ExternalLimit().limit(100).periodUnit(ExternalPeriodUnit.DAY)).contactless(true)

fun validExternalPowerOfAttorney(): ExternalPowerOfAttorney = ExternalPowerOfAttorney().id("1").grantor("Grantor").grantee("Grantee").account("NL23RABO123456789").direction(ExternalDirection.GIVEN).authorizations(listOf(ExternalAuthorization.CREDIT_CARD, ExternalAuthorization.DEBIT_CARD, ExternalAuthorization.VIEW)).cards(listOf(ExternalCardReference().type(ExternalCardType.DEBIT_CARD).id("2222"), ExternalCardReference().type(ExternalCardType.CREDIT_CARD).id("1111")))

fun validInternalPowerOfAttorney(): InternalPowerOfAttorney = InternalPowerOfAttorney("1", "Grantor", "Grantee", "NL23RABO123456789", nl.rabobank.service.poa.search.powerofattorney.model.Direction.GIVEN, listOf(InternalAuthorization.DEBIT_CARD, InternalAuthorization.CREDIT_CARD, InternalAuthorization.VIEW), listOf(InternalCardReference("1111", false), InternalCardReference("2222", true)))