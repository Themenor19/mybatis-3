/*
 *    Copyright 2009-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;

public class ProjectFourthTests {

  // Boundary values for the offset
  private static final int MIN_OFFSET = 0;
  private static final int MAX_OFFSET = Integer.MAX_VALUE;

  // Boundary values for the limit
  private static final int MIN_LIMIT = 0;
  private static final int MAX_LIMIT = Integer.MAX_VALUE;

  // Test case 1: Test with the minimum offset and minimum limit
  @Test
  public void testRowBoundsWithMinOffsetAndMinLimit() {
    RowBounds rowBounds = new RowBounds(MIN_OFFSET, MIN_LIMIT);
    assertEquals(MIN_OFFSET, rowBounds.getOffset());
    assertEquals(MIN_LIMIT, rowBounds.getLimit());
  }

  // Test case 2: Test with the maximum offset and minimum limit
  @Test
  public void testRowBoundsWithMaxOffsetAndMinLimit() {
    RowBounds rowBounds = new RowBounds(MAX_OFFSET, MIN_LIMIT);
    assertEquals(MAX_OFFSET, rowBounds.getOffset());
    assertEquals(MIN_LIMIT, rowBounds.getLimit());
  }

  // Test case 3: Test with the minimum offset and maximum limit
  @Test
  public void testRowBoundsWithMinOffsetAndMaxLimit() {
    RowBounds rowBounds = new RowBounds(MIN_OFFSET, MAX_LIMIT);
    assertEquals(MIN_OFFSET, rowBounds.getOffset());
    assertEquals(MAX_LIMIT, rowBounds.getLimit());
  }

  // Test case 4: Test with the maximum offset and maximum limit
  @Test
  public void testRowBoundsWithMaxOffsetAndMaxLimit() {
    RowBounds rowBounds = new RowBounds(MAX_OFFSET, MAX_LIMIT);
    assertEquals(MAX_OFFSET, rowBounds.getOffset());
    assertEquals(MAX_LIMIT, rowBounds.getLimit());
  }

  // Test case 5: Test with default RowBounds (offset = 0, limit = Integer.MAX_VALUE)
  @Test
  public void testRowBoundsWithDefault() {
    RowBounds rowBounds = RowBounds.DEFAULT;
    assertEquals(RowBounds.NO_ROW_OFFSET, rowBounds.getOffset());
    assertEquals(RowBounds.NO_ROW_LIMIT, rowBounds.getLimit());
  }
}
