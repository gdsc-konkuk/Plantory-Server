package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

private const val DESCRIPTION_MAX_LENGTH = 16

@Embeddable
class ShortDescription(
    @Column(name = "short_description", nullable = false)
    private var _value: String,
) {

    init {
        validateValue(_value)
    }

    val value: String
        get() = this._value

    private fun validateValue(value: String) {
        if (value.isBlank()) {
            throw IllegalArgumentException("\"shortDescription\"은 공백일 수 없습니다.")
        }

        if (value.length > DESCRIPTION_MAX_LENGTH) {
            throw IllegalArgumentException("\"shortDescription\"은 16자를 초과할 수 없습니다.")
        }
    }
}
