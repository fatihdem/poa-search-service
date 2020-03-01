package nl.rabobank.service.poa.search.config

import io.swagger.client.ApiClient
import io.swagger.client.api.AccountApi
import io.swagger.client.api.CreditCardApi
import io.swagger.client.api.DebitCardApi
import io.swagger.client.api.PowerOfAttorneyApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class ServiceConfig {
    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun accountApi(): AccountApi = AccountApi(buildSyncHttpClient(environment, "adapter.account"))

    @Bean
    fun creditCardApi(): CreditCardApi = CreditCardApi(buildSyncHttpClient(environment, "adapter.creditcard"))

    @Bean
    fun debitCardApi(): DebitCardApi = DebitCardApi(buildSyncHttpClient(environment, "adapter.debitcard"))

    @Bean
    fun powerOfAttorneyApi(): PowerOfAttorneyApi = PowerOfAttorneyApi(buildSyncHttpClient(environment, "adapter.poa"))


    private fun buildSyncHttpClient(environment: Environment, propPrefix: String): ApiClient {
        val client = ApiClient()
        client.basePath = environment.getProperty("$propPrefix.baseUrl", String::class.java)
        client.connectTimeout = environment.getProperty("$propPrefix.connectionTimeout", Int::class.java, 2000)
        return client
    }
}