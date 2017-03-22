<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TestCase creation</title>
</head>
<body>
	<f:view>
		<h:form id="SerivceForm">
			<table>
				<tr>
					<td><h:inputHidden id="tableSize" value="#{uIBean.listSize}"></h:inputHidden>
						<h:dataTable id="testCaseTableWithData"
							value="#{uIBean.testCaseList}" var="testCase" border="1">
							<f:facet name="header">List</f:facet>
							<h:column>
								<f:facet name="header">
									<h:outputText value="TestCase"></h:outputText>
								</f:facet>
								<h:selectBooleanCheckbox id="checkBox"
									onmousedown="setHiddenValue(this.id)"></h:selectBooleanCheckbox>
								<h:inputHidden id="hiddenVal" value="#{testCase.selected}"></h:inputHidden>
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="TestCase Name"></h:outputText>
								</f:facet>
								<h:inputText value="#{testCase.name}"></h:inputText>
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Service Context"></h:outputText>
								</f:facet>
								<h:inputText value="#{testCase.endPoint}"></h:inputText>
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Input"></h:outputText>
								</f:facet>
								<h:inputTextarea value="#{testCase.input}"></h:inputTextarea>
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Output"></h:outputText>
								</f:facet>
								<h:inputTextarea value="#{testCase.output}"></h:inputTextarea>
							</h:column>
						</h:dataTable></td>
				</tr>
				<tr>
					<td><h:commandButton id="testCaseButton"
							action="#{controller.addTestCase}" value="Add Test Case"></h:commandButton>
						<!--<h:commandButton id="executeTestCases" value="Execute"
							action="#{controller.executeTestSuite}"></h:commandButton>--></td>
				</tr>
			</table>
		</h:form>
	</f:view>
	<script type="text/javascript">
		function setHiddenValue(id) {
			var idSubString = id.substring(0, id.indexOf("checkBox"));
			if (document.getElementById(id).value == "true") {
				document.getElementById(idSubString + "hiddenVal").value = true;
			}
		}
		function onload() {
			var listSize = document.getElementById("SerivceForm:tableSize").value;
			var index = 0;
			while (index < listSize) {
				if (document
						.getElementById("SerivceForm:testCaseTableWithData:"
								+ index + ":hiddenVal").value) {
					document
							.getElementById("SerivceForm:testCaseTableWithData:"
									+ index + ":checkBox") = "true";
				} else {
					document
							.getElementById("SerivceForm:testCaseTableWithData:"
									+ index + ":checkBox") = "false";
				}
				index++;
			}
		}
		onload();
	</script>
</body>
</html>