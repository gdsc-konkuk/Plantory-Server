package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("도메인 : PlantRecord")
class PlantRecordTest {

    @ParameterizedTest
    @ValueSource(strings = ["https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg", "https://blogshine.tistory.com/", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbbw4Az%2FbtsbCHvGUfF%2FwLs50sdYRtik8iaR6I3KEK%2Fimg.png", "www.naver.com"])
    fun `기록_생성_테스트`(uri: String) {
        assertThatCode { PlantRecord(uri, "test_comment") }.doesNotThrowAnyException()
    }

    @ParameterizedTest
    @ValueSource(strings = ["http//www.gurubee.net/postgresql/basic", "https:/blogshine.tistory.com/"])
    fun `사진 uri 형식 검증 테스트`(uri: String) {
        assertThrows<IllegalArgumentException> { PlantRecord(uri, "test_comment") }
    }

    @Test
    fun `사진 url 공백일 경우 예외를 반환`() {
        assertThrows<IllegalArgumentException> { PlantRecord("", "test_comment") }
    }

    @Test
    fun `기록 길이 제한 초과하면 예외를 반환`() {
        // length: 301
        val longComment = "https://www.google.com/search?q=%EC%95%88%EB%8dsfajaslkfjasdkfjkldsafjlsadkjf" +
            "lksajfldkadsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;" +
            "ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;ljlf" +
            "sadjlafkj;lkdjalkfjads;jfalksdfjlasjf;ljdsfajaslkfj"

        assertThrows<IllegalArgumentException> {
            PlantRecord(
                "https://nongsaro.go.kr/14687_MF_ATTACH_01.jpg",
                longComment
            )
        }
    }

    @Test
    fun `링크 url 길이 제한 초과하면 예외를 반환`() {
        // length: 1001
        val longLink = "https://www.google.com/search?q=%EC%95%88%EB%8dsfajaslkfjasdkfjkldsafjlsadkjf" +
            "lksajfldkadsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;" +
            "ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;ljlf" +
            "sadjlafkj;lkdjalkfjads;jfalksdfjlasjf;ljdsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafk" +
            "j;lkdjalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;" +
            "lkdjalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkd" +
            "jalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkf" +
            "jads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads" +
            ";jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfal" +
            "ksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjl" +
            "asjf;ljlfsadlfsadkj;lasfkj5%95%ED%95%98%EC%84%B8%EC%969i57j46i433i512l2j0i131i433i512l3j4" +
            "6i131i433i512j69i60.3640j0j7&sourceid=chrome&ie=UTF-8"

        assertThrows<IllegalArgumentException> { PlantRecord(longLink, "test_comment") }
    }
}
