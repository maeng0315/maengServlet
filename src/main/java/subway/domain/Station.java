package subway.domain;

import javax.persistence.*;

@Entity                     // 객체와 테이블을 매핑 시켜주기 위해 필요한 어노테이션 | 이 클래스가 엔티티 클래스라는것을 드러내는 어노테이션 | ID 어노테이션을 가진 클래스를 가진 녀석을 엔티티라 부름(Entity는 여러 의미로 쓰이는데 JPA 에서는 이런 의미로 쓰임)
@Table(name = "station")    // 직접 테이블 이름을 지정해주고 싶을때 쓰는 어노테이션 | 어노테이션을 통체로 생략 시 클래스 이름을 테이블 이름으로 사용함
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 자동 생성하는 값을 사용할 때 | 오토인크리먼트 (PK number 자동 생성)
    private Long id;

    @Column(name = "name", nullable = false)                // Table 어노테이션과 똑같은데 컬럼에 이름을 지정해주는것, | 생략 시 마찬가지로 변수 이름 그대로 사용 | 스키마 지정 가능(nullable) |  Spring Boot Validation이 우선
    private String name;

    // JPA Entity 클래스에는 기본적으로 매개변수가 없는 생성자가 있어야함 | private으로 해도 되지만 나중에 프록시 객체 만들때 문제가 되서 protected로 만드는 편
    protected Station() {
    }

    public Station(final String name) {
        this(null, name);
    }

    public Station(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
