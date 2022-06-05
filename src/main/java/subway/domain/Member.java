package subway.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /* 일대다 (1:N) 관계 매핑 정보 (단방향)
       - 일대다 단방향 매핑의 단점
          1. 매핑한 객체가 관리하는 외래키가 다른 테이블에 있다 | 주체가 되는 테이블이 아니라 다른 테이블이 외래키를 관리 함 | Member가 어떤 Favorite와 관계를 맺고 있는지 조회하려면 Favorite 테이블의 member_id를 사용해서 다시 한번 조회를 해야함(추가 쿼리 발생)
          2. 연관 관계 처리를 위한 UPDATE SQL을 추가로 실행해야 한다.
          3. 일대다 단방향 매핑보다는 다대일 양방향 매핑을 '권장'한다. | 실무에서는 써야 할 경우도 있을 수 있음
     */
    @OneToMany      // Member가 직접 외래키를 관리하기 때문에 mappedBy가 빠짐
    @JoinColumn(name = "member_id")     // OneToMany 단방향 @JoinColumn의 역할, 테이블 2개만 만들어 + 외래키 관리는 '다'에서 하니까 Favorite 테이블에 외래키 컬럼(member_id) 만들어
    private List<Favorite> favorites = new ArrayList<>();

    public Member(String name) {
        this.name = name;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void addFavorate(Favorite favorite) {
        favorites.add(favorite);
    }
}
