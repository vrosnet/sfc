/*
 * Copyright (c) 2016 Ericsson Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.sfc.genius.impl.handlers.writers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class SfcGeniusDpnIfWriterTest {

    Map<BigInteger, Set<String>> dpnInterfaces;
    SfcGeniusDpnIfWriter sfcGeniusDpnIfWriter;
    BigInteger dpnIdWithOneInterface = BigInteger.valueOf(9);
    BigInteger dpnIdWithTwoInterfaces = BigInteger.valueOf(99);
    String existingInterface = "IF1";

    @Before
    public void setup() {
        dpnInterfaces = new HashMap<>();
        dpnInterfaces.put(dpnIdWithOneInterface, new HashSet<>(Arrays.asList(existingInterface)));
        dpnInterfaces.put(dpnIdWithTwoInterfaces, new HashSet<>(Arrays.asList(existingInterface, "IF2")));
        sfcGeniusDpnIfWriter = new SfcGeniusDpnIfWriter(dpnInterfaces);
    }

    @Test
    public void removeInterfaceFromDpnLastOfDpn() throws Exception {
        int oldSize = dpnInterfaces.size();

        Optional<BigInteger> oldDpnId =
                sfcGeniusDpnIfWriter.removeInterfaceFromDpn(dpnIdWithOneInterface, existingInterface).get();

        assertThat(dpnInterfaces.size(), is(oldSize - 1));
        assertThat(oldDpnId.isPresent(), is(true));
        assertThat(oldDpnId.get(), is(dpnIdWithOneInterface));
    }

    @Test
    public void removeInterfaceFromDpnNotLastOfDpn() throws Exception {
        int oldSize = dpnInterfaces.size();

        Optional<BigInteger> oldDpnId =
                sfcGeniusDpnIfWriter.removeInterfaceFromDpn(dpnIdWithTwoInterfaces, existingInterface).get();

        assertThat(dpnInterfaces.size(), is(oldSize));
        assertThat(dpnInterfaces.get(dpnIdWithTwoInterfaces).contains(existingInterface), is(false));
        assertThat(oldDpnId.isPresent(), is(false));
    }

}
