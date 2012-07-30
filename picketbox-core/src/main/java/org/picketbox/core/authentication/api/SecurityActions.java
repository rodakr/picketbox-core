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

package org.picketbox.core.authentication.api;

import java.security.AccessController;
import java.security.PrivilegedAction;


/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 *
 */
public final class SecurityActions {

    static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    public static AuthenticationProvider newAuthenticationProviderInstance(final String className) {
        return AccessController.doPrivileged(new PrivilegedAction<AuthenticationProvider>() {

            public AuthenticationProvider run() {
                try {
                    return (AuthenticationProvider) getContextClassLoader().loadClass(className).newInstance();
                } catch (ClassCastException cce) {
                    throw new SecurityException("Instance of " + className + " is not a type of AuthenticationProvider.");
                } catch (Exception e) {
                    throw new SecurityException("Unable to load class " + className);
                }
            }
        });
    }

    public static SecurityRealm newSecurityRealm(final String className) {
        return AccessController.doPrivileged(new PrivilegedAction<SecurityRealm>() {

            public SecurityRealm run() {
                try {
                    return (SecurityRealm) getContextClassLoader().loadClass(className).newInstance();
                } catch (ClassCastException cce) {
                    throw new SecurityException("Instance of " + className + " is not a type of AuthenticationProvider.");
                } catch (Exception e) {
                    throw new SecurityException("Unable to load class " + className);
                }
            }
        });
    }

}
