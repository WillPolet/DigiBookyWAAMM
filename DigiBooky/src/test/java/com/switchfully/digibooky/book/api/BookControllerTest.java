package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookControllerTest {

    private static final CreateAuthorDto CREATE_AUTHOR1_DTO = new CreateAuthorDto("first1name1", "lastname1");
    private static final CreateAuthorDto CREATE_AUTHOR2_DTO = new CreateAuthorDto("firstname2", "lastname2");
    private static final CreateAuthorDto CREATE_AUTHOR3_DTO = new CreateAuthorDto("firstname3", "3lastname3");
    private static final CreateBookDto CREATE_BOOK1_DTO = new CreateBookDto("is1bn1", "title1", "summary", CREATE_AUTHOR1_DTO);
    private static final CreateBookDto CREATE_BOOK2_DTO = new CreateBookDto("isbn2", "title2", "summary", CREATE_AUTHOR2_DTO);
    private static final CreateBookDto CREATE_BOOK3_DTO = new CreateBookDto("isbn3", "3title", "summary", CREATE_AUTHOR3_DTO);
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    void givenCreateBookDto_whenCreateBook_thenReturnBookDto() {
        BookDto bookDto = createABook(CREATE_BOOK1_DTO);
        Assertions.assertThat(bookDto.getId()).isNotNull();
        Assertions.assertThat(bookDto.getIsbn()).isEqualTo(CREATE_BOOK1_DTO.getIsbn());
    }

    @Test
    void givenExistingBooks_whenGetBooks_thenReturnAllBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book1Dto, book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByTitle_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?title=title");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book1Dto, book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByIsbn_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?isbn=isbn");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByTitleAndIsbn_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?title=title&isbn=isbn");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByFirstname_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?authorFirstname=firstname");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByLastname_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?authorLastname=lastname");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book1Dto, book2Dto, book3Dto);
    }

    @Test
    void givenExistingBooks_whenSearchBooksByFirstnameAndLastname_thenReturnMatchedBooksDto() {
        BookDto book1Dto = createABook(CREATE_BOOK1_DTO);
        BookDto book2Dto = createABook(CREATE_BOOK2_DTO);
        BookDto book3Dto = createABook(CREATE_BOOK3_DTO);

        List<BookDto> booksDto = searchBooks("?title=title&isbn=isbn&authorFirstname=firstname&authorLastname=3lastname3");

        Assertions.assertThat(booksDto).containsExactlyInAnyOrder(book3Dto);
    }

//    @Test
//    void givenExistingBooks_whenSearchById_thenReturnMatchedBookDto(){
//        BookDto book1Dto= createABook(CREATE_BOOK1_DTO);
//        String id = book1Dto.getId();
//
//    }

    private BookDto createABook(CreateBookDto createBookDto) {
        return RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .when()
                .post("/books")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value()).extract().as(BookDto.class);
    }

    private List<BookDto> searchBooks(String searchString) {
        return RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .when()
                .get("/books" + searchString)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", BookDto.class);
    }
}