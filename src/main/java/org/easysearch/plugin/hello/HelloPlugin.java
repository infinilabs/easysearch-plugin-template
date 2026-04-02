/* Copyright © INFINI Ltd. All rights reserved.
 * Web: https://infinilabs.com
 * Email: hello#infini.ltd
 * SPDX-License-Identifier: Apache-2.0 */

package org.easysearch.plugin.hello;

import org.easysearch.action.ActionRequest;
import org.easysearch.action.ActionResponse;
import org.easysearch.cluster.metadata.IndexNameExpressionResolver;
import org.easysearch.cluster.node.DiscoveryNodes;
import org.easysearch.common.settings.ClusterSettings;
import org.easysearch.common.settings.IndexScopedSettings;
import org.easysearch.common.settings.Settings;
import org.easysearch.common.settings.SettingsFilter;
import org.easysearch.plugins.ActionPlugin;
import org.easysearch.plugins.Plugin;
import org.easysearch.rest.RestController;
import org.easysearch.rest.RestHandler;
import org.easysearch.plugin.hello.rest.RestHelloAction;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Hello Plugin - A simple plugin that exposes a REST endpoint
 * for greeting and cluster information.
 */
public class HelloPlugin extends Plugin implements ActionPlugin {

    /**
     * Default constructor for HelloPlugin.
     */
    public HelloPlugin() {
        super();
    }

    @Override
    public List<RestHandler> getRestHandlers(
            Settings settings,
            RestController restController,
            ClusterSettings clusterSettings,
            IndexScopedSettings indexScopedSettings,
            SettingsFilter settingsFilter,
            IndexNameExpressionResolver indexNameExpressionResolver,
            Supplier<DiscoveryNodes> discoveryNodes) {
        return Collections.singletonList(new RestHelloAction());
    }

    @Override
    public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
        return Collections.emptyList();
    }
}
