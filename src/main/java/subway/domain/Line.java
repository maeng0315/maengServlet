package subway.domain;

import javax.persistence.*;

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

    protected Line() {
    }

    public Line(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
