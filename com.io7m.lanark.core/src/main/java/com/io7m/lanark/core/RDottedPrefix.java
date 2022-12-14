/*
 * Copyright © 2022 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.lanark.core;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A standard restricted dotted name prefix.
 *
 * @param value The name
 */

public record RDottedPrefix(String value)
  implements Comparable<RDottedPrefix>
{
  /**
   * A standard restricted dotted name prefix.
   *
   * @param value The name
   */

  public RDottedPrefix
  {
    Objects.requireNonNull(value, "value");

    final var pattern = RDottedNamePatterns.dottedPrefix();
    if (!pattern.matcher(value).matches()) {
      throw new IllegalArgumentException(
        "Name '%s' must match %s".formatted(value, pattern)
      );
    }
  }

  @Override
  public String toString()
  {
    return this.value;
  }

  /**
   * @return The prefix as a list of segments
   */

  public List<String> segments()
  {
    return List.of(this.value.split("\\."));
  }

  /**
   * Construct a prefix from a list of segments.
   *
   * @param segments The segments
   *
   * @return A name
   */

  public static RDottedPrefix ofSegments(
    final List<String> segments)
  {
    return new RDottedPrefix(String.join(".", segments) + ".");
  }

  @Override
  public int compareTo(
    final RDottedPrefix o)
  {
    return Comparator.comparing(RDottedPrefix::value).compare(this, o);
  }
}
