package gdsc.plantory.plant.service

import gdsc.plantory.common.support.photo.PhotoLocalManager
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.member.domain.findByDeviceTokenOrThrow
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.domain.findByIdAndMemberIdOrThrow
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupResponse
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.plantInformation.domain.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.YearMonth

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

        val companionPlant = request.toEntity(
            imagePath,
            findMember.getId,
            findPlantInformation.getWaterCycle,
            request.plantInformationId
        )

        companionPlantRepository.save(companionPlant)
    }

    fun remove(companionPlantId: Long, deviceToken: String) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        companionPlantRepository.removeByIdAndMemberId(companionPlantId, findMember.getId)
    }

    @Transactional(readOnly = true)
    fun lookupAllCompanionPlantsOfMember(deviceToken: String): CompanionPlantsLookupResponse {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findCompanionPlants = companionPlantRepository.findAllByMemberId(findMember.getId)

        return CompanionPlantsLookupResponse(findCompanionPlants)
    }

    fun createPlantHistory(plantId: Long, deviceToken: String, historyType: HistoryType) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findCompanionPlant = companionPlantRepository.findByIdAndMemberIdOrThrow(plantId, findMember.getId)

        if (historyType == HistoryType.RECORDING)
            throw IllegalArgumentException("데일리 기록 히스토리는 직접 추가할 수 없습니다.")

        findCompanionPlant.saveHistory(historyType)
    }

    @Transactional(readOnly = true)
    fun lookupAllPlantHistoriesOfMonth(
        companionPlantId: Long,
        targetMonth: YearMonth,
        deviceToken: String
    ): PlantHistoriesLookupResponse {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findPlantHistories = companionPlantRepository.findAllHistoriesByMonth(
            companionPlantId,
            findMember.getId,
            targetMonth.year,
            targetMonth.monthValue
        )

        return PlantHistoriesLookupResponse.from(findPlantHistories)
    }

    fun createRecord(
        companionPlantId: Long,
        request: PlantRecordCreateRequest,
        image: MultipartFile?,
        deviceToken: String,
    ) {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findCompanionPlant =
            companionPlantRepository.findByIdAndMemberIdOrThrow(companionPlantId, findMember.getId)
        val imagePath: String = saveImageAndGetPath(image, findCompanionPlant.getImageUrl)

        // TODO : Cloud 환경으로 이전 후 제거, 로컬 사진 저장 테스트 용도
        val baseUrl = "https://nongsaro.go.kr/"
        findCompanionPlant.saveRecord(request.comment, baseUrl + imagePath)
        findCompanionPlant.saveHistory(HistoryType.RECORDING)
    }

    @Transactional(readOnly = true)
    fun lookupPlantRecordOfDate(
        companionPlantId: Long,
        recordDate: LocalDate,
        deviceToken: String
    ): PlantRecordLookupResponse {
        val findMember = memberRepository.findByDeviceTokenOrThrow(deviceToken)
        val findPlantRecord = companionPlantRepository.findRecordByDate(
            companionPlantId,
            findMember.getId,
            recordDate
        )

        val historyType =
            companionPlantRepository.findAllHistoryTypeByDate(companionPlantId, recordDate)

        return PlantRecordLookupResponse.of(findPlantRecord, historyType.contains(HistoryType.WATER_CHANGE))
    }

    private fun saveImageAndGetPath(image: MultipartFile?, defaultUrl: String): String {
        return image?.let { photoLocalManager.upload(image) } ?: return defaultUrl
    }
}
