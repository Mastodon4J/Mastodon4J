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
package org.mastodon4j.core.api.entities;

import java.util.List;
import java.util.Map;

public record Instance(String domain,
                       String title,
                       String version,
                       String source_url,
                       String description,
                       Usage usage,
                       Thumbnail thumbnail,
                       List<String> languages,
                       Configuration configuration,
                       Registrations registrations,
                       Contact contact,
                       List<Rule> rules) {

    public record Usage(Users users) {
        public record Users(Integer active_month) {
        }
    }

    public record Thumbnail(String url, @Optional String blurhash, @Optional Map<String, String> versions) {
    }

    public record Configuration(Urls urls, Accounts accounts, Statuses statuses,
                                MediaAttachments media_attachments, Polls polls, Translation translation) {
        public record Urls(String status, String streaming) {
        }

        public record Accounts(Integer max_featured_tags) {
        }

        public record Statuses(Integer max_characters, Integer max_media_attachments,
                               Integer characters_reserved_per_url) {
        }

        public record MediaAttachments(List<String> supported_mime_types, Integer image_size_limit,
                                       Integer image_matrix_limit, Integer video_size_limit,
                                       Integer video_frame_rate_limit, Integer video_matrix_limit) {
        }

        public record Polls(Integer max_options, Integer max_characters_per_option, Integer min_expiration,
                            Integer max_expiration) {
        }

        public record Translation(Boolean enabled) {
        }
    }

    public record Registrations(Boolean enabled, Boolean approval_required, @Optional String message) {
    }

    public record Contact(String email, Account account) {
    }
}
