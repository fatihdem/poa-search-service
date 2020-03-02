package nl.rabobank.service.poa.search.detailedpowerofattorney

import nl.rabobank.service.poa.search.card.common.CardStatus
import nl.rabobank.service.poa.search.card.common.CardType
import nl.rabobank.service.poa.search.util.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class DetailedPowerOfAttorneyExposeTest {

    @Test
    fun `expose valid detailed power of attorney object`() {
        val exposedDetailedPowerOfAttorney = validDetailedPowerOfAttorney().expose()

        assertNotNull(exposedDetailedPowerOfAttorney)
        assertEquals("1", exposedDetailedPowerOfAttorney.id)
        assertEquals("Grantor", exposedDetailedPowerOfAttorney.grantor)
        assertEquals("Grantee", exposedDetailedPowerOfAttorney.grantee)
        assertEquals(Direction.GIVEN, exposedDetailedPowerOfAttorney.direction)
        assertEquals(3, exposedDetailedPowerOfAttorney.authorizations.size)
        assertTrue { exposedDetailedPowerOfAttorney.authorizations.contains(Authorization.CREDIT_CARD) }
        assertTrue { exposedDetailedPowerOfAttorney.authorizations.contains(Authorization.DEBIT_CARD) }
        assertTrue { exposedDetailedPowerOfAttorney.authorizations.contains(Authorization.VIEW) }
        assertNotNull(exposedDetailedPowerOfAttorney.account)
        assertEquals(2, exposedDetailedPowerOfAttorney.cards.size)
        assertTrue(exposedDetailedPowerOfAttorney.cards.stream().anyMatch { it.type == CardType.CREDIT_CARD.name })
        assertTrue(exposedDetailedPowerOfAttorney.cards.stream().anyMatch { it.type == CardType.DEBIT_CARD.name })
    }

    @Test
    fun `expose valid detailed power of attorney object skip expired account`() {
        val exposedDetailedPowerOfAttorney = validDetailedPowerOfAttorney().copy(account = validInternalAccount().copy(ended = LocalDate.of(2011, 10, 10))).expose()

        assertNotNull(exposedDetailedPowerOfAttorney)
        assertNotNull(exposedDetailedPowerOfAttorney.account)
        assertNotNull(exposedDetailedPowerOfAttorney.account.iban)
        assertNotNull(exposedDetailedPowerOfAttorney.account.endedDate)
        assertNull(exposedDetailedPowerOfAttorney.account.balance)
        assertNull(exposedDetailedPowerOfAttorney.account.owner)
        assertNull(exposedDetailedPowerOfAttorney.account.createdDate)
    }

    @Test
    fun `expose valid detailed power of attorney object skip blocked credit card`() {
        val exposedDetailedPowerOfAttorney = validDetailedPowerOfAttorney().copy(creditCards = listOf(validInternalCreditCard().copy(status = CardStatus.BLOCKED))).expose()

        assertNotNull(exposedDetailedPowerOfAttorney)
        assertEquals(2, exposedDetailedPowerOfAttorney.cards.size)
        val creditCard = exposedDetailedPowerOfAttorney.cards.filter { it.type == CardType.CREDIT_CARD.name }[0]
        assertNotNull(creditCard)
        assertNotNull(creditCard.id)
        assertNotNull(creditCard.type)
        assertNotNull(creditCard.status)
        assertNull(creditCard.monthlyLimit)
        assertNull(creditCard.cardHolder)
        assertNull(creditCard.cardNumber)
        assertNull(creditCard.sequenceNumber)
        assertNull(creditCard.atmLimit)
        assertNull(creditCard.posLimit)
        assertNull(creditCard.contactless)
    }

    @Test
    fun `expose valid detailed power of attorney object skip blocked debit card`() {
        val exposedDetailedPowerOfAttorney = validDetailedPowerOfAttorney().copy(debitCards = listOf(validInternalDebitCard().copy(status = CardStatus.BLOCKED))).expose()

        assertNotNull(exposedDetailedPowerOfAttorney)
        assertEquals(2, exposedDetailedPowerOfAttorney.cards.size)
        val debitCard = exposedDetailedPowerOfAttorney.cards.filter { it.type == CardType.DEBIT_CARD.name }[0]
        assertNotNull(debitCard)
        assertNotNull(debitCard.id)
        assertNotNull(debitCard.type)
        assertNotNull(debitCard.status)
        assertNull(debitCard.monthlyLimit)
        assertNull(debitCard.cardHolder)
        assertNull(debitCard.cardNumber)
        assertNull(debitCard.sequenceNumber)
        assertNull(debitCard.atmLimit)
        assertNull(debitCard.posLimit)
        assertNull(debitCard.contactless)
    }
}