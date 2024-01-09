package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

private const val NICKNAME_MAX_LENGTH = 16

@Embeddable
class NickName(
    @Column(name = "nickname", nullable = false)
    private var _value: String,
) {
    init {
        validateValue(_value)
    }

    val value: String
        get() = this._value

    private fun validateValue(value: String) {
        if (value.isBlank()) {
            throw IllegalArgumentException("\"nickName\"은 공백일 수 없습니다.")
        }

        if (value.length > NICKNAME_MAX_LENGTH) {
            throw IllegalArgumentException("\"nickName\"은 ${NICKNAME_MAX_LENGTH}자를 초과할 수 없습니다.")
        }
    }
}
