package gdsc.plantory.fixture

import gdsc.plantory.plantInformation.domain.PlantInformation

object PlantInformationFixture {

    fun generatePlantInformation(
        imageUrl: String = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
        species: String = "덕구리난",
        familyName: String = "백합과",
        requireTemp: String = "21~25℃",
        minimumTemp: String = "13℃ 이상",
        waterCycleSpring: Int = 4,
        waterCycleSummer: Int = 3,
        waterCycleAutumn: Int = 4,
        waterCycleWinter: Int = 4,
        smell: String = "거의 없음",
        manageLevel: String = "초보자",
        growSpeed: String = "느림",
        requireHumidity: String = "40% 미만",
        postingPlace: String = "거실 창측 (실내깊이 150~300cm),발코니 내측 (실내깊이 50~150cm),발코니 창측 (실내깊이 0~50cm)",
        poison: String = "없음",
        specialManageInfo: String = "적절한 환기가 필요함, 여름동안 햇볕이 잘드는 위치에 배치하는 것이 좋음.",
    ): PlantInformation {
        return PlantInformation(
            _imageUrl = imageUrl,
            _species = species,
            _familyName = familyName,
            _requireTemp = requireTemp,
            _minimumTemp = minimumTemp,
            _waterCycleSpring = waterCycleSpring,
            _waterCycleSummer = waterCycleSummer,
            _waterCycleAutumn = waterCycleAutumn,
            _waterCycleWinter = waterCycleWinter,
            smell = smell,
            manageLevel = manageLevel,
            growSpeed = growSpeed,
            requireHumidity = requireHumidity,
            postingPlace = postingPlace,
            poison = poison,
            specialManageInfo = specialManageInfo,
        )
    }
}