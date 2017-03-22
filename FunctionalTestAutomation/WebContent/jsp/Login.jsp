<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Functional Test Automation - Login</title>
</head>
<body>
	<f:view>
		<h:form>
			<table>
				<tr>
					<td>Login Name :</td>
					<td><h:inputText id="Login" value="#{userBean.loginName}" /></td>
				</tr>
				<tr>
					<td>Password :</td>
					<td><h:inputSecret id="password" value="#{userBean.password}" />
					</td>
				</tr>
				<tr>
					<td><h:commandButton value="Login"
							action="#{controller.login}" id="submit"></h:commandButton></td>
				</tr>
			</table>
		</h:form>
	</f:view>
</body>
</html>