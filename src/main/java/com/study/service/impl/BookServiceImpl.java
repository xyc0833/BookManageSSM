package com.study.service.impl;

import com.study.entity.Book;
import com.study.entity.Borrow;
import com.study.mapper.BookMapper;
import com.study.service.BookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper mapper;

    @Override
    public List<Borrow> getBorrowList() {
        return mapper.getBorrowList();
    }

    @Override
    //这里需要返回 书籍的状态
    public Map<Book,Boolean> getBookList() {
        Set<Integer> set = new HashSet<>();//先看一下哪些书是被借阅的 然后存到set里面
        this.getBorrowList().forEach(borrow -> {
            set.add(borrow.getBid());
        });
        Map<Book,Boolean> map = new LinkedHashMap<>();
        //如果书的id在上面的 set集合里面 说明这本书被借了 返回1？也就是返回true
        mapper.getBookList().forEach(book -> map.put(book,set.contains(book.getId())));
        return map;
    }

    //一次性获取当前可以被借的书
    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();
        this.getBorrowList().forEach(borrow -> set.add(borrow.getId()));
        return mapper.getBookList()
                .stream()
                //把已经被借走的书 过滤掉
                //把 不被包含的书 通过我们的过滤
                .filter(book -> !set.contains(book.getId()))
                .toList();
    }

    @Override
    public void addBorrow(int sid, int bid) {
        mapper.addBorrow(sid,bid);
    }

    @Override
    public void returnBook(String id) {
        mapper.deleteBorrow(id);
    }

    @Override
    public void addBook(String title, String info, double price) {
        mapper.addBook(title, info, price);
    }

    @Override
    public void deleteBook(int bid) {
        mapper.deleteBook(bid);
    }

}
