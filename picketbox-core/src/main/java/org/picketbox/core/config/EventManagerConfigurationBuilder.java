/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.picketbox.core.config;

import java.util.ArrayList;
import java.util.List;

import org.picketbox.core.authentication.AuthenticationEventManager;
import org.picketbox.core.authentication.event.AuthenticationEventHandler;
import org.picketbox.core.authentication.event.DefaultAuthenticationEventManager;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 *
 */
public class EventManagerConfigurationBuilder extends AbstractConfigurationBuilder<EventManagerConfiguration> {

    private AuthenticationEventManager manager;
    private List<AuthenticationEventHandler> handlers;

    public EventManagerConfigurationBuilder(ConfigurationBuilder builder) {
        super(builder);
        this.handlers = new ArrayList<AuthenticationEventHandler>();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.config.AbstractConfigurationBuilder#setDefaults()
     */
    @Override
    protected void setDefaults() {
        if (manager == null) {
            manager = new DefaultAuthenticationEventManager(this.handlers);
        }
    }

    public EventManagerConfigurationBuilder manager(AuthenticationEventManager eventManager) {
        this.manager = eventManager;
        return this;
    }

    public EventManagerConfigurationBuilder handler(AuthenticationEventHandler authenticationEventHandler) {
        this.handlers.add(authenticationEventHandler);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.config.AbstractConfigurationBuilder#doBuild()
     */
    @Override
    protected EventManagerConfiguration doBuild() {
        return new EventManagerConfiguration(this.manager);
    }

    public void setEventManager(AuthenticationEventManager eventManager) {
        this.manager = eventManager;
    }

}
