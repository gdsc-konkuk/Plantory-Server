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

    _species: String,
    _familyName: String,

    _requireTemp: String,
    _minimumTemp: String,

    _waterCycleSpring: Int,
    _waterCycleSummer: Int,
    _waterCycleAutumn: Int,
    _waterCycleWinter: Int,

    @Column(name = "smell", nullable = false)
    private var smell: String,

    @Column(name = "manage_level", nullable = false)
    private var manageLevel: String,

    @Column(name = "grow_speed", nullable = false)
    private var growSpeed: String,

    @Column(name = "require_humidity", nullable = false)
    private var requireHumidity: String,

    @Column(name = "posting_place", nullable = false)
    private var postingPlace: String,

    @Column(name = "poison")
    private var poison: String? = null,

    @Column(name = "special_manage_info")
    private var specialManageInfo: String? = null,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L
) : BaseTimeEntity() {

    @Embedded
    private val imageUrl: ImageUrl = ImageUrl(_imageUrl)

    @Embedded
    private val species: Species = Species(_species, _familyName)

    @Embedded
    private val temperature: Temperature = Temperature(_requireTemp, _minimumTemp)

    @Embedded
    private val waterCycle: WaterCycle = WaterCycle(
        _waterCycleSpring, _waterCycleSummer, _waterCycleAutumn, _waterCycleWinter
    )

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