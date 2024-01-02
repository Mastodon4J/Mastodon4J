/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2024 Mastodon4J
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
package org.mastodon4j.core.impl;

import org.mastodon4j.core.api.BaseStreaming;
import org.mastodon4j.core.api.EventStream;
import org.mastodon4j.core.api.Streaming;

import java.net.URI;
import java.net.http.WebSocket;

public class MastodonStreaming implements Streaming {
    private final BaseStreaming baseStreaming;
    private final WebSocket.Builder websocketBuilder;
    private final URI socketUri;

    public MastodonStreaming(BaseStreaming baseStreaming, WebSocket.Builder websocketBuilder, String baseStreamingUri) {
        this.baseStreaming = baseStreaming;
        this.websocketBuilder = websocketBuilder;
        this.socketUri = URI.create(baseStreamingUri + "/api/v1/streaming");
    }

    @Override
    public String health() {
        return baseStreaming.health();
    }

    @Override
    public EventStream stream() {
        final MastodonEventStream mastodonEventStream = new MastodonEventStream();
        websocketBuilder.buildAsync(socketUri, mastodonEventStream).join();
        return mastodonEventStream;
    }
}
