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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.ibatis.binding.BindingException;
import org.junit.jupiter.api.Test;

class ProjectFifthTests {

  @Test
  void executeUpdate_ReturnsRowCount_WhenRowCountIsNonNegative() {
    // Arrange
    int rowCount = 3;

    // Act
    int result = handleExecuteUpdateResult(rowCount);

    // Assert
    assertEquals(3, result);
  }

  @Test
  void executeUpdate_ThrowsBindingException_WhenRowCountIsNegative() {
    // Arrange
    int rowCount = -1;

    // Act & Assert
    assertThrows(BindingException.class, () -> handleExecuteUpdateResult(rowCount));
  }

  // Simulated private method for handling update results
  private int handleExecuteUpdateResult(int rowCount) {
    if (rowCount < 0) {
      throw new BindingException("Invalid update count: " + rowCount);
    }
    return rowCount;
  }
}
