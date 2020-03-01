package nl.rabobank.service.poa.search.powerofattorney

import com.nhaarman.mockito_kotlin.whenever
import io.swagger.client.api.PowerOfAttorneyApi
import io.swagger.client.model.PowerOfAttorneyReference
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.powerofattorney.model.Authorization
import nl.rabobank.service.poa.search.powerofattorney.model.Card
import nl.rabobank.service.poa.search.powerofattorney.model.Direction
import nl.rabobank.service.poa.search.util.validExternalPowerOfAttorney
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PowerOfAttorneyAdapterTest {
    @Mock
    private lateinit var powerOfAttorneyApi: PowerOfAttorneyApi

    @InjectMocks
    private lateinit var powerOfAttorneyAdapter: PowerOfAttorneyAdapter

    @Test
    fun `get all power of attorney ids successfully`() {
        whenever(powerOfAttorneyApi.allPowerOfAttorneys).thenReturn(listOf(PowerOfAttorneyReference().id("1"), PowerOfAttorneyReference().id("2")))
        val allPowerOfAttorneyReferences = powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()
        assertNotNull(allPowerOfAttorneyReferences)
        assertEquals(2, allPowerOfAttorneyReferences.size)
        assertTrue(allPowerOfAttorneyReferences.contains("1"))
        assertTrue(allPowerOfAttorneyReferences.contains("2"))
    }

    @Test
    fun `get all power of attorney ids api fails`() {
        whenever(powerOfAttorneyApi.allPowerOfAttorneys).thenThrow(RuntimeException())

        val exception = assertThrows(DependencyFailedException::class.java) {
            powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()
        }

        assertEquals("Power of Attorneys Service failed while getting all power of attorneys.", exception.message)
    }

    @Test
    fun `get power of attorney by id succesfully`() {
        val powerOfAttorneyId = "1"
        whenever(powerOfAttorneyApi.getPowerOfAttorneyDetail(powerOfAttorneyId)).thenReturn(validExternalPowerOfAttorney())
        val powerOfAttorneyById = powerOfAttorneyAdapter.getPowerOfAttorneyById(powerOfAttorneyId)
        assertNotNull(powerOfAttorneyById)
        assertEquals(powerOfAttorneyId, powerOfAttorneyById.id)
        //Only the complex objects requires assertion
        assertEquals(Direction.GIVEN, powerOfAttorneyById.direction)
        assertEquals(3, powerOfAttorneyById.authorizations.size)
        assertTrue(powerOfAttorneyById.authorizations.contains(Authorization.CREDIT_CARD))
        assertTrue(powerOfAttorneyById.authorizations.contains(Authorization.DEBIT_CARD))
        assertTrue(powerOfAttorneyById.authorizations.contains(Authorization.VIEW))
        assertEquals(2, powerOfAttorneyById.cards.size)
        assertTrue(powerOfAttorneyById.cards.contains(Card("1111", false)))
        assertTrue(powerOfAttorneyById.cards.contains(Card("2222", true)))
    }

    @Test
    fun `get power of attorney by id api fails`() {
        val powerOfAttorneyId = "1"
        whenever(powerOfAttorneyApi.getPowerOfAttorneyDetail(powerOfAttorneyId)).thenThrow(RuntimeException())
        val exception = assertThrows(DependencyFailedException::class.java) {
            powerOfAttorneyAdapter.getPowerOfAttorneyById(powerOfAttorneyId)
        }
        assertEquals("Power of Attorneys Service failed while getting power of atterney with id 1.", exception.message)
    }

}