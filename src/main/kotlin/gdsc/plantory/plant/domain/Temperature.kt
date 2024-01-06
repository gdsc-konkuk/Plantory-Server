package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Temperature(
    @Column(name = "require_temp", nullable = false)
    private val require: String,

    @Column(name = "minimum_temp", nullable = false)
    private val minimum: String,
)