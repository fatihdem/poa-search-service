package nl.rabobank.service.poa.search.detailedpowerofattorney

import com.nhaarman.mockito_kotlin.whenever
import nl.rabobank.service.poa.search.account.AccountService
import nl.rabobank.service.poa.search.card.creditcard.CreditCardService
import nl.rabobank.service.poa.search.card.debitcard.DebitCardService
import nl.rabobank.service.poa.search.error.ErrorCodes
import nl.rabobank.service.poa.search.exception.InvalidIbanException
import nl.rabobank.service.poa.search.powerofattorney.PowerOfAttorneyService
import nl.rabobank.service.poa.search.powerofattorney.model.Authorization
import nl.rabobank.service.poa.search.powerofattorney.model.Direction
import nl.rabobank.service.poa.search.util.validInternalAccount
import nl.rabobank.service.poa.search.util.validInternalCreditCard
import nl.rabobank.service.poa.search.util.validInternalDebitCard
import nl.rabobank.service.poa.search.util.validInternalPowerOfAttorney
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DetailedPowerOfAttorneyServiceTest {

    @Mock
    private lateinit var accountService: AccountService

    @Mock
    private lateinit var creditCardService: CreditCardService

    @Mock
    private lateinit var debitCardService: DebitCardService

    @Mock
    private lateinit var powerOfAttorneyService: PowerOfAttorneyService

    @InjectMocks
    private lateinit var detailedPowerOfAttorneyService: DetailedPowerOfAttorneyService

    @Test
    fun `get detailed power of attorneys for client successfully`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)).thenReturn(listOf(validInternalPowerOfAttorney()))
        whenever(debitCardService.getDebitCardById("2222")).thenReturn(validInternalDebitCard())
        whenever(creditCardService.getCreditCardById("1111")).thenReturn(validInternalCreditCard())
        whenever(accountService.getAccountDetails(123456789)).thenReturn(validInternalAccount())

        val detailedPowerOfAttorneyByName = detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)

        assertNotNull(detailedPowerOfAttorneyByName)
        assertEquals(1, detailedPowerOfAttorneyByName.size)
        assertEquals(clientName, detailedPowerOfAttorneyByName[0].grantor)
        assertEquals(Direction.GIVEN, detailedPowerOfAttorneyByName[0].direction)
        assertEquals(3, detailedPowerOfAttorneyByName[0].authorizations.size)
        assertTrue(detailedPowerOfAttorneyByName[0].authorizations.contains(Authorization.VIEW))
        assertTrue(detailedPowerOfAttorneyByName[0].authorizations.contains(Authorization.CREDIT_CARD))
        assertTrue(detailedPowerOfAttorneyByName[0].authorizations.contains(Authorization.DEBIT_CARD))
        assertNotNull(detailedPowerOfAttorneyByName[0].account)
        assertEquals(1, detailedPowerOfAttorneyByName[0].creditCards.size)
        assertEquals(1, detailedPowerOfAttorneyByName[0].debitCards.size)
    }

    @Test
    fun `get detailed power of attorneys hide cards if missing card authorizations`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)).thenReturn(listOf(validInternalPowerOfAttorney().copy(authorizations = listOf(Authorization.VIEW))))
        whenever(accountService.getAccountDetails(123456789)).thenReturn(validInternalAccount())

        val detailedPowerOfAttorneyByName = detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)

        assertNotNull(detailedPowerOfAttorneyByName)
        assertEquals(1, detailedPowerOfAttorneyByName.size)
        assertEquals(0, detailedPowerOfAttorneyByName[0].creditCards.size)
        assertEquals(0, detailedPowerOfAttorneyByName[0].debitCards.size)
    }

    @Test
    fun `get detailed power of attorneys filter if dependency returns invalid data`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)).thenReturn(listOf(validInternalPowerOfAttorney().copy(accountIban = "NLINVALID")))

        val exception = assertThrows(InvalidIbanException::class.java) {
            detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)
        }

        assertEquals(ErrorCodes.IBAN_FORMATTING_EXCEPTION, exception.errorCode)
        assertEquals("Invalid Iban: NLINVALID", exception.message)
    }

    @Test
    fun `get detailed power of attorneys by id successfully`() {
        val powerOfAttorneyId = "1"
        whenever(powerOfAttorneyService.getPowerOfAttorneysById(powerOfAttorneyId)).thenReturn(validInternalPowerOfAttorney())
        whenever(debitCardService.getDebitCardById("2222")).thenReturn(validInternalDebitCard())
        whenever(creditCardService.getCreditCardById("1111")).thenReturn(validInternalCreditCard())
        whenever(accountService.getAccountDetails(123456789)).thenReturn(validInternalAccount())

        val detailedPowerOfAttorneyById = detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByPowerOfAttorneyId(powerOfAttorneyId)

        assertNotNull(detailedPowerOfAttorneyById)
        assertEquals(powerOfAttorneyId, detailedPowerOfAttorneyById.id)
    }

}