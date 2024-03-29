package gdsc.plantory.plant.domain

import ConflictException
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

    _shortDescription: String,

    _nickname: String,

    @Column(name = "next_water_date", nullable = false)
    private var nextWaterDate: LocalDate,

    @Column(name = "last_water_date", nullable = false)
    private var lastWaterDate: LocalDate,

    @Column(name = "water_cycle", nullable = false)
    private var waterCycle: Int,

    @Column(name = "birth_date")
    private val birthDate: LocalDate? = null,

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
    private val shortDescription: ShortDescription = ShortDescription(_shortDescription)

    @Embedded
    private val nickname: NickName = NickName(_nickname)

    @Embedded
    private val records: PlantRecords = PlantRecords()

    @Embedded
    private val histories: PlantHistories = PlantHistories()

    init {
        if (lastWaterDate.isAfter(nextWaterDate)) {
            throw IllegalArgumentException("마지막으로 물 준 날짜는 다음에 물 줄 날짜보다 빠를 수 없습니다.")
        }
    }

    val getId: Long
        get() = this.id

    val getImageUrl: String
        get() = this.imageUrl.value

    val getNextWaterDate: LocalDate
        get() = LocalDate.from(this.nextWaterDate)

    val getLastWaterDate: LocalDate
        get() = LocalDate.from(this.lastWaterDate)

    val getNickName: String
        get() = this.nickname.value

    val getName: String
        get() = this.nickname.value

    val getSortDescription: String
        get() = this.shortDescription.value

    val getWaterCycle: Int
        get() = this.waterCycle

    val getBirthDate: LocalDate
        get() = LocalDate.from(this.birthDate ?: this.createAt)

    fun saveRecord(comment: String, imageUrl: String? = null, date: LocalDate = LocalDate.now()) {
        if (this.records.isAlreadyRegisteredAt(date)) {
            throw ConflictException()
        }

        this.records.add(PlantRecord(imageUrl, comment, this))
        this.saveRecordHistory(date)
    }

    fun saveHistory(historyType: HistoryType, date: LocalDate = LocalDate.now()) {
        validateInput(historyType, date)

        if (historyType == HistoryType.WATER_CHANGE) {
            this.lastWaterDate = date
            this.nextWaterDate = date.plusDays(this.waterCycle.toLong())
        }

        this.histories.add(PlantHistory(historyType, date, this))
    }

    fun recordSize(): Int = this.records.size()

    fun historySize(): Int = this.histories.size()

    fun calculateDaySince(currentDate: LocalDate): Int {
        require(!currentDate.isBefore(birthDate)) { "함께한 날은 음수가 될 수 없습니다. Date: $currentDate" }

        return ChronoUnit.DAYS.between(birthDate, currentDate).toInt() + 1
    }

    private fun validateInput(historyType: HistoryType, date: LocalDate) {
        if (isRecordType(historyType)) {
            throw IllegalArgumentException("데일리 기록은 히스토리 타입을 직접 추가할 수 없습니다.")
        }

        if (isNotCurrentDay(date)) {
            throw IllegalArgumentException("물을 줄 날짜는 오늘 날짜여야 합니다.")
        }
    }

    private fun isRecordType(historyType: HistoryType) = historyType == HistoryType.RECORDING

    private fun saveRecordHistory(date: LocalDate) {
        this.histories.add(PlantHistory(HistoryType.RECORDING, date, this))
    }

    private fun isNotCurrentDay(date: LocalDate) = !date.isEqual(LocalDate.now())

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
