package gdsc.plantory.plant.domain

enum class HistoryType {
    WATER_CHANGE, POT_CHANGE, RECORDING;

    companion object {
        fun byNameIgnoreCaseOrNull(input: String): HistoryType? {
            return entries.firstOrNull { it.name.equals(input, true) }
        }
    }
}
