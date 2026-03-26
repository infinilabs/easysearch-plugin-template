/* Copyright © INFINI Ltd. All rights reserved.
 * Web: https://infinilabs.com
 * Email: hello#infini.ltd
 * SPDX-License-Identifier: Apache-2.0 */

package org.easysearch.plugin.hello.rest;

import org.easysearch.rest.RestRequest;
import org.easysearch.test.EasySearchTestCase;

import java.util.List;

public class RestHelloActionTests extends EasySearchTestCase {

    public void testGetName() {
        RestHelloAction action = new RestHelloAction();

        assertEquals("hello_action", action.getName());
    }

    public void testRoutes() {
        RestHelloAction action = new RestHelloAction();
        List<org.easysearch.rest.BaseRestHandler.Route> routes = action.routes();

        assertEquals(1, routes.size());
        assertEquals(RestRequest.Method.GET, routes.get(0).getMethod());
        assertEquals("/_hello", routes.get(0).getPath());
    }
}

