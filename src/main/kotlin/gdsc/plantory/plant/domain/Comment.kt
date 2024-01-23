package gdsc.plantory.plant.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

private const val MAX_CONTENT_LENGTH = 300

@Embeddable
class Comment(
    @Column(name = "comment", length = MAX_CONTENT_LENGTH, nullable = false)
    private val content: String,
) {
    init {
        validateContent(content)
    }

    val value: String
        get() = this.content

    private fun validateContent(content: String) {
        if (content.isBlank()) {
            throw IllegalArgumentException("기록은 공백일 수 없습니다.")
        }

        if (content.length > MAX_CONTENT_LENGTH) {
            throw IllegalArgumentException("${MAX_CONTENT_LENGTH}자를 초과하는 기록은 저장할 수 없습니다.")
        }
    }
}
