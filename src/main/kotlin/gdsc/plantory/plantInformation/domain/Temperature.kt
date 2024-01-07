package gdsc.plantory.plantInformation.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Temperature(
    @Column(name = "require_temp", nullable = false)
    private val require: String,

    @Column(name = "minimum_temp", nullable = false)
    private val minimum: String,
) {
    init {
        if (require.isBlank() or minimum.isBlank()) {
            throw IllegalArgumentException("\"temperature\"은 공백일 수 없습니다.")
        }
    }
}
