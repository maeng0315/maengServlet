package subway.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    private String name;

    /*
      Line -> Stations (일대다, 앙뱡항 관계 매핑 정보) (@OneToMany)
      객체 입장에서 서로 단방향으로 설정 해주면, 서로를 가르키는 단방향이 양방향 처럼 쓰이는 것
      station Table안에 line Table의 FK(참조키) 컬럼 이름 지정 'line_id' (@JoinColumn) | 어노테이션이나 이름, 생략 시 자동으로 '컬럼 이름_id'
    */

    /* 연관 관계의 주인(외래키 관리자)을 지정 해주지 않으면 -> 테이블 간 관계를 관리하는 별도의 관계 테이블을 자동으로 생성 해 버림
        create table line_stations (
            line_id bigint not null,
            stations_id bigint not null
        )
       그러니 'Station 클래스에 있는 line 변수(필드)가 fk를 관리할거야 별도 테이블 생성하지 마'라고 JPA에게 알려주는 것
    */
    @OneToMany(mappedBy = "line")
    private List<Station> stations = new ArrayList<>();

    public List<Station> getStations() {
        return stations;
    }

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStation(final Station station) {
        if (station.getLine() == null) {
            station.setLine(this);  // 이때 추가 되는 station에는 'line == null'이기 때문에 연관 관계 형성을 위해 line을 추가 해줘야 함 | 연관 관계(양방향) 편의 메소드
        }
        stations.add(station);
    }
}
