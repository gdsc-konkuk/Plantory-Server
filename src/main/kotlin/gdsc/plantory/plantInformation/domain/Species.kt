package gdsc.plantory.plantInformation.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Species(
    @Column(name = "name", nullable = false)
    private val name: String,

    @Column(name = "family_name", nullable = false)
    private val familyName: String,
) {
    init {
        if (name.isBlank() or familyName.isBlank()) {
            throw IllegalArgumentException("\"species\"는 공백일 수 없습니다.")
        }
    }
}
