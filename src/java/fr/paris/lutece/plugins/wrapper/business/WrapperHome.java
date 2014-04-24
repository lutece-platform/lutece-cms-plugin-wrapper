/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.wrapper.business;

import fr.paris.lutece.portal.business.indexeraction.IndexerAction;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.util.Collection;


/**
 * This class provides instances management methods (create, find, ...) for Htmlpage objects
 * @author lenaini
 */
public class WrapperHome
{
    // Static variable pointed at the DAO instance
    private static IWrapperDAO _dao = (IWrapperDAO) SpringContextService.getPluginBean( "wrapper", "wrapperDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private WrapperHome(  )
    {
    }

    /**
      * Creation of an instance of wrapper
      *
      * @param wrapper The instance of the wrapper which contains the informations to store
      * @param plugin The Plugin object
      * @return The  instance of wrapper which has been created with its primary key.
      */
    public static Wrapper create( Wrapper wrapper, Plugin plugin )
    {
        _dao.insert( wrapper, plugin );

        return wrapper;
    }

    /**
     * Update of the wrapper which is specified in parameter
     *
     * @param wrapper The instance of the wrapper which contains the data to store
     * @param plugin The Plugin object
     * @return The instance of the  wrapper which has been updated
     */
    public static Wrapper update( Wrapper wrapper, Plugin plugin )
    {
        _dao.store( wrapper, plugin );
        return wrapper;
    }

    /**
     * Remove the Htmlpage whose identifier is specified in parameter
     * @param wrapper The Htmlpage object to remove
     * @param plugin The Plugin object
     */
    public static void remove( Wrapper wrapper, Plugin plugin )
    {
        _dao.delete( wrapper, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a wrapper whose identifier is specified in parameter
     * @param nKey The Primary key of the wrapper
     * @param plugin The Plugin object
     * @return An instance of wrapper
     */
    public static Wrapper findByPrimaryKey( int nKey, Plugin plugin )
    {
        return _dao.load( nKey, plugin );
    }

    /**
     * Returns a collection of wrappers objects
     * @param plugin The Plugin object
     * @return A collection of wrappers
     */
    public static Collection<Wrapper> findAll( Plugin plugin )
    {
        return _dao.selectAll( plugin );
    }

    /**
     * Returns  wrapper object with valid status
     * @param nKey the page id
     * @param plugin The Plugin object
     * @return A wrapper
     */
    public static Wrapper findEnabledWrapper( int nKey, Plugin plugin )
    {
        return _dao.selectEnabledWrapper( nKey, plugin );
    }

    /**
     * Returns a collection of wrappers objects with valid status
     * @param plugin The Plugin object
     * @return A collection of wrappers
     */
    public static Collection<Wrapper> findEnabledWrapperList( Plugin plugin )
    {
        return _dao.selectEnabledWrapperList( plugin );
    }
}
