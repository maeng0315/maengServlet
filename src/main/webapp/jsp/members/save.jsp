<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- import -->
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>

<!-- 자바 코드 블럭 -->
<%
    /* JSP로 작성해도 내부 적으로는 결국 서블릿으로 바뀌기 때문에
    request, response 객체 사용 가능 (예약어) */
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <!-- 자바 코드 출력 블럭 -->
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUserName()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
