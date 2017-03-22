<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Test Case</title>
</head>
<body>
	<f:view>
		<h:form>
			<table>
				<tr>
					<td><h:messages style="color:red;margin:8px;" /></td>
				</tr>
			</table>
			<table>
				<tr>
					<td><h:outputText value="Testcase Name"></h:outputText></td>
					<td><h:inputText id="name" value="#{testCaseBean.name}" /></td>
				</tr>
				<tr>
					<td><h:outputText value="Flow">
						</h:outputText></td>
					<td><h:selectOneMenu id="flows" value="#{testCaseBean.flow}">
							<f:selectItems value="#{testCaseBean.flows}" />
						</h:selectOneMenu></td>
				</tr>
				<tr>
					<td><h:outputText value="Environment"></h:outputText></td>
					<td><h:inputText id="envId" value="#{testCaseBean.env}" /></td>
				</tr>
				<tr>
					<td><h:outputText value="ServiceContext"></h:outputText></td>
					<td><h:inputText id="endPointId"
							value="#{testCaseBean.endPoint}" /></td>
				</tr>
				<tr>
					<td><h:inputTextarea id="input" value="#{testCaseBean.input}"></h:inputTextarea></td>
					<td><h:inputTextarea id="output"
							value="#{testCaseBean.output}"></h:inputTextarea></td>
				</tr>
				<tr>
					<td><h:outputText value="ESN Part Num : " /> <h:selectOneMenu
							id="esnPartNum" value="#{testCaseBean.esnPartNum}"
							valueChangeListener="#{controller.getDependency}"
							onchange="submit()" immediate="true">
							<f:selectItems value="#{testCaseBean.esnPartNumList}" />
						</h:selectOneMenu></td>
					<td><h:outputText value="Sim Part Num : " /> <h:selectOneMenu
							id="simPartNum" value="#{testCaseBean.simPartNum}">
							<f:selectItems value="#{testCaseBean.simPartNumList}" />
						</h:selectOneMenu> <h:outputText value="Pin Part Num :" /> <h:selectOneMenu
							id="pinPartNum" value="#{testCaseBean.pinPartNum}">
							<f:selectItems value="#{testCaseBean.pinPartNumList}" />
						</h:selectOneMenu></td>
				<tr>
				<tr>
					<td>Brand :<h:selectOneMenu id="brand"
							value="#{testCaseBean.brand}">
							<f:selectItems value="#{testCaseBean.brandList}" />
						</h:selectOneMenu></td>
					<td>Channel :<h:selectOneMenu id="channel"
							value="#{testCaseBean.channel}">
							<f:selectItems value="#{testCaseBean.channelList}" />
						</h:selectOneMenu></td>
				</tr>
				<tr>
					<td><h:commandButton id="generate" value="Generate Data"
							action="#{controller.generateAndExecuteTestCase}"></h:commandButton></td>
					<td></td>
				</tr>
				<tr>
					<td><h:commandButton id="submit" value="Execute"
							action="#{controller.executeTestCase}"></h:commandButton></td>
					<td><h:commandButton id="saveCaseButton"
							action="#{controller.saveTestCase}" value="Save Test Case"></h:commandButton></td>

				</tr>
			</table>
		</h:form>
	</f:view>
</body>
</html>