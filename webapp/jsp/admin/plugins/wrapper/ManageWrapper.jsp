<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="wrapper" scope="session" class="fr.paris.lutece.plugins.wrapper.web.WrapperJspBean" />

<% wrapper.init( request, wrapper.RIGHT_MANAGE_WRAPPER ); %>
<%= wrapper.getManageWrapper ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>