package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class WaterCycle(
    @Column(name = "water_cycle_spring", nullable = false)
    private val spring: Int,

    @Column(name = "water_cycle_summer", nullable = false)
    private val summer: Int,

    @Column(name = "water_cycle_autumn", nullable = false)
    private val autumn: Int,

    @Column(name = "water_cycle_winter", nullable = false)
    private val winter: Int,
) {
    init {
        if ((spring <= 0) or (summer <= 0) or (autumn <= 0) or (winter <= 0)) {
            throw IllegalArgumentException("\"waterCycle\"은 양의 정수여야 합니다.")
        }
    }
}