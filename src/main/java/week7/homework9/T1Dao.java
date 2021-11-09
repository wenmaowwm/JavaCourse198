package week7.homework9;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface T1Dao {
    @Select("select id from t1")
    List<Integer> getAll();

    @Insert("insert into t1 (id) values (#{id})")
    void insert(@Param("id") Integer id);

    @Delete("delete from t1 where id=#{id}")
    void deleteById(@Param("id") Integer id);
}
