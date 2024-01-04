package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "plant_record")
class PlantRecord(

    _imageUrl: String,

    _comment: String,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "companion_plant_id")
    private val companionPlant: CompanionPlant? = null,

    @Id
    @Column(name = "plant_record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val imageUrl: ImageUrl

    @Embedded
    private val comment: Comment

    init {
        this.imageUrl = ImageUrl(_imageUrl)
        this.comment = Comment(_comment)
    }
}
