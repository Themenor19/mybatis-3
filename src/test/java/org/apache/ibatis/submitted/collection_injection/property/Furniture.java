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
package org.apache.ibatis.submitted.collection_injection.property;

import java.util.List;

public class Furniture {
  private int id;
  private String description;
  private List<Defect> defects;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Defect> getDefects() {
    return defects;
  }

  public void setDefects(List<Defect> defects) {
    this.defects = defects;
  }

  @Override
  public String toString() {
    return "Furniture{" + "id=" + id + ", description='" + description + '\'' + ", defects='" + defects + '\'' + '}';
  }
}
