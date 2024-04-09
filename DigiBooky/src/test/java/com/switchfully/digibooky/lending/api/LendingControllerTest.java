package com.switchfully.digibooky.lending.api;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.lending.domain.Lending;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.Librarian;
import com.switchfully.digibooky.user.domain.User;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
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
class LendingControllerTest {
    private final static CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto("a@a.com",
            "lastname",
            "firstname",
            "pswd");
    private static final User USER_LIBRARIAN = new Librarian("email", "lastname", "firstname", "password");
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final CreateMemberDto CREATE_MEMBER1_DTO = new CreateMemberDto("email1@email.com", "lastname1", "firstname1", "password1", ADDRESS, "inss1");
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
    void givenCreateLendingDto_whenCreateLending_thenReturnLendingDto() {
        createABook(CREATE_BOOK1_DTO);
        MemberDto memberDTO = createAMember(CREATE_MEMBER1_DTO);
        CreateLendingDto createLendingDto = new CreateLendingDto(CREATE_BOOK1_DTO.getIsbn(), memberDTO.getId(), null);
        LendingDto lendingDto = createLending(createLendingDto);

        Assertions.assertThat(lendingDto.getId()).isNotNull();
        Assertions.assertThat(lendingDto.getBook().isLent()).isTrue();
        Assertions.assertThat(lendingDto.getMember()).isEqualTo(memberDTO);
        Assertions.assertThat(lendingDto.getLendingDate()).isNotNull();
        Assertions.assertThat(lendingDto.getReturningDate()).isNotNull();
    }

    @Test
    void givenLendingId_whenReturnBook_thenReceiveGoodMessage() {
        createABook(CREATE_BOOK1_DTO);
        MemberDto memberDTO = createAMember(CREATE_MEMBER1_DTO);
        CreateLendingDto createLendingDto = new CreateLendingDto(CREATE_BOOK1_DTO.getIsbn(), memberDTO.getId(), null);
        LendingDto lendingDto = createLending(createLendingDto);

        String responseMessage = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_MEMBER1_DTO.getEmail(),CREATE_MEMBER1_DTO.getPassword())
                .contentType(ContentType.JSON)
                .when()
                .put("/lendings/return/" + lendingDto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value()).extract().asString();

        Assertions.assertThat(responseMessage).isEqualTo("You returned the book with isbn " + lendingDto.getBook().getIsbn());
    }

    @Test
    void givenLendings_whenGetOverdue_thenReturnListOfOverdue() {
        createLibrarian();
        createABook(CREATE_BOOK1_DTO);
        createABook(CREATE_BOOK2_DTO);
        MemberDto memberDTO = createAMember(CREATE_MEMBER1_DTO);
        CreateLendingDto createLendingDto1 = new CreateLendingDto(CREATE_BOOK1_DTO.getIsbn(), memberDTO.getId(), null);
        LendingDto lendingDto1 = createLending(createLendingDto1);
        CreateLendingDto createLendingDto2 = new CreateLendingDto(CREATE_BOOK2_DTO.getIsbn(), memberDTO.getId(), "01/01/2023");
        LendingDto lendingDto2 = createLending(createLendingDto2);

        List<LendingDto> overdueLendings = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_LIBRARIAN_DTO.getEmail(),CREATE_LIBRARIAN_DTO.getPassword())
                .contentType(ContentType.JSON)
                .when()
                .get("/lendings/overdue")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", LendingDto.class);

        Assertions.assertThat(overdueLendings).containsExactlyInAnyOrder(lendingDto2);
    }

    private void createLibrarian() {
        LibrarianDto actualLibrarianDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic("root@root.com", "rootPswd")
                .contentType(ContentType.JSON)
                .body(CREATE_LIBRARIAN_DTO)
                .when()
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LibrarianDto.class);
    }

    private LendingDto createLending(CreateLendingDto createLendingDto) {
        return RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_MEMBER1_DTO.getEmail(),CREATE_MEMBER1_DTO.getPassword())
                .contentType(ContentType.JSON)
                .body(createLendingDto)
                .when()
                .post("/lendings")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value()).extract().as(LendingDto.class);
    }


    private MemberDto createAMember(CreateMemberDto createMemberDTO) {
        return RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(createMemberDTO)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value()).extract().as(MemberDto.class);
    }

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
}