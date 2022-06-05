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

    @DisplayName("ManyToOne - Save")
    @Test
    void saveWithLine() {
        // save() 해주지 않으면 ID 값이 null인데 이것은 영속화 되지 않았기 때문 (JPA - 영속 상태(영속 화) - 관계 - ID)
        final Station station = new Station("사가정역");
        station.setLine(lineRepository.save(new Line("7호선")));
        final Station actual = stationRepository.save(station);
        stationRepository.flush();
    }

    @DisplayName("ManyToOne - Select")
    @Test
    void findByNameWithLine() {
        final Station station = stationRepository.findByName("면목역");
        assertThat(station).isNotNull();
        assertThat(station.getLine().getName()).isEqualTo("7호선");
    }

    @DisplayName("ManyToOne - Update")
    @Test
    void updateWithLine() {
        final Station station = stationRepository.findByName("면목역");
        station.setLine(lineRepository.save(new Line("1호선")));   // insert into line + update station
        stationRepository.flush();
    }

    @DisplayName("ManyToOne - 연관 관계 제거 (RemoveLine)")
    @Test
    void removeLine() {
        final Station station = stationRepository.findByName("면목역");
        station.setLine(null);
        stationRepository.flush();
    }
}
