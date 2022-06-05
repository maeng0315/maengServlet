package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest    // JPA 테스트를 위한 설정들을 불러오는 어노테이션
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @DisplayName("저장 테스트")
    @Test
    void save() {
        final Station expected = new Station("잠심역");
        final Station actual = stationRepository.save(expected);     // 인자로 넣은 타입과 같은 타입으로 리턴 해줌, 다만 타입이 같다고 해서 인자로 넣은 객체와 리턴 객체가 같은 객체라는 말은 아님 | 습관적으로 리턴 값을 변수로 들고 다니는게 좋음 (언제 써야할지 모르니)
        assertThat(actual.getId()).isNotNull();                      // 입력 됐다면 id 값이 있을것이기 때문에
        assertThat(actual.getName()).isEqualTo("잠심역");             // 내가 넣은 값이 맞는지
        System.out.println("getId: " + actual.getId());
    }

    @DisplayName("시그니쳐 자동 쿼리 생성 | 저장, 조회 테스트")
    @Test
    void findByName() {
        stationRepository.save(new Station("면목역"));
        final Station actual = stationRepository.findByName("면목역");

        // NPE 방지 용으로 Optional 권장
        assertThat(actual).isNotNull();     // 조회 되는 결과가 있는지
    }

    @DisplayName("영속성 컨텍스트 - 1차 캐시 (Map의 K,V와 비슷), Key = id | 동일성 보장 테스트")
    @Test
    void identity() {
        final Station station1 = stationRepository.save(new Station("면목역"));

        // findByName으로 조회 시 1차 캐시를 거치지 않고 DB로 바로 조회
        final Station station2 = stationRepository.findByName("면목역");
        assertThat(station1 == station2).isTrue();      // (저장 인스턴스 == 조회 인스턴스)
        assertThat(station1).isSameAs(station2);

        // findById으로 조회 시 영속성 켄텍스트 1차 캐시만 거치기 때문에 DB까지 가지 않음
        final Station station3 = stationRepository.findById(station1.getId()).get();
        assertThat(station1).isSameAs(station3);

        // 영속석 컨텍스트 1차 캐시에 없는 id로 조회할 경우, 그때 DB에 접근하고 그 결과 값을 영속성 컨텍스트 1차 캐시에 저장, 그리고 반환 함
    }

    @DisplayName("영속성 컨텍스트 - 트랜잭션을 지원하는 쓰기 지연, 저장 확인")
    @Test
    void save2() {
        // @GeneratedValue(strategy = GenerationType.IDENTITY)
        // 기존 save()에서 사용한 방식인 오토인크리먼트 전략은 영속성 컨텍스트에서 활용할 ID가 있어야 하기 때문에, 쓰기 지연 없이 DB insert를 바로 때렸음
        final Station actual = stationRepository.save(new Station(1L, "사가정역"));
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("사가정역");

        // 그치만 save2()에서는 쓰기 지연으로 인해 insert 쿼리가 작동하지 않은것을 알 수 있음

        // @Transactional 어노테이션은 테스트 코드가 아닌 일반 코드에서도 자주 사용하는 어노테이션일텐데요 스프링에서는 어떻게 test code + @Transactional 을 인식해서 query를 롤백하는걸까요? : @ExtendWith(SpringExtension.class), @Transactional 참고
        // @DataJpaTest 어노테이션 때문에 테스트가 끝나더라도 자동으로 flush 되지 않음
        stationRepository.flush();

        /* SELECT 쿼리가 동작 하는 이유
            ID(1L)을 전달 해줬기 때문에,
            확인 차 조회 쿼리를 날려 보는 것
            ( JPA : ID가 있다? = DB에 이미 insert 된 값이다 & UPDATE를 해야 한다 & 실제 DB에 존재하는 값이 맞는지 확인 )
         */

        /* SELECT 쿼리를 안나가게 하는 법
            Station.java -> implements Persistable<Long>
            isNew() -> return true | 무조건 새로운 Entity로 처리
         */
    }

    /* 더티 채킹
      트랜잭션 안에서 Entity의 변경이 일어났을 때
      변경한 내용을 자동으로 DB에 반영하는 것
    */
    @DisplayName("영속성 컨텍스트 - 변경 감지 | 더티 채킹")
    @Test
    void update() {
        final Station station1 = stationRepository.save(new Station("사가정역"));   // 영속화(save) 해주지 않으면
        station1.changeName("면목역");

        final Station station2 = stationRepository.findByName("면목역");   // findByName으로 조회 시 1차 캐시를 거치지 않고 DB로 바로 조회
        assertThat(station2).isNotNull();

        /*
          flush 되어 영속성 컨텍스트에 영속 상태가 되었을때
          최초 영속화 되었을때의 엔티티(스냅샷)와
          현재 엔티티의 값을 비교하여
          자동으로 UPDATE 쿼리를 생성하여 '쓰기 지연 SQL 저장소'에 저장 해둠
        */

        /*
          findByName같은 JPQL를 쓰게 될 경우
          코드 흐름 상의 문제가 발생 하기 때문에 ('station1'를 만들고 'station1.changeName()'으로 이름을 바꾸었지만 '쓰기 지연'으로 인해 실제 DB insert 된 값이 없는 상태인데 'findByName()'만 먼저 실행 되어 DB 조회를 해봤자 값이 없는 상태 이기 때문에 | DB를 최신화 해야 하기에)
          JPA가 '쓰기 지연' 저장소에 있는 쿼리들을 자동으로 'flush' 해줌
        */
    }


}
