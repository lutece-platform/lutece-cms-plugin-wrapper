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
import fr.paris.lutece.portal.service.message.SiteMessage;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import fr.paris.lutece.portal.service.message.SiteMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.xpages.XPage;
import fr.paris.lutece.portal.web.xpages.XPageApplication;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


/**
 * This class manages Wrapper page.
 */
public class WrapperApp implements XPageApplication
{
    //////////////////////////////////////////////////////////////////////////////////////////////
    // Constants

    // Parameters
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_WRAPPER = "wrapper";
    private static final String PARAMETER_WRAPPER_ID = "wrapper_id";

    // Properties
    private static final String PROPERTY_PAGE_TITLE = "wrapper.pageTitle";
    private static final String PROPERTY_PAGE_PATH = "wrapper.pagePathLabel";

    // Messages
    private static final String PROPERTY_MESSAGE_ERROR_WRAPPER = "wrapper.message.pageInvalid";
    private static final String PROPERTY_MESSAGE_NOT_AUTHORIZED = "wrapper.message.notAuthorized";

    // Markers
    private static final String MARK_WRAPPER_LIST = "wrappers_list";
    private static final String MARK_WRAPPER = "wrapper";
    private static final String MARK_PAGE = "page";

    // Templates
    private static final String TEMPLATE_XPAGE_WRAPPER = "skin/plugins/wrapper/page_wrapper.html";
    private static final String TEMPLATE_XPAGE_WRAPPER_LISTS = "skin/plugins/wrapper/wrappers_list.html";

    // Constants
    private static final String EMPTY_STRING = "";

    // private fields
    private Plugin _plugin;

    /** Creates a new instance of WrapperApp */
    public WrapperApp(  )
    {
    }

    /**
     * Returns the content of the page Wrapper. It is composed by a form which to capture the data to send a message to
     * a contact of the portal.
     * @return the Content of the page Contact
     * @param request The http request
     * @param nMode The current mode
     * @param plugin The plugin object
     * @throws fr.paris.lutece.portal.service.message.SiteMessageException Message displayed if an exception occures
     */
    public XPage getPage( HttpServletRequest request, int nMode, Plugin plugin )
        throws SiteMessageException
    {
        XPage page = new XPage(  );

        String strPluginName = request.getParameter( PARAMETER_PAGE );
        _plugin = PluginService.getPlugin( strPluginName );

        page.setTitle( AppPropertiesService.getProperty( PROPERTY_PAGE_TITLE ) );
        page.setPathLabel( AppPropertiesService.getProperty( PROPERTY_PAGE_PATH ) );

        String strWrapperId = request.getParameter( PARAMETER_WRAPPER_ID );

        if ( strWrapperId == null )
        {
            page.setContent( getWrappersLists( request ) );
        }

        if ( ( strWrapperId != null ) )
        {
            page.setContent( getWrapper( request, strWrapperId ) );
        }

        return page;
    }

    private String getWrappersLists( HttpServletRequest request )
        throws SiteMessageException
    {
        HashMap model = new HashMap(  );

        Collection<Wrapper> wrapperList = WrapperHome.findEnabledWrapperList( _plugin );
        Collection<Wrapper> visibleWrapperList = new ArrayList(  ); // filter the list of lists by role

        for ( Wrapper wrapper : wrapperList )
        {
            if ( isVisible( request, wrapper.getRole(  ) ) )
            {
                visibleWrapperList.add( wrapper );
            }
        }

        model.put( MARK_WRAPPER_LIST, visibleWrapperList );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_XPAGE_WRAPPER_LISTS, request.getLocale(  ),
                model );

        return template.getHtml(  );
    }

    /**
     * Returns the wrapper page
     * @param request The Html request
     * @param plugin The plugin
     * @return The Html template
     */
    private String getWrapper( HttpServletRequest request, String strWrapperId )
        throws SiteMessageException
    {
        HashMap model = new HashMap(  );

        int nWrapperId = Integer.parseInt( strWrapperId );
        Wrapper wrapper = WrapperHome.findByPrimaryKey( nWrapperId, _plugin );

        if ( wrapper != null )
        {
            int nStatus = wrapper.getStatus(  );

            if ( ( nStatus == 0 ) && ( isVisible( request, wrapper.getRole(  ) ) ) )
            {
                model.put( MARK_WRAPPER, wrapper );
                model.put( MARK_PAGE, _plugin.getName(  ) );
            }
            else
            {
                SiteMessageService.setMessage( request, PROPERTY_MESSAGE_NOT_AUTHORIZED, SiteMessage.TYPE_ERROR );
            }
        }
        else
        {
            SiteMessageService.setMessage( request, PROPERTY_MESSAGE_ERROR_WRAPPER, SiteMessage.TYPE_ERROR );
        }

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_XPAGE_WRAPPER, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * Checks if the page is visible for the current user
     * @param request The HTTP request
     * @return true if the page could be shown to the user
     * @since v1.3.1
     */
    private boolean isVisible( HttpServletRequest request, String strRole )
    {
        if ( ( strRole == null ) || ( strRole.trim(  ).equals( EMPTY_STRING ) ) )
        {
            return true;
        }

        if ( !strRole.equals( Wrapper.ROLE_NONE ) && SecurityService.isAuthenticationEnable(  ) )
        {
            return SecurityService.getInstance(  ).isUserInRole( request, strRole );
        }

        return true;
    }
}