<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>old id=<%=((Member)request.getAttribute("member")).getId()%></li>
    <li>new id=${member.id}</li>

    <li>old userName=<%=((Member)request.getAttribute("member")).getUserName()%></li>
    <li>new userName=${member.userName}</li>

    <li>old age=<%=((Member)request.getAttribute("member")).getAge()%></li>
    <li>new age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
