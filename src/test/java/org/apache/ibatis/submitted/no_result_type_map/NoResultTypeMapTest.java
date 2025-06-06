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
package org.apache.ibatis.submitted.no_result_type_map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NoResultTypeMapTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create a SqlSessionFactory
    try (Reader reader = Resources
        .getResourceAsReader("org/apache/ibatis/submitted/no_result_type_map/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      sqlSessionFactory.getConfiguration().addMapper(Mapper.class);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
        "org/apache/ibatis/submitted/no_result_type_map/CreateDB.sql");
  }

  @Test
  void shouldGetAUser() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.getUser(1);
      Assertions.assertEquals("User1", user.getName());
    }
  }

  @Test
  void shouldGetAllUsers() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<User> users = mapper.getAllUsers();
      Assertions.assertEquals(3, users.size());
    }
  }

  @Test
  void shouldResolveInheritedReturnType() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<User> users = mapper.getAllUsersInParent();
      Assertions.assertEquals(3, users.size());
    }
  }

  @Test
  void shouldFailIfNoMatchingMethod() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      PersistenceException ex = assertThrows(PersistenceException.class,
          () -> sqlSession.selectList("org.apache.ibatis.submitted.no_result_type_map.Mapper.noMatchingMethod"));
      ExecutorException cause = (ExecutorException) ex.getCause();
      assertEquals("""
          A query was run and no Result Maps were found for the Mapped Statement \
          'org.apache.ibatis.submitted.no_result_type_map.Mapper.noMatchingMethod'. \
          'resultType' or 'resultMap' must be specified when there is no corresponding method.""", cause.getMessage());
    }
  }

}
