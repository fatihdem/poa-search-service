package nl.rabobank.service.poa.search.config

import nl.rabobank.service.poa.search.util.isSecurityEnabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*


@Configuration
@EnableSwagger2
class SpringFoxConfig {

    @Autowired
    private lateinit var environment: Environment

    @Bean
    fun actuatorApi(): Docket {
        val docket = Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()

        return if (environment.isSecurityEnabled()) {
            docket.securityContexts(Arrays.asList(actuatorSecurityContext())).securitySchemes(Arrays.asList(basicAuthScheme()))
        } else docket
    }

    private fun actuatorSecurityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(basicAuthReference()))
                .forPaths(PathSelectors.ant("/actuator/**"))
                .build()
    }

    private fun basicAuthScheme(): SecurityScheme {
        return BasicAuth("basicAuth")
    }

    private fun basicAuthReference(): SecurityReference {
        return SecurityReference("basicAuth", arrayOfNulls<AuthorizationScope>(0))
    }
}