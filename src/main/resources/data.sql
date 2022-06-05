-- 스프링부트 실행 시 함께 실행되는 쿼리가 담겨 있는 곳 (LineRepositoryTest.saveWithLine() 같은 기능)
-- 테이블 못 찾는다는 에러 발생 시 추가 : spring.jpa.defer-datasource-initialization: true
INSERT INTO line (id, name) VALUES (5, '7호선');
INSERT INTO station (id, line_id, name) VALUES (1, 5, '면목역');
