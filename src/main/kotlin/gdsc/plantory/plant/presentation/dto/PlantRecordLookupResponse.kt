package gdsc.plantory.plant.presentation.dto

class PlantRecordLookupResponse(
    val imageUrl: String,
    val comment: String,
    val nickname: String,
    val water: Boolean,
) {
    companion object {
        fun of(plantRecord: PlantRecordDto, hasWater: Boolean): PlantRecordLookupResponse {
            return PlantRecordLookupResponse(
                plantRecord.imageUrl,
                plantRecord.comment,
                plantRecord.nickname,
                hasWater
            )
        }
    }
}
