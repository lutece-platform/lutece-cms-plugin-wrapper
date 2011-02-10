<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:useBean id="wrapper" scope="session" class="fr.paris.lutece.plugins.wrapper.web.WrapperJspBean" />

<%
    wrapper.init( request, wrapper.RIGHT_MANAGE_WRAPPER );
    response.sendRedirect( wrapper.getConfirmRemoveWrapper( request ) );
%>