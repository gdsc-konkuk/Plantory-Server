package gdsc.plantory.plant.service

import gdsc.plantory.common.support.photo.PhotoLocalManager
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.member.domain.findByDeviceTokenOrThrow
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.domain.findByIdAndMemberIdOrThrow
import gdsc.plantory.plant.domain.findRecordByDateOrThrow
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantDto
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordDto
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
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
        val findPlantInformation = plantInformationRepository.findByIdOrThrow(request.plantInformationId)
        val imagePath: String = saveImageAndGetPath(image, findPlantInformation.getImageUrl)

        val companionPlant = request.toEntity(imagePath, findMember.getId, findPlantInformation.getWaterCycle)

        companionPlantRepository.save(companionPlant)
    }

    fun createHistory(plantId: Long, deviceToken: String, historyType: HistoryType) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findPlant = companionPlantRepository.findByIdAndMemberIdOrThrow(plantId, findMember.getId)
        findPlant.saveHistory(historyType)
    }

    @Transactional(readOnly = true)
    fun lookupAllPlantsOfMember(deviceToken: String): List<CompanionPlantDto> {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)

        return companionPlantRepository.findAllByMemberId(findMember.getId)
            .stream()
            .map { CompanionPlantDto.from(it) }
            .toList()
    }

    fun registerRecord(
        request: PlantRecordCreateRequest,
        image: MultipartFile?,
        deviceToken: String,
    ) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findCompanionPlant =
            companionPlantRepository.findByIdAndMemberIdOrThrow(request.companionPlantId, findMember.getId)
        val imagePath: String = saveImageAndGetPath(image, findCompanionPlant.getImageUrl)

        findCompanionPlant.saveRecord(request.comment, imagePath)
        findCompanionPlant.saveHistory(HistoryType.RECORDING)
    }

    @Transactional(readOnly = true)
    fun lookupPlantRecordOfDate(request: PlantRecordLookupRequest, deviceToken: String): PlantRecordDto {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findPlantRecord = companionPlantRepository.findRecordByDateOrThrow(
            request.companionPlantId,
            findMember.getId,
            request.recordDate
        )

        return PlantRecordDto.from(findPlantRecord)
    }

    private fun saveImageAndGetPath(image: MultipartFile?, defaultUrl: String): String {
        return image?.let { photoLocalManager.upload(image) } ?: return defaultUrl
    }
}
