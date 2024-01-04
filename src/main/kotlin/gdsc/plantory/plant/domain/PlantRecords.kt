package gdsc.plantory.plant.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class PlantRecords {

    @OneToMany(mappedBy = "companion_plant", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val plantRecords: MutableList<PlantRecord> = mutableListOf()

    fun add(plantRecord: PlantRecord) = this.plantRecords.add(plantRecord)

    fun size(): Int = this.plantRecords.size
}
