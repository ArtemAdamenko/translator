package com.cds.mapper;

import com.cds.translator.TestInsertDB;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

/**
 *
 * @author Artem Adamenko <adamenko.artem@gmail.com>
 * Интерфейс запросов к базе Data
 */
public interface DataMapper {
    @Select("SELECT NAME_ FROM USERS WHERE ID_ = #{id}")
    public String test(int id);
    
    @Insert("INSERT INTO test(id) VALUES(#{id})")
    public void insert(int id);
    
    @SelectProvider(type = TestInsertDB.class, method = "insert")
    public void insertProvider(int id);
}
