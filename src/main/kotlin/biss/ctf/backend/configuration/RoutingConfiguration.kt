package biss.ctf.backend.configuration

import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.objects.apiObjects.Megama
import jakarta.validation.constraints.AssertTrue
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

typealias CTFRoute = List<CTFStage>

@Configuration
@ConfigurationProperties(prefix = "routing")
@Validated
class RoutingConfiguration {
    var routingPaths: Map<Megama, CTFRoute> = emptyMap()

    @AssertTrue(message = "Every valid CTF route must end with END_OF_CTF!")
    fun doAllRoutesEndCorrectly(): Boolean = routingPaths.values.all { ctfRoute: CTFRoute ->
        ctfRoute.last() == CTFStage.END_OF_CTF
    }

    @AssertTrue(message = "No CTF route can be empty")
    fun areNoRoutesEmpty(): Boolean = routingPaths.values.none { it.isEmpty() }

    @AssertTrue(message = "Every valid CTF route must start with a login level")
    fun doAllRoutesStartWithLogin(): Boolean = routingPaths.values.all { it.first().isLoginStage }
}