package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @DisplayName("ManyToOne (단방향) - Save")
    @Test
    void saveWithLine() {
        // save() 해주지 않으면 ID 값이 null인데 이것은 영속화 되지 않았기 때문 (JPA - 영속 상태(영속 화) - 관계 - ID)
        final Station station = new Station("사가정역");
        station.setLine(lineRepository.save(new Line("7호선")));
        final Station actual = stationRepository.save(station);
        stationRepository.flush();
    }

    @DisplayName("ManyToOne (단방향) - Select")
    @Test
    void findByNameWithLine() {
        final Station station = stationRepository.findByName("면목역");
        assertThat(station).isNotNull();
        assertThat(station.getLine().getName()).isEqualTo("7호선");
    }

    @DisplayName("ManyToOne (단방향) - Update")
    @Test
    void updateWithLine() {
        final Station station = stationRepository.findByName("면목역");
        station.setLine(lineRepository.save(new Line("1호선")));   // insert into line + update station
        stationRepository.flush();
    }

    @DisplayName("ManyToOne (단방향) - 연관 관계 제거 (RemoveLine)")
    @Test
    void removeLine() {
        final Station station = stationRepository.findByName("면목역");
        station.setLine(null);
        stationRepository.flush();
    }

    @DisplayName("OneToMany (양방향) - (Line 조회 -> Line ID로 Station 조회)")
    @Test
    void findByName() {
        final Line line = lineRepository.findByName("7호선");
        assertThat(line.getStations().size() != 0).isTrue();
        assertThat(line.getStations().get(0).getName()).isEqualTo("면목역");
    }

    @DisplayName("OneToMany (양방향) - 연관 관계의 주인(외래키 관리자) | Line 추가 저장)")
    @Test
    void save() {
        final Line line = new Line("2호선");
        line.addStation(stationRepository.save(new Station("면목역")));
        lineRepository.save(line);
        lineRepository.flush();

        /* 관계가 형성 되어 있지 않기 때문에
           Line에서 Station에 대한 insert 작업을 하려 해도
           Line 테이블 Insert 쿼리만 생성 됨
           (다대일, 일대다 관계에서는 항상 '다' 쪽이 외래키 관리자)
        */
    }
}
