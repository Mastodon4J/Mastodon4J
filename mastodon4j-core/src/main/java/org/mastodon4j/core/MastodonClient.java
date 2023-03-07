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
package org.mastodon4j.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.http2client.Http2Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.mastodon4j.core.api.*;
import org.mastodon4j.core.api.entities.AccessToken;
import org.mastodon4j.core.api.entities.Instance;
import org.mastodon4j.core.api.entities.Search;

import java.util.Map;

import static java.util.Objects.requireNonNull;

public class MastodonClient implements MastodonApi {
    private final Feign.Builder builder;
    private final String restUrl;
    private final Globals globals;

    private Apps apps;
    private Lists lists;
    private Notifications notifications;
    private Statuses statuses;
    private Streaming streaming;
    private Timelines timelines;

    MastodonClient(final Feign.Builder builder, String restUrl) {
        this.builder = builder;
        this.restUrl = restUrl;
        this.globals = builder.target(Globals.class, restUrl);
    }

    /**
     * Creates an mastodon API instance for the given restUrl and access token.
     *
     * @param restUrl     the base URL of a mastodon server
     * @param accessToken the access token for the given instance
     * @return a new API ineraction instance
     */
    public static MastodonApi create(String restUrl, AccessToken accessToken) {
        requireNonNull(restUrl);
        requireNonNull(accessToken);

        final ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);

        final Feign.Builder builder = Feign.builder()
                .client(new Http2Client())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .requestInterceptor(template -> template.header("User-Agent", "Mastodon4J"))
                .requestInterceptor(template -> template.header("Authorization", accessToken.authorization()));

        return new MastodonClient(builder, restUrl);
    }

    @Override
    public Accounts accounts() {
        return builder.target(Accounts.class, restUrl);
    }

    @Override
    public Apps apps() {
        if (apps == null) {
            apps = builder.target(Apps.class, restUrl);
        }
        return apps;
    }

    @Override
    public Lists lists() {
        if (lists == null) {
            lists = builder.target(Lists.class, restUrl);
        }
        return lists;
    }

    @Override
    public Notifications notifications() {
        if (notifications == null) {
            notifications = builder.target(Notifications.class, restUrl);
        }
        return notifications;
    }

    @Override
    public Statuses statuses() {
        if (statuses == null) {
            statuses = builder.target(Statuses.class, restUrl);
        }
        return statuses;
    }

    @Override
    public Streaming streaming() {
        if (streaming == null) {
            streaming = builder.target(Streaming.class, restUrl);
        }
        return streaming;
    }

    @Override
    public Timelines timelines() {
        if (timelines == null) {
            timelines = builder.target(Timelines.class, restUrl);
        }
        return timelines;
    }

    @Override
    public Instance instance() {
        return globals.instance();
    }

    @Override
    public Search search(String query) {
        return globals.search(QueryOptions.of(query));
    }

    @Override
    public Search search(QueryOptions queryOptions) {
        return globals.search(queryOptions);
    }
}
