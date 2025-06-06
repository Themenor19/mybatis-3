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
package org.apache.ibatis.submitted.record_type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordTypeTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create a SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/record_type/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }
    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
        "org/apache/ibatis/submitted/record_type/CreateDB.sql");
  }

  @Test
  void selectRecord() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      RecordTypeMapper mapper = sqlSession.getMapper(RecordTypeMapper.class);
      Property prop = mapper.selectProperty(1);
      assertEquals("Val1!", prop.value());
      assertEquals("https://www.google.com", prop.URL());
    }
  }

  @Test
  void selectRecordAutomapping() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      RecordTypeMapper mapper = sqlSession.getMapper(RecordTypeMapper.class);
      Property prop = mapper.selectPropertyAutomapping(1);
      assertEquals("Val1!", prop.value());
      assertEquals("https://www.google.com", prop.URL());
    }
  }

  @Test
  void insertRecord() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      RecordTypeMapper mapper = sqlSession.getMapper(RecordTypeMapper.class);
      assertEquals(1, mapper.insertProperty(new Property(2, "Val2", "https://mybatis.org")));
      sqlSession.commit();
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      RecordTypeMapper mapper = sqlSession.getMapper(RecordTypeMapper.class);
      Property prop = mapper.selectProperty(2);
      assertEquals("Val2!!", prop.value());
      assertEquals("https://mybatis.org", prop.URL());
    }
  }

  @Test
  void selectNestedRecord() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      RecordTypeMapper mapper = sqlSession.getMapper(RecordTypeMapper.class);
      Item item = mapper.selectItem(100);
      assertEquals(Integer.valueOf(100), item.id());
      assertEquals(new Property(1, "Val1", "https://www.google.com"), item.property());
    }
  }
}
