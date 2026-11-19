/* Copyright © INFINI Ltd. All rights reserved.
 * Web: https://infinilabs.com
 * Email: hello#infini.ltd
 * SPDX-License-Identifier: Apache-2.0 */

package org.easysearch.plugin.hello.rest;

import org.easysearch.client.node.NodeClient;
import org.easysearch.cluster.ClusterName;
import org.easysearch.common.xcontent.XContentBuilder;
import org.easysearch.node.Node;
import org.easysearch.rest.BaseRestHandler;
import org.easysearch.rest.BytesRestResponse;
import org.easysearch.rest.RestRequest;
import org.easysearch.rest.RestResponse;
import org.easysearch.rest.RestStatus;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.easysearch.rest.RestRequest.Method.GET;

/**
 * REST Action for the /_hello endpoint.
 * Returns a greeting message along with cluster information.
 */
public class RestHelloAction extends BaseRestHandler {

    /**
     * Default constructor for RestHelloAction.
     */
    public RestHelloAction() {
        super();
    }

    @Override
    public String getName() {
        return "hello_action";
    }

    @Override
    public List<Route> routes() {
        return Collections.singletonList(
                new Route(GET, "/_hello")
        );
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        String name = request.param("name", "World");
        String clusterName = ClusterName.CLUSTER_NAME_SETTING.get(client.settings()).value();
        String nodeName = Node.NODE_NAME_SETTING.get(client.settings());

        return channel -> {
            try (XContentBuilder builder = channel.newBuilder()) {
                builder.startObject();
                builder.field("message", "Hello " + name);
                builder.field("cluster_name", clusterName);
                builder.field("node_name", nodeName);
                builder.field("timestamp", Instant.now().toEpochMilli());
                builder.endObject();
                RestResponse response = new BytesRestResponse(RestStatus.OK, builder);
                channel.sendResponse(response);
            }
        };
    }
}
