/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import java.util.Collection;


/**
 * wrapperInterface
 * @author lenaini
 */
public interface IWrapperDAO
{
    /**
     * Delete a record from the table
     * @param wrapper The Wrapper object
     * @param plugin The plugin
     */
    void delete( Wrapper wrapper, Plugin plugin );

    /**
     * Insert a new record in the table.
     * @param wrapper The wrapper object
     * @param plugin The plugin
     */
    void insert( Wrapper wrapper, Plugin plugin );

    /**
     * Load the data of Wrapper from the table
     * @param nWrapperId The identifier of Wrapper
     * @param plugin The plugin
     * @return the instance of the Wrapper
     */
    Wrapper load( int nWrapperId, Plugin plugin );

    /**
     * Load the data of enabled Wrapper from the table
     * @param nWrapperId The identifier of Wrapper
     * @param plugin The plugin
     * @return the instance of the Wrapper
     */
    Wrapper selectEnabledWrapper( int nWrapperId, Plugin plugin );

    /**
     * Load the list of wrappers
     * @param plugin The plugin
     * @return The Collection of the Wrappers
     */
    Collection<Wrapper> selectAll( Plugin plugin );

    /**
     * Load the list of wrappers with valid status
     * @param plugin The plugin
     * @return The Collection of the Wrappers
     */
    Collection<Wrapper> selectEnabledWrapperList( Plugin plugin );

    /**
     * Update the record in the table
     * @param wrapper The reference of wrapper
     * @param plugin The plugin
     */
    void store( Wrapper wrapper, Plugin plugin );
}
