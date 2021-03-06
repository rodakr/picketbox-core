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
package org.picketbox.core.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.picketbox.core.AbstractPicketBoxLifeCycle;

/**
 * Abstract class for implementations of {@link SessionStore}
 *
 * @author Pedro Silva
 * @author anil saldhana
 * @since Aug 22, 2012
 */
public abstract class AbstractSessionStore extends AbstractPicketBoxLifeCycle implements SessionStore {

    protected Map<Serializable, PicketBoxSession> sessions = new HashMap<Serializable, PicketBoxSession>();

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.session.SessionStore#load(org.picketbox.core.session.SessionId)
     */
    @Override
    public PicketBoxSession load(SessionId<? extends Serializable> key) {
        return this.sessions.get(key.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.session.SessionStore#store(org.picketbox.core.session.PicketBoxSession)
     */
    @Override
    public void store(PicketBoxSession session) {
        this.sessions.put(session.getId().getId(), session);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.session.SessionStore#remove(org.picketbox.core.session.SessionId)
     */
    @Override
    public void remove(SessionId<? extends Serializable> id) {
        this.sessions.remove(id.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.picketbox.core.session.SessionStore#update(org.picketbox.core.session.PicketBoxSession)
     */
    @Override
    public void update(PicketBoxSession session) {
        this.sessions.put(session.getId().getId(), session);
    }

    @Override
    protected void doStart() {
    }

    @Override
    protected void doStop() {
        this.sessions.clear();
        this.sessions = null;
    }
}