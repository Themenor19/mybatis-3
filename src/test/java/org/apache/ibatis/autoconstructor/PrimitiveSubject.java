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
package org.apache.ibatis.autoconstructor;

import java.util.Date;

public class PrimitiveSubject {
  private final int id;
  private final String name;
  private final int age;
  private final int height;
  private final int weight;
  private final boolean active;
  private final Date dt;

  public PrimitiveSubject(final int id, final String name, final int age, final int height, final int weight,
      final boolean active, final Date dt) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.active = active;
    this.dt = dt;
  }
}
