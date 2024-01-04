package gdsc.plantory.plant.domain

import gdsc.plantory.common.domain.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "history")
class History(

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private val type: HistoryType,

    @Column(name = "history_date", nullable = false)
    private val date: LocalDate,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "companion_plant_id")
    private val companionPlant: CompanionPlant? = null,

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {
}
