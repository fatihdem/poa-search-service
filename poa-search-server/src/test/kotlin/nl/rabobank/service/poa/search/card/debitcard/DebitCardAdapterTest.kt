package nl.rabobank.service.poa.search.card.debitcard

import com.nhaarman.mockito_kotlin.whenever
import io.swagger.client.api.DebitCardApi
import nl.rabobank.service.poa.search.card.common.CardStatus
import nl.rabobank.service.poa.search.card.debitcard.model.Limit
import nl.rabobank.service.poa.search.card.debitcard.model.PeriodUnit
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.util.validExternalDebitCard
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DebitCardAdapterTest {
    @Mock
    private lateinit var debitCardApi: DebitCardApi

    @InjectMocks
    private lateinit var debitCardAdapter: DebitCardAdapter

    @Test
    fun `get debit card by card id successfully`() {
        val debitCardId = "2222"
        whenever(debitCardApi.getDebitCardDetail(debitCardId)).thenReturn(validExternalDebitCard())
        val debitCardById = debitCardAdapter.getDebitCardById(debitCardId)
        assertNotNull(debitCardId)
        assertEquals(debitCardId, debitCardById.id)
        //Only the complex objects requires assertion
        assertEquals(CardStatus.ACTIVE, debitCardById.status)
        assertEquals(Limit(100, PeriodUnit.PER_DAY), debitCardById.atmLimit)
        assertEquals(Limit(100, PeriodUnit.PER_DAY), debitCardById.posLimit)
    }



    @Test
    fun `get debit card by card id api fails`() {
        val debitCardId = "2222"
        whenever(debitCardApi.getDebitCardDetail(debitCardId)).thenThrow(RuntimeException())
        val exception = assertThrows(DependencyFailedException::class.java) {
            debitCardAdapter.getDebitCardById(debitCardId)
        }

        assertEquals("Debit Card Service failed while getting Debit Card with id 2222.", exception.message)
    }

}