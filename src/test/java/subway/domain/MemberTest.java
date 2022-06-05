package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @DisplayName("OneToMany (단방향) - Save")
    @Test
    void save() {
        Member member = new Member("maeng");
        member.addFavorate(favoriteRepository.save(new Favorite("즐겨찾기1")));
        memberRepository.save(member);
        memberRepository.flush();

        assertThat(member.getFavorites().get(0).getName()).isEqualTo("즐겨찾기1");
    }

    @DisplayName("OneToMany (단방향) - Update")
    @Test
    void update() {
        Member member = new Member("maeng");
        member.addFavorate(favoriteRepository.save(new Favorite("즐겨찾기1")));
        memberRepository.save(member);
        memberRepository.flush();
        assertThat(member.getFavorites().get(0).getName()).isEqualTo("즐겨찾기1");

        member.getFavorites().get(0).changeName("즐겨찾기2");
        memberRepository.flush();

        assertThat(member.getFavorites().get(0).getName()).isEqualTo("즐겨찾기2");
    }
}
