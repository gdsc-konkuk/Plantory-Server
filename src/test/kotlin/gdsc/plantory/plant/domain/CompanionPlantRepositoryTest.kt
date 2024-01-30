package gdsc.plantory.plant.domain

import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlant
import gdsc.plantory.fixture.MemberFixture.generateMember
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.util.AcceptanceTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate

@DisplayName("리포지토리 : CompanionPlant")
class CompanionPlantRepositoryTest(
    @Autowired val companionPlantRepository: CompanionPlantRepository,
    @Autowired val memberRepository: MemberRepository,
) : AcceptanceTest() {

    @Test
    fun `물주는 날짜가 된 반려식물의 별칭과 해당 유저의 deviceToken을 조회한다`() {
        // given
        val member = generateMember(deviceToken = "shine")
        val savedMember = memberRepository.save(member)
        val memberId = savedMember.getId

        val nextWaterDate = LocalDate.of(2024, 1, 7)

        val companionPlant1 = createCompanionPlantByLastWaterDate(nextWaterDate, memberId)
        val companionPlant2 = createCompanionPlantByLastWaterDate(nextWaterDate.plusDays(1), memberId)
        val companionPlant3 = createCompanionPlantByLastWaterDate(nextWaterDate.minusDays(1), memberId)
        val companionPlant4 = createCompanionPlantByLastWaterDate(nextWaterDate, memberId)
        companionPlantRepository.saveAll(
            listOf(
                companionPlant1,
                companionPlant2,
                companionPlant3,
                companionPlant4
            )
        )

        // when
        val results = companionPlantRepository.findAllByNextWaterDate(nextWaterDate)

        // then
        assertThat(results).hasSize(2)
            .extracting("deviceToken", "nickName")
            .containsExactlyInAnyOrder(
                Tuple.tuple("shine", "2024-01-07"),
                Tuple.tuple("shine", "2024-01-07")
            )
    }

    private fun createCompanionPlantByLastWaterDate(nextWaterDate: LocalDate, memberId: Long) =
        generateCompanionPlant(
            memberId = memberId,
            nickname = nextWaterDate.toString(),
            nextWaterDate = nextWaterDate,
            lastWaterDate = nextWaterDate.minusDays(5),
            waterCycle = 5
        )
}
