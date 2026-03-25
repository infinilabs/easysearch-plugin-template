/* Copyright © INFINI Ltd. All rights reserved.
 * Web: https://infinilabs.com
 * Email: hello#infini.ltd
 * SPDX-License-Identifier: Apache-2.0 */

package org.easysearch.plugin.hello;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;
import org.easysearch.test.rest.yaml.ClientYamlTestCandidate;
import org.easysearch.test.rest.yaml.ESClientYamlSuiteTestCase;

/**
 * Minimal YAML REST test suite entry for plugin REST tests.
 */
public class HelloClientYamlTestSuiteIT extends ESClientYamlSuiteTestCase {

    public HelloClientYamlTestSuiteIT(@Name("yaml") ClientYamlTestCandidate testCandidate) {
        super(testCandidate);
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() throws Exception {
        return createParameters();
    }
}

