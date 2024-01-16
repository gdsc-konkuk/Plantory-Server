package gdsc.plantory.plant.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Embeddable
class PlantRecords {

    @OneToMany(mappedBy = "companionPlant", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val plantRecords: MutableList<PlantRecord> = mutableListOf()

    fun add(plantRecord: PlantRecord) = this.plantRecords.add(plantRecord)
    fun isAlreadyRegisteredAt(date: LocalDate) = this.plantRecords.find { it.getDate == date } != null
    fun size(): Int = this.plantRecords.size
}
