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
package org.mastodon4j.core.api;

import feign.QueryMap;
import feign.RequestLine;
import org.mastodon4j.core.api.entities.Instance;
import org.mastodon4j.core.api.entities.Search;

/**
 * Contains all global related REST call methods.
 */
public interface BaseMastodonApi {
    /**
     * <a href="https://docs.joinmastodon.org/methods/instance/#v2">View server information</a>
     * <p>
     * Obtain general information about the server.
     *
     * @return a instance information object
     */
    @RequestLine("GET /api/v2/instance")
    Instance instance();

    /**
     * <a href="https://docs.joinmastodon.org/methods/search/#v2">Perform a search</a>
     *
     * @param queryOptions query options record containing all query parameters
     * @return a search result instance
     */
    @RequestLine("GET /api/v2/search")
    Search search(@QueryMap QueryOptions queryOptions);

    record QueryOptions(String q,
                        String type,
                        Boolean resolve,
                        Boolean following,
                        String account_id,
                        Boolean exclude_unreviewed,
                        String max_id,
                        String min_id,
                        Integer limit,
                        Integer offset) {

        public static QueryOptions of(String query) {
            return new QueryOptions(query, null, null, null, null, null,
                    null, null, null, null);
        }

        public QueryOptions type(QueryOptions.Type type) {
            return new QueryOptions(q, type.value(), resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions resolve(boolean resolve) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions following(boolean following) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions accountId(String account_id) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions excludeUnreviewed(boolean exclude_unreviewed) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions maxId(String max_id) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions minId(String min_id) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions limit(int limit) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public QueryOptions offset(int offset) {
            return new QueryOptions(q, type, resolve, following, account_id, exclude_unreviewed, max_id, min_id,
                    limit, offset);
        }

        public enum Type {
            ACCOUNTS, HASHTAGS, STATUSES;

            public static QueryOptions.Type ofValue(String value) {
                return QueryOptions.Type.valueOf(value.toUpperCase());
            }

            public String value() {
                return name().toLowerCase();
            }
        }
    }
}
