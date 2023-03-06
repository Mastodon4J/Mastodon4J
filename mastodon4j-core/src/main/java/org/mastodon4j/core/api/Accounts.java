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

import feign.Param;
import feign.RequestLine;
import org.mastodon4j.core.api.entities.Account;
import org.mastodon4j.core.api.entities.MList;
import org.mastodon4j.core.api.entities.Status;

import java.util.List;

/**
 * Contains all account related REST call methods.
 *
 * @see <a href="https://docs.joinmastodon.org/methods/accounts/">mastodon/accounts</a>
 */
public interface Accounts {
    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#get">Get account</a>.
     * <p>
     * View information about a profile.
     *
     * @param id the account id
     * @return the account
     */
    @RequestLine("GET /api/v1/accounts/{id}")
    Account get(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#lists">Get lists containing this account</a>.
     * <p>
     * User lists that you have added this account to.
     *
     * @param id the account id
     * @return a list of account statuses
     */
    @RequestLine("GET /api/v1/accounts/{id}/lists")
    List<MList> lists(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#statuses">Get account’s statuses</a>.
     * <p>
     * Statuses posted to the given account.
     *
     * @param id the account id
     * @return a list of account statuses
     */
    @RequestLine("GET /api/v1/accounts/{id}/statuses")
    List<Status> statuses(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#followers">Get account’s followers</a>.
     * <p>
     * Accounts which follow the given account, if network is not hidden by the account owner.
     *
     * @param id the account id
     * @return a list of account followers
     */
    @RequestLine("GET /api/v1/accounts/{id}/followers")
    List<Account> followers(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#following">Get account’s following</a>.
     * <p>
     * Accounts which the given account is following, if network is not hidden by the account owner.
     *
     * @param id the account id
     * @return a list of account's following
     */
    @RequestLine("GET /api/v1/accounts/{id}/following")
    List<Account> following(@Param("id") String id);

    /**
     * <a href="https://docs.joinmastodon.org/methods/accounts/#search">Search for matching accounts</a>.
     * <p>
     * Search for matching accounts by username or display name.
     *
     * @param query the account id
     * @return a list of account's following
     */
    @RequestLine("GET /api/v1/accounts/search?q={query}")
    List<Account> search(@Param("query") String query);
}
