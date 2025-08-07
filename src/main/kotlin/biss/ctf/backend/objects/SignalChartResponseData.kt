package biss.ctf.backend.objects

import biss.ctf.backend.services.login.SignalGraph

data class SignalChartResponseData(
    val signalGraphs: List<SignalGraph>,
    val passwordParts: List<String>
)
