package gdsc.plantory.plant.service

import gdsc.plantory.common.support.photo.PhotoLocalManager
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.member.domain.findByDeviceTokenOrThrow
import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantLookupRequest
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.plantInformation.domain.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class PlantService(
    private val companionPlantRepository: CompanionPlantRepository,
    private val plantInformationRepository: PlantInformationRepository,
    private val photoLocalManager: PhotoLocalManager,
    private val memberRepository: MemberRepository,
) {

    fun create(request: CompanionPlantCreateRequest, image: MultipartFile?, deviceToken: String) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val plantInformation = plantInformationRepository.findByIdOrThrow(request.plantInformationId)
        val imagePath: String = saveImageAndGetPath(image, plantInformation.getImageUrl)

        val companionPlant = request.toEntity(imagePath, findMember.getId, plantInformation.getWaterCycle)

        companionPlantRepository.save(companionPlant)
    }

    private fun saveImageAndGetPath(image: MultipartFile?, defaultUrl: String): String {
        return image?.let { photoLocalManager.upload(image) } ?: return defaultUrl
    }

    fun lookup(request: CompanionPlantLookupRequest, deviceToken: String): MutableList<CompanionPlant> {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        return companionPlantRepository.findAllByMemberid(findMember.getId)
    }
}
