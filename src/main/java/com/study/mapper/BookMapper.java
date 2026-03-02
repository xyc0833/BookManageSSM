package com.study.mapper;

import com.study.entity.Book;
import com.study.entity.Borrow;
import com.study.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {
    //查询借阅信息 需要把名字啥的 都补充上去
    //需要关联查询
    @Results({
            //column对应数据库 property对应类
            @Result(column = "id",property = "id"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "bid",property = "bid"),
            @Result(column = "time",property = "time"),
            @Result(column = "name",property = "studentName"),
            @Result(column = "title",property = "bookName")
    })
    @Select("""
            select * from borrow left join student on borrow.sid = student.id
                left join book on borrow.bid = book.id
            """)
    List<Borrow> getBorrowList();

    //借阅信息 写一个 插入和 删除
    @Insert("insert into borrow(sid,bid,time) values (#{sid},#{bid},NOW())")
    void addBorrow(@Param("sid")int sid,@Param("bid") int bid);

    @Delete("delete from borrow where id=#{id}")
    void deleteBorrow(String id);

    @Select("select * from book")
    List<Book> getBookList();

    @Delete("delete from book where id = #{id}")
    void deleteBook(int id);

    @Insert("insert into book(title,info,price) values (#{title},#{info},#{price})")
    void addBook(@Param("title")String title,@Param("info") String info,@Param("price")double price);
}
