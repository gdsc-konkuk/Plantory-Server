package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

private const val DEVICE_TOKEN_LENGTH_LIMIT = 300

@Entity
@Table(name = "MEMBER")
class Member(

    @Column(name = "device_token", length = DEVICE_TOKEN_LENGTH_LIMIT, nullable = false)
    private val deviceToken: String,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    init {
        if (deviceToken.length > DEVICE_TOKEN_LENGTH_LIMIT) {
            throw IllegalArgumentException("${DEVICE_TOKEN_LENGTH_LIMIT}자를 초과하는 \"device_token\"은 존재하지 않습니다.")
        }

        if (deviceToken.isBlank()) {
            throw IllegalArgumentException("\"device_token\"은 공백일 수 없습니다.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}