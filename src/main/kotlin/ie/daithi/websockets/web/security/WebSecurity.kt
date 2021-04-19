package ie.daithi.websockets.web.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*

@EnableWebSecurity
class WebSecurity(
        @Value("\${auth0.audience}")
        private val audience: String,
        @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
        private val issuer: String,
        @Value("#{'\${cors.whitelist}'.split(',')}")
        private val allowedOrigins: List<String>
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/websocket/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder? {
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }
}
