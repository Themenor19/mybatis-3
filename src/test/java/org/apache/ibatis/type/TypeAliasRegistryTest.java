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
package org.apache.ibatis.type;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class TypeAliasRegistryTest {

  @Test
  void shouldRegisterAndResolveTypeAlias() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    typeAliasRegistry.registerAlias("rich", "org.apache.ibatis.domain.misc.RichType");

    assertEquals("org.apache.ibatis.domain.misc.RichType", typeAliasRegistry.resolveAlias("rich").getName());
  }

  @Test
  void shouldFetchArrayType() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    assertEquals(Byte[].class, typeAliasRegistry.resolveAlias("byte[]"));
  }

  @Test
  void shouldBeAbleToRegisterSameAliasWithSameTypeAgain() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    typeAliasRegistry.registerAlias("String", String.class);
    typeAliasRegistry.registerAlias("string", String.class);
  }

  @Test
  void shouldNotBeAbleToRegisterSameAliasWithDifferentType() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    assertThrows(TypeException.class, () -> typeAliasRegistry.registerAlias("string", BigDecimal.class));
  }

  @Test
  void shouldBeAbleToRegisterAliasWithNullType() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    typeAliasRegistry.registerAlias("foo", (Class<?>) null);
    assertNull(typeAliasRegistry.resolveAlias("foo"));
  }

  @Test
  void shouldBeAbleToRegisterNewTypeIfRegisteredTypeIsNull() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    typeAliasRegistry.registerAlias("foo", (Class<?>) null);
    typeAliasRegistry.registerAlias("foo", String.class);
  }

  @Test
  void shouldFetchCharType() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    assertEquals(Character.class, typeAliasRegistry.resolveAlias("char"));
    assertEquals(Character[].class, typeAliasRegistry.resolveAlias("char[]"));
    assertEquals(char[].class, typeAliasRegistry.resolveAlias("_char[]"));
  }

  @Test
  void shouldNotBeAbleToRegisterAliasWithEmptyString() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    assertThatThrownBy(() -> typeAliasRegistry.registerAlias("foo", "")).isInstanceOf(TypeException.class)
        .hasMessageContaining("Error registering type alias foo for");
  }

  @Test
  void shouldNotBeAbleToResolveNotExistsAlias() {
    TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    assertThatThrownBy(() -> typeAliasRegistry.resolveAlias("abc")).isInstanceOf(TypeException.class)
        .hasMessageContaining(
            "Could not resolve type alias 'abc'.  Cause: java.lang.ClassNotFoundException: Cannot find class: abc");
  }
}
