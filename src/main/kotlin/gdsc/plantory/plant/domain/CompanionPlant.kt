package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "COMPANION_PLANT")
class CompanionPlant(

    _imageUrl: String,

    @Column(name = "nickname", nullable = false)
    private var nickname: String,

    @Column(name = "short_description", nullable = false)
    private var shortDescription: String,

    @Column(name = "next_water_date", nullable = false)
    private var nextWaterDate: LocalDate,

    @Column(name = "last_water_date", nullable = false)
    private var lastWaterDate: LocalDate,

    @Column(name = "water_cycle", nullable = false)
    private var waterCycle: Int,

    @Column(name = "birth_date")
    private var birthDate: LocalDate? = null,

    @Column(name = "member_id", nullable = false)
    private val memberId: Long = 0L,

    @Column(name = "plant_information_id", nullable = false)
    private val plantInformationId: Long = 0L,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val imageUrl: ImageUrl = ImageUrl(_imageUrl)

    @Embedded
    private val records: PlantRecords = PlantRecords()

    @Embedded
    private val histories: Histories = Histories()

    fun saveRecord(comment: String, imageUrl: String? = null) =
        this.records.add(PlantRecord(imageUrl, comment, this))

    fun saveHistory(waterChange: HistoryType, date: LocalDate = LocalDate.now()) =
        this.histories.add(History(waterChange, date, this))

    fun recordSize(): Int = this.records.size()

    fun historySize(): Int = this.histories.size()

    fun calculateDaySince(currentDate: LocalDate): Int {
        require(!currentDate.isBefore(birthDate)) { "함께한 날은 음수가 될 수 없습니다. Date: $currentDate" }

        return ChronoUnit.DAYS.between(birthDate, currentDate).toInt() + 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompanionPlant

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
