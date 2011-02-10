/*
 * Copyright (c) 2002-2010, Mairie de Paris
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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;


/**
 * This class provides Data Access methods for Wrapper objects
 * @author lenaini
 */
public class WrapperDAO implements IWrapperDAO
{
    // Constants
    private static final String SQL_QUERY_NEWPK = "SELECT max( id_wrapper ) FROM wrapper ";
    private static final String SQL_QUERY_SELECT = "SELECT id_wrapper, description, wrapper_url, wrapper_styles, status, workgroup_key, role FROM wrapper WHERE id_wrapper = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_wrapper, description, wrapper_url, wrapper_styles, status, workgroup_key, role FROM wrapper ORDER BY description, id_wrapper DESC";
    private static final String SQL_QUERY_SELECT_ENABLED = "SELECT id_wrapper, description, wrapper_url, wrapper_styles, status, workgroup_key, role FROM wrapper WHERE id_wrapper = ? AND status = 0 ";
    private static final String SQL_QUERY_SELECT_ENABLED_WRAPPER_LIST = "SELECT id_wrapper, description, wrapper_url, wrapper_styles, status, workgroup_key, role FROM wrapper WHERE status = 0 ORDER BY description, id_wrapper DESC";
    private static final String SQL_QUERY_INSERT = "INSERT INTO wrapper ( id_wrapper , description, wrapper_url, wrapper_styles, status, workgroup_key, role )  VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM wrapper WHERE id_wrapper = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE wrapper SET description = ? , wrapper_url = ?, wrapper_styles = ? ,status = ?, workgroup_key = ?, role = ?  WHERE id_wrapper = ?  ";

    ///////////////////////////////////////////////////////////////////////////////////////
    //Access methods to data

    /**
     * Generates a new primary key
     * @param plugin The plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEWPK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;

        daoUtil.free(  );

        return nKey;
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods using a dynamic pool

    /**
     * Insert a new record in the table.
     *
     * @param wrapper The wrapper object
     * @param plugin The plugin
     */
    public void insert( Wrapper wrapper, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        wrapper.setId( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, wrapper.getId(  ) );
        daoUtil.setString( 2, wrapper.getDescription(  ) );
        daoUtil.setString( 3, wrapper.getWrapperUrl(  ) );
        daoUtil.setString( 4, wrapper.getWrapperStyles(  ) );
        daoUtil.setInt( 5, wrapper.getStatus(  ) );
        daoUtil.setString( 6, wrapper.getWorkgroup(  ) );
        daoUtil.setString( 7, wrapper.getRole(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the data of Wrapper from the table
     * @param nWrapperId The identifier of Wrapper
     * @param plugin The plugin
     * @return the instance of the Wrapper
     */
    public Wrapper load( int nWrapperId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nWrapperId );
        daoUtil.executeQuery(  );

        Wrapper wrapper = null;

        if ( daoUtil.next(  ) )
        {
            wrapper = new Wrapper(  );
            wrapper.setId( daoUtil.getInt( 1 ) );
            wrapper.setDescription( daoUtil.getString( 2 ) );
            wrapper.setWrapperUrl( daoUtil.getString( 3 ) );
            wrapper.setWrapperStyles( daoUtil.getString( 4 ) );
            wrapper.setStatus( daoUtil.getInt( 5 ) );
            wrapper.setWorkgroup( daoUtil.getString( 6 ) );
            wrapper.setRole( daoUtil.getString( 7 ) );
        }

        daoUtil.free(  );

        return wrapper;
    }

    /**
     * Delete a record from the table
     * @param wrapper The Wrapper object
     * @param plugin The plugin
     */
    public void delete( Wrapper wrapper, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, wrapper.getId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     * @param wrapper The reference of wrapper
     * @param plugin The plugin
     */
    public void store( Wrapper wrapper, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nWrapperId = wrapper.getId(  );

        daoUtil.setString( 1, wrapper.getDescription(  ) );
        daoUtil.setString( 2, wrapper.getWrapperUrl(  ) );
        daoUtil.setString( 3, wrapper.getWrapperStyles(  ) );
        daoUtil.setInt( 4, wrapper.getStatus(  ) );
        daoUtil.setString( 5, wrapper.getWorkgroup(  ) );
        daoUtil.setString( 6, wrapper.getRole(  ) );
        daoUtil.setInt( 7, nWrapperId );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Load the list of wrappers
     *
     * @param plugin The plugin
     * @return The Collection of the Wrappers
     */
    public Collection<Wrapper> selectAll( Plugin plugin )
    {
        Collection<Wrapper> wrapperList = new ArrayList<Wrapper>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Wrapper wrapper = new Wrapper(  );
            wrapper.setId( daoUtil.getInt( 1 ) );
            wrapper.setDescription( daoUtil.getString( 2 ) );
            wrapper.setWrapperUrl( daoUtil.getString( 3 ) );
            wrapper.setWrapperStyles( daoUtil.getString( 4 ) );
            wrapper.setStatus( daoUtil.getInt( 5 ) );
            wrapper.setWorkgroup( daoUtil.getString( 6 ) );
            wrapper.setRole( daoUtil.getString( 7 ) );
            wrapperList.add( wrapper );
        }

        daoUtil.free(  );

        return wrapperList;
    }

    /**
     * Load enabled wrapper
     * @param nWrapperId The page id
     * @param plugin The plugin
     * @return The Collection of the Wrappers
     */
    public Wrapper selectEnabledWrapper( int nWrapperId, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ENABLED, plugin );
        daoUtil.setInt( 1, nWrapperId );
        daoUtil.executeQuery(  );

        Wrapper wrapper = null;

        if ( daoUtil.next(  ) )
        {
            wrapper = new Wrapper(  );
            wrapper.setId( daoUtil.getInt( 1 ) );
            wrapper.setDescription( daoUtil.getString( 2 ) );
            wrapper.setWrapperUrl( daoUtil.getString( 3 ) );
            wrapper.setWrapperStyles( daoUtil.getString( 4 ) );
            wrapper.setStatus( daoUtil.getInt( 5 ) );
            wrapper.setWorkgroup( daoUtil.getString( 6 ) );
            wrapper.setRole( daoUtil.getString( 7 ) );
        }

        daoUtil.free(  );

        return wrapper;
    }

    /**
     * Load the list of wrappers
     *
     * @param plugin The plugin
     * @return The Collection of the Wrappers
     */
    public Collection<Wrapper> selectEnabledWrapperList( Plugin plugin )
    {
        Collection<Wrapper> wrapperList = new ArrayList<Wrapper>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ENABLED_WRAPPER_LIST, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            Wrapper wrapper = new Wrapper(  );
            wrapper.setId( daoUtil.getInt( 1 ) );
            wrapper.setDescription( daoUtil.getString( 2 ) );
            wrapper.setWrapperUrl( daoUtil.getString( 3 ) );
            wrapper.setWrapperStyles( daoUtil.getString( 4 ) );
            wrapper.setStatus( daoUtil.getInt( 5 ) );
            wrapper.setWorkgroup( daoUtil.getString( 6 ) );
            wrapper.setRole( daoUtil.getString( 7 ) );
            wrapperList.add( wrapper );
        }

        daoUtil.free(  );

        return wrapperList;
    }
}