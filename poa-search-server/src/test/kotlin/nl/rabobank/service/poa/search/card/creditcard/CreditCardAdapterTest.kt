package nl.rabobank.service.poa.search.card.creditcard

import com.nhaarman.mockito_kotlin.whenever
import io.swagger.client.api.CreditCardApi
import nl.rabobank.service.poa.search.card.common.CardStatus
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.util.validExternalCreditCard
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CreditCardAdapterTest {
    @Mock
    private lateinit var creditCardApi: CreditCardApi

    @InjectMocks
    private lateinit var creditCardAdapter: CreditCardAdapter

    @Test
    fun `get credit card by card id successfully`() {
        val creditCardId = "1111"
        whenever(creditCardApi.getCreditCardDetail(creditCardId)).thenReturn(validExternalCreditCard())
        val creditCardById = creditCardAdapter.getCreditCardById(creditCardId)
        assertNotNull(creditCardById)
        assertEquals(creditCardId, creditCardById.id)
        //Only the complex objects requires assertion
        assertEquals(CardStatus.ACTIVE, creditCardById.status)
    }

    @Test
    fun `get credit card by card id api fails`() {
        val creditCardId = "1111"
        whenever(creditCardApi.getCreditCardDetail(creditCardId)).thenThrow(RuntimeException())
        val exception = assertThrows(DependencyFailedException::class.java) {
            creditCardAdapter.getCreditCardById(creditCardId)
        }

        assertEquals("Credit Card Service failed while getting Credit Card with id 1111.", exception.message)
    }

}