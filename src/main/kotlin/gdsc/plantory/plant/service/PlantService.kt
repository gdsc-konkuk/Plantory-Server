package gdsc.plantory.plant.service

import gdsc.plantory.common.support.photo.PhotoLocalManager
import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.plantInformation.domain.findByIdOrThrow
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class PlantService(
    @Value("\${companionPlant.image.directory}")
    private var companionPlantImageDirectory: String,
    private val companionPlantRepository: CompanionPlantRepository,
    private val plantInformationRepository: PlantInformationRepository,
    private val photoLocalManager: PhotoLocalManager
) {

    fun create(request: CompanionPlantCreateRequest, image: MultipartFile?, deviceToken: String) {
        val plantInformation = plantInformationRepository.findByIdOrThrow(request.plantInformationId)

        val imagePath: String = saveImageAndGetPath(image, plantInformation.getImageUrl)

        val companionPlant = CompanionPlant(
            _imageUrl = imagePath,
            _shortDescription = request.shortDescription,
            _nickname = request.nickname,
            nextWaterDate = request.lastWaterDate.plusDays(plantInformation.getWaterCycle.toLong()),
            lastWaterDate = request.lastWaterDate,
            waterCycle = plantInformation.getWaterCycle,
            birthDate = request.birthDate
        )

        companionPlantRepository.save(companionPlant)
    }

    private fun saveImageAndGetPath(image: MultipartFile?, defaultUrl: String): String {
        if (image == null) return defaultUrl
        return photoLocalManager.upload(image, companionPlantImageDirectory)!!
    }
}
