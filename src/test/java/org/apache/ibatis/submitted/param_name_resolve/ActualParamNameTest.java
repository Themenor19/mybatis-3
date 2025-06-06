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
package org.apache.ibatis.submitted.param_name_resolve;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ActualParamNameTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources
        .getResourceAsReader("org/apache/ibatis/submitted/param_name_resolve/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      sqlSessionFactory.getConfiguration().addMapper(Mapper.class);
    }

    // populate in-memory database
    try (Connection conn = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection();
        Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/param_name_resolve/CreateDB.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
    }
  }

  @Test
  void singleListParameterWhenUseActualParamNameIsTrue() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      // use actual name
      {
        long count = mapper.getUserCountUsingList(Arrays.asList(1, 2));
        assertEquals(2, count);
      }
      // use 'collection' as alias
      {
        long count = mapper.getUserCountUsingListWithAliasIsCollection(Arrays.asList(1, 2));
        assertEquals(2, count);
      }
      // use 'list' as alias
      {
        long count = mapper.getUserCountUsingListWithAliasIsList(Arrays.asList(1, 2));
        assertEquals(2, count);
      }
      // use actual name #2693
      {
        long count = mapper.getUserCountUsingList_RowBounds(new RowBounds(0, 5), Arrays.asList(1, 2));
        assertEquals(2, count);
      }
    }
  }

  @Test
  void singleArrayParameterWhenUseActualParamNameIsTrue() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      // use actual name
      {
        long count = mapper.getUserCountUsingArray(1, 2);
        assertEquals(2, count);
      }
      // use 'array' as alias
      {
        long count = mapper.getUserCountUsingArrayWithAliasArray(1, 2);
        assertEquals(2, count);
      }
    }
  }

  interface Mapper {
    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingList(List<Integer> ids);

    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='collection' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingListWithAliasIsCollection(List<Integer> ids);

    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingListWithAliasIsList(List<Integer> ids);

    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingArray(Integer... ids);

    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='array' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingArrayWithAliasArray(Integer... ids);

    // @formatter:off
    @Select({
        "<script>",
        "  select count(*) from users u where u.id in",
        "  <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>",
        "    #{item}",
        "  </foreach>",
        "</script>"
      })
    // @formatter:on
    Long getUserCountUsingList_RowBounds(RowBounds rowBounds, List<Integer> ids);
  }

}
