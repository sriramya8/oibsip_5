<%--
  Created by IntelliJ IDEA.
  User: SriRamya Nemani
  Date: 28-Jun-24
  Time: 5:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>PDF Viewer</title>
</head>
<body>
<iframe src="data:application/pdf;base64,${pdfBytes}" width="100%" height="100%"></iframe>
</body>
</html>

