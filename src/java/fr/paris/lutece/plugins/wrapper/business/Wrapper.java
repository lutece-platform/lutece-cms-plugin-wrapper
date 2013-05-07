/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
 *  3. Neither the description of 'Mairie de Paris' nor 'Lutece' nor the descriptions of its
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
package fr.paris.lutece.plugins.wrapper.business;

import fr.paris.lutece.plugins.wrapper.service.WrapperWorkgroupRemovalListener;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupResource;
import fr.paris.lutece.portal.service.workgroup.AdminWorkgroupService;
import fr.paris.lutece.portal.service.workgroup.WorkgroupRemovalListenerService;


/**
 * This class represents business object Wrapper
 * @author lenaini
 */
public class Wrapper implements AdminWorkgroupResource
{
    /////////////////////////////////////////////////////////////////////////////////
    // Constants
    public static final String RESOURCE_TYPE = "WRAPPER";
    public static final String ROLE_NONE = "none";
    private static final String EMPTY_STRING = "";
    private static final int ENABLED = 0;
    private static WrapperWorkgroupRemovalListener _listenerWorkgroup;
    private int _nId;
    private int _nStatus;
    private String _strDescription;
    private String _strWrapperUrl;
    private String _strWrapperStyles;
    private String _strWorkgroupKey;
    private String _strAdminWorkgroup;
    private String _strRole;

    /** Creates a new instance of Wrapper */
    public Wrapper(  )
    {
    }

    /**
    * Initialize the WrapperList
    */
    public static void init(  )
    {
        // Create removal listeners and register them
        if ( _listenerWorkgroup == null )
        {
            _listenerWorkgroup = new WrapperWorkgroupRemovalListener(  );
            WorkgroupRemovalListenerService.getService(  ).registerListener( _listenerWorkgroup );
        }
    }

    /**
     * Returns the identifier of this wrapper.
     *
     * @return the wrapper identifier
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     * Sets the identifier of the wrapper to the specified integer.
     *
     * @param nId the new identifier
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the description of this wrapper.
     *
     * @return the wrapper description
     */
    public String getDescription(  )
    {
        return _strDescription;
    }

    /**
     * Sets the description of the wrapper to the specified string.
     *
     * @param strDescription the new description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = ( strDescription == null ) ? EMPTY_STRING : strDescription;
    }

    /**
     * Returns the WrapperUrl of this wrapper.
     *
     * @return the wrapper Url
     */
    public String getWrapperUrl(  )
    {
        return _strWrapperUrl;
    }

    /**
     * Sets the url of the wrapper to the specified string.
     *
     * @param strWrapperUrl the new url
     */
    public void setWrapperUrl( String strWrapperUrl )
    {
        _strWrapperUrl = ( strWrapperUrl == null ) ? EMPTY_STRING : strWrapperUrl;
    }

    /**
     * Returns the WrapperStyles of this wrapper.
     *
     * @return the wrapper Styles
     */
    public String getWrapperStyles(  )
    {
        return _strWrapperStyles;
    }

    /**
     * Sets the Styles of the wrapper to the specified string.
     *
     * @param strWrapperStyles the new Styles
     */
    public void setWrapperStyles( String strWrapperStyles )
    {
        _strWrapperStyles = ( strWrapperStyles == null ) ? EMPTY_STRING : strWrapperStyles;
    }

    /**
     * Returns the status of this wrapper.
     *
     * @return the wrapper status
     */
    public int getStatus(  )
    {
        return _nStatus;
    }

    /**
     * Sets the description of the wrapper to the specified string.
     *
     * @param strDescription the new description
     */
    public void setStatus( int nStatus )
    {
        _nStatus = nStatus;
    }

    /**
     * Gets the contactList role
     * @return contactList's role as a String
     * @since v1.1
     */
    public String getRole(  )
    {
        return _strRole;
    }

    /**
     * allows the contact List to be saw by a role
     * @param strRole The role that can see the contact list
     */
    public void setRole( String strRole )
    {
        _strRole = ( ( strRole == null ) || ( strRole.equals( "" ) ) ) ? ROLE_NONE : strRole;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Workgtoup management

    /**
     * Return the WorkgroupKey Contact Label
     * @return The label of the selected Workgroup Key
     */
    public String getWorkgroupKey(  )
    {
        return _strWorkgroupKey;
    }

    /**
     * Sets the description of the Contact with the specified String
     * @param strWorkgroupKey The workgroup key
     */
    public void setWorkgroupKey( String strWorkgroupKey )
    {
        _strWorkgroupKey = strWorkgroupKey;
    }

    /**
     * Returns the workgroup
     * @return The workgroup
     */
    public String getWorkgroup(  )
    {
        return _strAdminWorkgroup;
    }

    /**
     * Sets the workgroup
     * @param strAdminWorkgroup The workgroup
     */
    public void setWorkgroup( String strAdminWorkgroup )
    {
        _strAdminWorkgroup = AdminWorkgroupService.normalizeWorkgroupKey( strAdminWorkgroup );
    }

    public boolean isEnabled(  )
    {
        return ( _nStatus == ENABLED );
    }
}
