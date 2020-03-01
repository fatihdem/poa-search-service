package nl.rabobank.service.poa.search.powerofattorney

import com.nhaarman.mockito_kotlin.whenever
import nl.rabobank.service.poa.search.util.validInternalPowerOfAttorney
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PowerOfAttorneyServiceTest {

    @Mock
    private lateinit var powerOfAttorneyAdapter: PowerOfAttorneyAdapter

    @InjectMocks
    private lateinit var powerOfAttorneyService: PowerOfAttorneyService

    @Test
    fun `get power of attorney by client name successfully`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()).thenReturn(listOf("1"))
        whenever(powerOfAttorneyAdapter.getPowerOfAttorneyById("1")).thenReturn(validInternalPowerOfAttorney())
        val powerOfAttorneysByClientName = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)
        assertNotNull(powerOfAttorneysByClientName)
        assertEquals(1, powerOfAttorneysByClientName.size)
    }

    @Test
    fun `get power of attorney by client name filter if resource doesn't belong to client`() {
        val clientName = "Different Client"
        whenever(powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()).thenReturn(listOf("1"))
        whenever(powerOfAttorneyAdapter.getPowerOfAttorneyById("1")).thenReturn(validInternalPowerOfAttorney())
        val powerOfAttorneysByClientName = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)
        assertNotNull(powerOfAttorneysByClientName)
        assertEquals(0, powerOfAttorneysByClientName.size)
    }

    @Test
    fun `get power of attorney by client name filter if invalid iban`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()).thenReturn(listOf("1"))
        whenever(powerOfAttorneyAdapter.getPowerOfAttorneyById("1")).thenReturn(validInternalPowerOfAttorney().copy(accountIban = "invalid"))
        val powerOfAttorneysByClientName = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)
        assertNotNull(powerOfAttorneysByClientName)
        assertEquals(0, powerOfAttorneysByClientName.size)
    }

    @Test
    fun `get power of attorney by client name filter if grantee and grantor are same`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()).thenReturn(listOf("1"))
        whenever(powerOfAttorneyAdapter.getPowerOfAttorneyById("1")).thenReturn(validInternalPowerOfAttorney().copy(grantee = "Grantor"))
        val powerOfAttorneysByClientName = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)
        assertNotNull(powerOfAttorneysByClientName)
        assertEquals(0, powerOfAttorneysByClientName.size)
    }

    @Test
    fun `get power of attorney by client name filter if nothing authorized`() {
        val clientName = "Grantor"
        whenever(powerOfAttorneyAdapter.getAllPowerOfAttorneyReferences()).thenReturn(listOf("1"))
        whenever(powerOfAttorneyAdapter.getPowerOfAttorneyById("1")).thenReturn(validInternalPowerOfAttorney().copy(authorizations = listOf()))
        val powerOfAttorneysByClientName = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)
        assertNotNull(powerOfAttorneysByClientName)
        assertEquals(0, powerOfAttorneysByClientName.size)
    }


}