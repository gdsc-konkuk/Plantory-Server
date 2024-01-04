package gdsc.plantory.plant.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.OneToMany

@Embeddable
class Histories {

    @OneToMany(mappedBy = "companion_plant", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val histories: MutableList<History> = mutableListOf()

    fun add(history: History): Any = this.histories.add(history)

    fun size(): Int = this.histories.size
}
