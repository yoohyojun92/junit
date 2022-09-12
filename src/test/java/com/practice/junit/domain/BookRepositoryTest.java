package com.practice.junit.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 할당
public class BookRepositoryTest {

    @Autowired //DI
    private BookRepository bookRepository;

    //@BeforeAll // 테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    } // 트랜잭션 종료됐다면 말이 안됨
    //가정 1 : [ 데이터준비() + 1 책등록 ] (T), [ 데이터준비() + 2 책목록보기 ] (T)
    //가정 2 : [ 데이터준비() + 2 책등록 + 데이터준비() + 2 책목록보기 ] (T)
    //가정을 확인해보기 위해서 책 목록보기 시 사이즈를 보면 됨
    //가정 1이라면 사이즈는 1 가정 2라면 사이즈는 2
    
    // 1. 책 등록
    @Test
    public void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    } // 트랜잭션 종료, 데이터 초기화

    // 2. 책 목록 보기
    @Test
    public void 책목록보기_test() {
        //given
        String title = "junit";
        String author = "겟인데어";

        //when
        List<Book> booksPS = bookRepository.findAll();

        System.out.println("사이즈 : ================================= : "+booksPS.size());

        //then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());

    } // 트랜잭션 종료, 데이터 초기화

    // 3. 책 한 건보기
    @Test
    public void 책한건보기_test() {
        //given
        String title = "junit";
        String author = "겟인데어";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    } // 트랜잭션 종료, 데이터 초기화

    // 4. 책 수정

    // 5. 책 삭제

}
