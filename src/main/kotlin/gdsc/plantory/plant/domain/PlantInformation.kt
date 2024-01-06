package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "PLANT_INFORMATION")
class PlantInformation(
    _imageUrl: String,

    @Column(name = "name", nullable = false)
    private var name: String,

    @Column(name = "family_name")
    private var familyName: String? = null,

    @Column(name = "smell")
    private var smell: String? = null,

    @Column(name = "poison")
    private var poison: String? = null,

    @Column(name = "manage_level")
    private var manageLevel: String? = null,

    @Column(name = "grow_speed")
    private var growSpeed: String? = null,

    @Column(name = "require_temp")
    private var requireTemp: String? = null,

    @Column(name = "minimum_temp")
    private var minimumTemp: String? = null,

    @Column(name = "require_humidity")
    private var requireHumidity: String? = null,

    @Column(name = "posting_place")
    private var postingPlace: String? = null,

    @Column(name = "special_manage_info")
    private var specialManageInfo: String? = null,

    @Embedded
    private val waterCycle: WaterCycle? = null,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) : BaseTimeEntity() {

    @Embedded
    private val imageUrl: ImageUrl = ImageUrl(_imageUrl)

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("\"name\"은 공백일 수 없습니다.")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlantInformation

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}