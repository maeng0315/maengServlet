package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest    // JPA 테스트를 위한 설정들을 불러오는 어노테이션
class StationRepositoryTest {

    @Autowired
    private StationRepository stations;

    @DisplayName("저장 테스트")
    @Test
    void save() {
        final Station expected = new Station("잠심역");
        final Station actual = stations.save(expected);     // 인자로 넣은 타입과 같은 타입으로 리턴 해줌, 다만 타입이 같다고 해서 인자로 넣은 객체와 리턴 객체가 같은 객체라는 말은 아님 | 습관적으로 리턴 값을 변수로 들고 다니는게 좋음 (언제 써야할지 모르니)
        assertThat(actual.getId()).isNotNull();             // 입력 됐다면 id 값이 있을것이기 때문에
        assertThat(actual.getName()).isEqualTo("잠심역");    // 내가 넣은 값이 맞는지
        System.out.println("getId: " + actual.getId());
    }

    @DisplayName("시그니쳐 자동 쿼리 생성 | 저장, 조회 테스트")
    @Test
    void findByName() {
        stations.save(new Station("면목역"));
        final Station actual = stations.findByName("면목역");

        // NPE 방지 용으로 Optional 권장
        assertThat(actual).isNotNull();     // 조회 되는 결과가 있는지
    }

    @DisplayName("영속성 컨텍스트 - 1차 캐시 (Map의 K,V와 비슷), Key = id | 동일성 보장 테스트")
    @Test
    void identity() {
        final Station station1 = stations.save(new Station("면목역"));

        // findByName으로 조회 시 DB를 바로 조회
        final Station station2 = stations.findByName("면목역");
        assertThat(station1 == station2).isTrue();      // (저장 인스턴스 == 조회 인스턴스)
        assertThat(station1).isSameAs(station2);

        // findById으로 조회 시 영속성 켄텍스트 1차 캐시만 거치기 때문에 DB까지 가지 않음
        final Station station3 = stations.findById(station1.getId()).get();
        assertThat(station1).isSameAs(station3);

        // 영속석 컨텍스트 1차 캐시에 없는 id로 조회할 경우, 그때 DB에 접근하고 그 결과 값을 영속성 컨텍스트 1차 캐시에 저장, 그리고 반환 함
    }

    @DisplayName("영속성 컨텍스트 - 트랜잭션을 지원하는 쓰기 지연, 저장 확인")
    @Test
    void save2() {
        // @GeneratedValue(strategy = GenerationType.IDENTITY)
        // 기존 방식인 오토인크리먼트 전략은 영속성 컨텍스트에서 활용할 ID가 있어야 하기 때문에, 쓰기 지연 없이 DB insert를 바로 때렸음
        final Station actual = stations.save(new Station(1L, "사가정역"));
        assertThat(actual.getName()).isEqualTo("사가정역");

        // 쓰기 지연으로 인해 insert 쿼리가 작동하지 않은걸 알 수 있음
        // 직접 flush 해주지 않으면 @DataJpaTest 어노테이션 때문에 Test가 끝나더라도 flush 되지 않음 | @ExtendWith(SpringExtension.class), @Transactional 참고
        stations.flush();
    }
}
