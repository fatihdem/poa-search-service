package nl.rabobank.service.poa.search.util

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

fun MockMvc.getDetailedPowerOfAttorneyForClient(clientName: String) =
        this.perform(get("/v1/detailed-power-of-attorney?client-name=$clientName"))

