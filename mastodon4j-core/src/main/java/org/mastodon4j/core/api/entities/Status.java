/*
 * SPDX-License-Identifier: MIT
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2025 Mastodon4J
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
package org.mastodon4j.core.api.entities;

import java.time.ZonedDateTime;
import java.util.List;

public record Status(String id,
                     String uri,
                     ZonedDateTime created_at,
                     Account account,
                     String content,
                     String visibility,
                     Boolean sensitive,
                     String spoiler_text,
                     List<MediaAttachment> media_attachments,
                     @Optional Application application,
                     List<Mention> mentions,
                     List<Tag> tags,
                     List<CustomEmoji> emojis,
                     Integer reblogs_count,
                     Integer favourites_count,
                     Integer replies_count,
                     String url,
                     @Optional String in_reply_to_id,
                     @Optional String in_reply_to_account_id,
                     @Optional Status reblog,
                     Poll poll,
                     @Optional PreviewCard card,
                     @Optional String language,
                     @Optional String text,
                     @Optional ZonedDateTime edited_at,
                     @Optional Boolean favourited,
                     @Optional Boolean reblogged,
                     @Optional Boolean muted,
                     @Optional Boolean bookmarked,
                     @Optional Boolean pinned,
                     @Optional List<FilterResult> filtered) {

    public record Application(String name,
                              String website) {
    }

    public record Mention(String id,
                          String username,
                          String url,
                          String acct) {
    }

    public record Tag(String name,
                      String url) {
    }
}


