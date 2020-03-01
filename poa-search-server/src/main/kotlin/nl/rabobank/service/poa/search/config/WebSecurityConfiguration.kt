package nl.rabobank.service.poa.search.config

import nl.rabobank.service.poa.search.util.isSecurityEnabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import java.util.Properties

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var environment: Environment

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        if (environment.isSecurityEnabled()) {
            auth.userDetailsService(inMemoryUserDetailsManager())
        }
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {
        val users = Properties()
        users["Super duper employee"] = passwordEncoder().encode("pass") + ",ROLE_USER,enabled"
        users["Super duper company"] = passwordEncoder().encode("pass") + ",ROLE_USER,enabled"
        users["Fellowship of the ring"] = passwordEncoder().encode("pass") + ",ROLE_USER,enabled"
        users["pass"] = passwordEncoder().encode("pass") + ",ROLE_USER,enabled"
        return InMemoryUserDetailsManager(users)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}