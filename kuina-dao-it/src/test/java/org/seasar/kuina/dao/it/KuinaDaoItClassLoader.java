/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.kuina.dao.it;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.seasar.framework.exception.IORuntimeException;

import sun.misc.CompoundEnumeration;
import sun.misc.URLClassPath;

/**
 * 
 * @author koichik
 */
public class KuinaDaoItClassLoader extends URLClassLoader {

    protected URLClassPath beforeClassPath;

    protected AccessControlContext acc;

    public KuinaDaoItClassLoader(final String[] beforeClassPath,
            final String[] afterClassPath) {
        super(toURL(afterClassPath), Thread
            .currentThread()
            .getContextClassLoader());
        this.beforeClassPath = new URLClassPath(toURL(beforeClassPath));
        acc = AccessController.getContext();
    }

    protected static URL[] toURL(final String[] paths) {
        try {
            final URL[] urls = new URL[paths.length];
            for (int i = 0; i < paths.length; ++i) {
                urls[i] = new File(paths[i]).toURL();
            }
            return urls;
        } catch (final MalformedURLException e) {
            throw new IORuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public URL getResource(final String name) {
        URL url = (URL) AccessController.doPrivileged(new PrivilegedAction() {

            public Object run() {
                return beforeClassPath.findResource(name, true);
            }
        }, acc);
        if (url != null) {
            url = beforeClassPath.checkURL(url);
        }
        if (url == null) {
            url = super.getResource(name);
        }
        return url;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enumeration<URL> getResources(final String name) throws IOException {
        final Enumeration e = beforeClassPath.findResources(name, true);
        final Enumeration[] tmp = new Enumeration[2];
        tmp[0] = new Enumeration<URL>() {

            private URL url = null;

            private boolean next() {
                if (url != null) {
                    return true;
                }
                do {
                    final URL u =
                        (URL) AccessController.doPrivileged(
                            new PrivilegedAction() {

                                public Object run() {
                                    if (!e.hasMoreElements())
                                        return null;
                                    return e.nextElement();
                                }
                            },
                            acc);
                    if (u == null)
                        break;
                    url = beforeClassPath.checkURL(u);
                } while (url == null);
                return url != null;
            }

            public URL nextElement() {
                if (!next()) {
                    throw new NoSuchElementException();
                }
                final URL u = url;
                url = null;
                return u;
            }

            public boolean hasMoreElements() {
                return next();
            }
        };

        tmp[1] = super.getResources(name);
        return new CompoundEnumeration<URL>(tmp);
    }

}
