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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;

//third set of tests
public class ProjectThirdTests {

  @Test
  public void testGetUrlAsStream_ValidUrl() throws IOException {
    InputStream stream = Resources.getUrlAsStream("https://www.example.com/");
    assertNotNull(stream);
  }

  @Test
  public void testGetUrlAsStream_InvalidUrl() {
    assertThrows(IOException.class, () -> Resources.getUrlAsStream("http://invalid.example.com/nope"));
  }
}
