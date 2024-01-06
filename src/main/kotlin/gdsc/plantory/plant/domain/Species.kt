package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Species(
    @Column(name = "name", nullable = false)
    private val name: String,

    @Column(name = "family_name", nullable = false)
    private val familyName: String,
)