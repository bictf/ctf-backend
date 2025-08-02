package biss.ctf.backend.services.logicgates

import biss.ctf.backend.objects.logicgates.LogicGateSignal
import org.springframework.stereotype.Service
import kotlin.random.Random

typealias LookUpTable = MutableMap<List<LogicGateSignal>, LogicGateSignal>

@Service
class LogicGatesService {
    // TODO(114) - add configurable LUT sizes
    fun generateLookupTable(seed: Long, inputCount: Int = 3): LookUpTable {
        val randomizer = Random(seed);
        val validSignals = LogicGateSignal.entries.toTypedArray()

        val lookUpTable: LookUpTable = mutableMapOf();

        fun generateLookUpTableCombinations(currentCombination: List<LogicGateSignal>) {
            if (currentCombination.size == inputCount) {
                val outputSignal = validSignals[randomizer.nextInt(validSignals.size)]

                lookUpTable[currentCombination] = outputSignal
                return;
            }

            for (signal in validSignals) {
                generateLookUpTableCombinations(currentCombination + signal)
            }
        }

        generateLookUpTableCombinations(emptyList())
        return lookUpTable
    }


}