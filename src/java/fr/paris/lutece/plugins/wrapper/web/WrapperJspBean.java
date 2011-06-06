/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.wrapper.web;

import fr.paris.lutece.plugins.wrapper.business.Wrapper;
import fr.paris.lutece.plugins.wrapper.business.WrapperHome;
import fr.paris.lutece.portal.business.role.RoleHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author lenaini
 */
public class WrapperJspBean extends PluginAdminPageJspBean
{
    // Right
    public static final String RIGHT_MANAGE_WRAPPER = "WRAPPER_MANAGEMENT";

    // properties for page titles
    private static final String PROPERTY_PAGE_TITLE_WRAPPER_LIST = "wrapper.manage_wrapper.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY = "wrapper.modify_wrapper.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE = "wrapper.create_wrapper.pageTitle";

    // Properties
    private static final String PROPERTY_DEFAULT_WRAPPER_LIST_PER_PAGE = "wrapper.wrapperList.itemsPerPage";

    // Messages
    private static final String MESSAGE_CONFIRM_REMOVE_WRAPPER = "wrapper.message.confirmRemoveWrapper";

    // Markers
    private static final String MARK_LIST_WRAPPER_LIST = "wrapper_list";
    private static final String MARK_NB_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_WORKGROUPS_LIST = "workgroups_list";    
    private static final String MARK_LOCALE = "locale";    
    private static final String MARK_WRAPPER = "wrapper";
    private static final String MARK_ROLES_LIST = "roles_list";
    private static final String MARK_PAGINATOR = "paginator";

    // parameters
    private static final String PARAMETER_WRAPPER_ID = "id_wrapper";
    private static final String PARAMETER_WRAPPER_DESCRIPTION = "description";
    private static final String PARAMETER_WRAPPER_STATUS = "status";
    private static final String PARAMETER_WRAPPER_WORKGROUP = "workgroup";
    private static final String PARAMETER_WRAPPER_URL = "wrapper_url";
    private static final String PARAMETER_WRAPPER_STYLES = "wrapper_styles";
    private static final String PARAMETER_ID_WRAPPER_LIST = "wrapper_list_id";
    private static final String PARAMETER_WRAPPER_ROLE = "role";
    private static final String PARAMETER_PAGE_INDEX = "page_index";

    // templates
    private static final String TEMPLATE_MANAGE_WRAPPER = "/admin/plugins/wrapper/manage_wrapper.html";
    private static final String TEMPLATE_CREATE_WRAPPER = "/admin/plugins/wrapper/create_wrapper.html";
    private static final String TEMPLATE_MODIFY_WRAPPER = "/admin/plugins/wrapper/modify_wrapper.html";

    // Jsp Definition
    private static final String JSP_DO_REMOVE_WRAPPER = "jsp/admin/plugins/wrapper/DoRemoveWrapper.jsp";
    private static final String JSP_REDIRECT_TO_MANAGE_WRAPPER = "ManageWrapper.jsp";

    //Variables
    private int _nDefaultItemsPerPage;
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;

    /**
     * returns the template of the WrapperLists management
     * @param request The HttpRequest
     * @return template of lists management
     */
    public String getManageWrapper( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_WRAPPER_LIST );

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_WRAPPER_LIST_PER_PAGE, 50 );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        Collection<Wrapper> listWrapperList = WrapperHome.findAll( getPlugin(  ) );
        listWrapperList = AdminWorkgroupService.getAuthorizedCollection( listWrapperList, getUser(  ) );

        Paginator paginator = new Paginator( (List<Wrapper>) listWrapperList, _nItemsPerPage, getHomeUrl( request ),
                PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_NB_ITEMS_PER_PAGE, "" + _nItemsPerPage );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_WRAPPER_LIST, paginator.getPageItems(  ) );

        HtmlTemplate templateList = AppTemplateService.getTemplate( TEMPLATE_MANAGE_WRAPPER, getLocale(  ), model );

        return getAdminPage( templateList.getHtml(  ) );
    }

    /**
      * Returns the form to create a wrapper
      *
      * @param request The Http request
      * @return the html code of the wrapper form
      */
    public String getCreateWrapper( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_CREATE );

        Map<String, Object> model = new HashMap<String, Object>(  );
        ReferenceList workgroupsList = AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) );
        model.put( MARK_WORKGROUPS_LIST, workgroupsList );
        model.put( MARK_LOCALE, getLocale(  ) );        
        model.put( MARK_ROLES_LIST, RoleHome.getRolesList(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_CREATE_WRAPPER, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the data capture form of a new wrapper
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doCreateWrapper( HttpServletRequest request )
    {
        String strDescription = request.getParameter( PARAMETER_WRAPPER_DESCRIPTION );
        String strWrapperUrl = request.getParameter( PARAMETER_WRAPPER_URL );
        String strWrapperStyles = request.getParameter( PARAMETER_WRAPPER_STYLES );
        String strStatus = request.getParameter( PARAMETER_WRAPPER_STATUS );
        String strWorkgroup = request.getParameter( PARAMETER_WRAPPER_WORKGROUP );
        String strRole = request.getParameter( PARAMETER_WRAPPER_ROLE );

        // Mandatory fields
        if ( ( strDescription == null ) || strDescription.trim(  ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        Wrapper wrapper = new Wrapper(  );

        wrapper.setDescription( strDescription );
        wrapper.setWrapperUrl( strWrapperUrl );
        wrapper.setWrapperStyles( strWrapperStyles );
        wrapper.setStatus( Integer.parseInt( strStatus ) );
        wrapper.setWorkgroup( strWorkgroup );
        wrapper.setRole( strRole );

        WrapperHome.create( wrapper, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the Wrappers
        return JSP_REDIRECT_TO_MANAGE_WRAPPER;
    }

    /**
     * Process the data capture form of a new wrapper from copy of other
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    public String doDuplicateWrapper( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_ID ) );

        Wrapper wrapper = WrapperHome.findByPrimaryKey( nId, getPlugin(  ) );

        Wrapper duplicateWrapper = new Wrapper(  );
        duplicateWrapper.setDescription( wrapper.getDescription(  ) );
        duplicateWrapper.setWrapperUrl( wrapper.getWrapperUrl(  ) );
        duplicateWrapper.setWrapperStyles( wrapper.getWrapperStyles(  ) );
        duplicateWrapper.setStatus( wrapper.getStatus(  ) );
        duplicateWrapper.setWorkgroup( wrapper.getWorkgroup(  ) );
        duplicateWrapper.setRole( wrapper.getRole(  ) );

        WrapperHome.create( duplicateWrapper, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the Wrappers
        return JSP_REDIRECT_TO_MANAGE_WRAPPER;
    }

    /**
     * Returns the form to update info about a wrapper
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    public String getModifyWrapper( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_PAGE_TITLE_MODIFY );

        int nId = Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_ID ) );
        Wrapper wrapper = WrapperHome.findByPrimaryKey( nId, getPlugin(  ) );

        Map<String, Object> model = new HashMap<String, Object>(  );
        ReferenceList workgroupsList = AdminWorkgroupService.getUserWorkgroups( getUser(  ), getLocale(  ) );
        model.put( MARK_WORKGROUPS_LIST, workgroupsList );        
        model.put( MARK_LOCALE, getLocale(  ) );
        model.put( MARK_WRAPPER, wrapper );
        model.put( MARK_ROLES_LIST, RoleHome.getRolesList(  ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_WRAPPER, getLocale(  ), model );

        return getAdminPage( template.getHtml(  ) );
    }

    /**
     * Process the change form of a wrapper
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    public String doModifyWrapper( HttpServletRequest request )
    {
        // Mandatory fields
        if ( request.getParameter( PARAMETER_WRAPPER_DESCRIPTION ).equals( "" ) ||
                request.getParameter( PARAMETER_WRAPPER_URL ).equals( "" ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nId = Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_ID ) );

        Wrapper wrapper = WrapperHome.findByPrimaryKey( nId, getPlugin(  ) );
        wrapper.setDescription( request.getParameter( PARAMETER_WRAPPER_DESCRIPTION ) );
        wrapper.setWrapperUrl( request.getParameter( PARAMETER_WRAPPER_URL ) );
        wrapper.setWrapperStyles( request.getParameter( PARAMETER_WRAPPER_STYLES ) );
        wrapper.setStatus( Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_STATUS ) ) );
        wrapper.setWorkgroup( request.getParameter( PARAMETER_WRAPPER_WORKGROUP ) );
        wrapper.setRole( request.getParameter( PARAMETER_WRAPPER_ROLE ) );

        WrapperHome.update( wrapper, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the Wrappers
        return JSP_REDIRECT_TO_MANAGE_WRAPPER;
    }

    /**
     * Manages the removal form of a wrapper whose identifier is in the http request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    public String getConfirmRemoveWrapper( HttpServletRequest request )
    {
        int nIdWrapper = Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_ID ) );

        UrlItem url = new UrlItem( JSP_DO_REMOVE_WRAPPER );
        url.addParameter( PARAMETER_WRAPPER_ID, nIdWrapper );
        url.addParameter( PARAMETER_ID_WRAPPER_LIST, request.getParameter( PARAMETER_ID_WRAPPER_LIST ) );

        Object[] args = { request.getParameter( PARAMETER_WRAPPER_DESCRIPTION ) };

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_WRAPPER, args, url.getUrl(  ),
            AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Treats the removal form of a wrapper
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage wrappers
     */
    public String doRemoveWrapper( HttpServletRequest request )
    {
        int nIdWrapper = Integer.parseInt( request.getParameter( PARAMETER_WRAPPER_ID ) );

        Wrapper wrapper = WrapperHome.findByPrimaryKey( nIdWrapper, getPlugin(  ) );
        WrapperHome.remove( wrapper, getPlugin(  ) );

        // if the operation occurred well, redirects towards the list of the Wrappers
        return JSP_REDIRECT_TO_MANAGE_WRAPPER;
    }
}
