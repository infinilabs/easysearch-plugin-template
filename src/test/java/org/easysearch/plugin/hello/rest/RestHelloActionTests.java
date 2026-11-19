/* Copyright © INFINI Ltd. All rights reserved.
 * Web: https://infinilabs.com
 * Email: hello#infini.ltd
 * SPDX-License-Identifier: Apache-2.0 */

package org.easysearch.plugin.hello.rest;

import org.easysearch.client.node.NodeClient;
import org.easysearch.common.settings.Settings;
import org.easysearch.common.xcontent.NamedXContentRegistry;
import org.easysearch.common.xcontent.XContentHelper;
import org.easysearch.common.xcontent.XContentType;
import org.easysearch.rest.RestRequest;
import org.easysearch.rest.RestResponse;
import org.easysearch.rest.RestStatus;
import org.easysearch.test.EasySearchTestCase;
import org.easysearch.test.rest.FakeRestChannel;
import org.easysearch.test.rest.FakeRestRequest;

import java.util.List;
import java.util.Map;

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

    public void testReturnsGreetingAndLocalNodeName() throws Exception {
        Map<String, Object> response = callHello(Map.of("name", "Tom \"T\""));
        assertEquals("Hello Tom \"T\"", response.get("message"));
        assertEquals("test-cluster", response.get("cluster_name"));
        assertEquals("test-node", response.get("node_name"));
    }

    public void testDefaultsToWorld() throws Exception {
        assertEquals("Hello World", callHello(Map.of()).get("message"));
    }

    private Map<String, Object> callHello(Map<String, String> params) throws Exception {
        RestRequest request = new FakeRestRequest.Builder(NamedXContentRegistry.EMPTY).withPath("/_hello").withParams(params).build();
        FakeRestChannel channel = new FakeRestChannel(request, false, 1);
        Settings settings = Settings.builder().put("cluster.name", "test-cluster").put("node.name", "test-node").build();
        try (NodeClient client = new NodeClient(settings, null)) {
            new RestHelloAction().handleRequest(request, channel, client);
        }
        RestResponse response = channel.capturedResponse();
        assertNotNull(response);
        assertEquals(RestStatus.OK, response.status());
        return XContentHelper.convertToMap(response.content(), false, XContentType.JSON).v2();
    }
}

