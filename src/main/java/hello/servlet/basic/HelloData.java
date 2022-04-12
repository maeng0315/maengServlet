package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

//롬복으로 겟터 셋터 대체
@Getter
@Setter
public class HelloData {

    private String userName;
    private int age;

}
