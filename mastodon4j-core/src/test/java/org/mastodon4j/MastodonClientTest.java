/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 Mastodon4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.mastodon4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mastodon4j.core.MastodonClient;
import org.mastodon4j.core.api.MastodonApi;
import org.mastodon4j.core.api.entities.AccessToken;
import org.mastodon4j.core.api.entities.Search;
import org.mastodon4j.core.api.entities.Status;

import static org.assertj.core.api.Assertions.assertThat;

class MastodonClientTest {
    MastodonApi client;

    @BeforeEach
    void prepare() {
        AccessToken accessToken = AccessToken.create(System.getenv("MASTODON_ACCESS_TOKEN"));
        assertThat(accessToken.access_token()).isNotNull();
        client = MastodonClient.create("https://mastodon.social", accessToken);
    }

    @Test
    void search() {
        Search search = client.search("@reinhapa");
        System.out.println(search);
        assertThat(search).isNotNull();
    }

    @Test
    void statuses() {
        Status status = client.statuses().getStatus("109955528323107565");
        System.out.println(status);
        assertThat(status).isNotNull();
        assertThat(status.account()).isNotNull().satisfies(account -> {
            assertThat(account.id()).isEqualTo("109258482743235692");
            assertThat(account.username()).isEqualTo("reinhapa");
        });
    }
}
