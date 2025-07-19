package biss.ctf.backend.services.login

import biss.ctf.backend.configuration.SignalChartConfiguration
import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.objects.SignalChartResponseData
import biss.ctf.backend.objects.routing.CTFStage
import org.springframework.stereotype.Service
import kotlin.random.Random

typealias SignalGraph = List<Boolean>
typealias Int4Bit = List<Boolean>

@Service

//TODO(100) - doc
class SignalChartPasswordService(private val signalChartConfiguration: SignalChartConfiguration) :
    LoginPasswordService() {
    override val ctfStage: CTFStage = CTFStage.LOGIN_SIGNAL_CHART

    //TODO(100) - think about expiring password
    override fun handlePasswordAttempt(
        passwordAttempt: String,
        user: UserDataEntity
    ): Pair<SignalChartResponseData, Boolean> {
        val passwordOrder = generatePasswordOrder(user.UUID, this.signalChartConfiguration.passwordParts.size)
        val signalChart = generateSignalChart(passwordOrder)

        val signalChartResponseData = SignalChartResponseData(signalChart, this.signalChartConfiguration.passwordParts)

        val isPasswordCorrect = this.validatePassword(passwordAttempt, user.password)

        return Pair(signalChartResponseData, isPasswordCorrect)
    }

    override fun generatePassword(uuid: String): String {
        val passwordParts = this.signalChartConfiguration.passwordParts
        val passwordOrder = generatePasswordOrder(uuid, passwordParts.size)

        return passwordOrder.joinToString("") { passwordParts[it] }
    }

    private fun generatePasswordOrder(seed: String, passwordPartCount: Int): List<Int> =
        (0 until passwordPartCount).shuffled(Random(seed.hashCode()))

    private fun generateSignalChart(passwordOrder: List<Int>): List<SignalGraph> =
        List(4) { bitIndex -> passwordOrder.to4Bit().map { it[bitIndex] } }

    private fun Int.to4Bit(): Int4Bit = (3 downTo 0).map { bit -> (this shr bit) and 1 == 1 }

    private fun List<Int>.to4Bit(): List<Int4Bit> = this.map { it.to4Bit() }

}