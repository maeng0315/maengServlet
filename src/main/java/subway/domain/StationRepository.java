package subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA framework | JPA에서도 반복적이던 부분들을 생략 가능하도록 해줌, 메서드 이름으로 쿼리 생성 해줌
public interface StationRepository extends JpaRepository<Station, Long> { // <엔티티 클래스, id의 데이터 형>

    Station findByName(String name);
}
