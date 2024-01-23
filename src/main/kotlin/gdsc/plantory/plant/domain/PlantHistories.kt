package gdsc.plantory.plant.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class PlantHistories {

    @OneToMany(mappedBy = "companionPlant", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val histories: MutableList<PlantHistory> = mutableListOf()

    fun add(history: PlantHistory): Any = this.histories.add(history)

    fun size(): Int = this.histories.size
}
