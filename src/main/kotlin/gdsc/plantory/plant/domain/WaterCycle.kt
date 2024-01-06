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
)