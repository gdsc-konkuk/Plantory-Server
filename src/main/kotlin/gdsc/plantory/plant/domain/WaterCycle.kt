package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class WaterCycle(
    @Column(name = "water_cycle_spring", nullable = false)
    private var spring: String,

    @Column(name = "water_cycle_summer", nullable = false)
    private var summer: String,

    @Column(name = "water_cycle_autumn", nullable = false)
    private var autumn: String,

    @Column(name = "water_cycle_winter", nullable = false)
    private var winter: String,
)