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

/**
 * Service responsible for the password chart password login "game" logic.
 */
class SignalChartPasswordService(private val signalChartConfiguration: SignalChartConfiguration) :
    LoginPasswordService() {
    override val ctfStage: CTFStage = CTFStage.LOGIN_SIGNAL_CHART

    //TODO - think about expiring password

    /**
     * On an entered password attempt:
     * A new password order (and with it a corresponding password) is generated.
     * A new signal chart is generated for that password order.
     * The inputted password is checked
     * A response is built, with the generated signal chart, the password parts (from the configurations) and
     *
     * @param passwordAttempt The password attempt by the user
     * @param user The user that attempted the password
     *
     * @return The response containing the signal chart data and password parts
     */
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

    /**
     * Generates a password by shuffling the password parts according to the password order.
     */
    override fun generatePassword(uuid: String): String {
        val passwordParts = this.signalChartConfiguration.passwordParts
        val passwordOrder = generatePasswordOrder(uuid, passwordParts.size)

        return passwordOrder.joinToString("") { passwordParts[it] }
    }

    /**
     * Deterministically generates a password order by the inputted seed
     *
     * @param seed The seed with which to deterministically generate the order
     * @param passwordPartCount The amount of parts (to be shuffled) in the password
     *
     * @return A shuffled list of the numbers 0 to [passwordPartCount]
     */
    private fun generatePasswordOrder(seed: String, passwordPartCount: Int): List<Int> =
        (0 until passwordPartCount).shuffled(Random(seed.hashCode()))

    /**
     * Generate a signal chart that corresponds to a password order.
     * This generates 4 signal graphs, which are meant to be displayed vertically, one on top of the other.
     *
     * They possess the following property:
     * If we were to split the entire chart (all 4 graphs stacked) into columns for each signal (or clock length),
     * we could read each column from top to bottom as 4-bit numbers.
     * In each graph configuration, all the available 4-bit numbers (0-15) exist, in some order (what we refer to as password order).
     *
     * @param passwordOrder The resulting order of the signal chart.
     *
     * @return A signal chart (represented as a list of [SignalGraph])
     */
    private fun generateSignalChart(passwordOrder: List<Int>): List<SignalGraph> =
        List(4) { bitIndex -> passwordOrder.to4Bit().map { it[bitIndex] } }

    /**
     * Represents an [Int] as an [Int4Bit].
     */
    private fun Int.to4Bit(): Int4Bit = (3 downTo 0).map { bit -> (this shr bit) and 1 == 1 }

    /**
     * Takes a list of [Int], and returns a [Int4Bit] representation of all the numbers in the list.
     */
    private fun List<Int>.to4Bit(): List<Int4Bit> = this.map { it.to4Bit() }

}