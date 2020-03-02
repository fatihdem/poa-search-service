package nl.rabobank.service.poa.search.detailedpowerofattorney


import com.nhaarman.mockito_kotlin.whenever
import nl.rabobank.service.poa.search.util.getDetailedPowerOfAttorneyForClient
import nl.rabobank.service.poa.search.util.validDetailedPowerOfAttorney
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [DetailedPowerOfAttorneyController::class, AutoConfigureMockMvc::class])
class DetailedPowerOfAttorneyControllerTest {

    @MockBean
    private lateinit var detailedPowerOfAttorneyService: DetailedPowerOfAttorneyService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithUserDetails("Grantor")
    fun `status okay`() {
        val clientName = "Grantor"
        whenever(detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)).thenReturn(listOf(validDetailedPowerOfAttorney()))

        mockMvc.getDetailedPowerOfAttorneyForClient(clientName).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithUserDetails("Super duper employee")
    fun `status unauthorized`() {
        val clientName = "Grantor"
        whenever(detailedPowerOfAttorneyService.getDetailedPowerOfAttorneyByName(clientName)).thenReturn(listOf(validDetailedPowerOfAttorney()))

        mockMvc.getDetailedPowerOfAttorneyForClient(clientName).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}


