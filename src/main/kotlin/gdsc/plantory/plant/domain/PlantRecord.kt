package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "PLANT_RECORD")
class PlantRecord(

    _imageUrl: String?,

    _comment: String,

    @ManyToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY)
    @JoinColumn(name = "companion_plant_id")
    private val companionPlant: CompanionPlant? = null,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val imageUrl: ImageUrl? = _imageUrl?.let { ImageUrl(it) }

    @Embedded
    private val comment: Comment = Comment(_comment)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlantRecord

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
