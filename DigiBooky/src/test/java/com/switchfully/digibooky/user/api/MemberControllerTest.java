package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
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
class MemberControllerTest {
    private final static CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto("a@a.com",
            "lastname",
            "firstname",
            "pswd");
    private static final User USER_LIBRARIAN = new Librarian("email", "lastname", "firstname", "password");
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final CreateMemberDto CREATE_MEMBER1_DTO = new CreateMemberDto("email1@email.com", "lastname1", "firstname1", "password1", ADDRESS, "inss1");
    private static final CreateMemberDto CREATE_MEMBER2_DTO = new CreateMemberDto("email2@email.com", "lastname2", "firstname2", "password2", ADDRESS, "inss2");
    private static final CreateAuthorDto CREATE_AUTHOR1_DTO = new CreateAuthorDto("first1name1", "lastname1");
    private static final CreateAuthorDto CREATE_AUTHOR2_DTO = new CreateAuthorDto("firstname2", "lastname2");
    private static final CreateAuthorDto CREATE_AUTHOR3_DTO = new CreateAuthorDto("firstname3", "3lastname3");
    private static final CreateBookDto CREATE_BOOK1_DTO = new CreateBookDto("is1bn1", "title1", "summary", CREATE_AUTHOR1_DTO);
    private static final CreateBookDto CREATE_BOOK2_DTO = new CreateBookDto("isbn2", "title2", "summary", CREATE_AUTHOR2_DTO);
    private static final CreateBookDto CREATE_BOOK3_DTO = new CreateBookDto("isbn3", "3title", "summary", CREATE_AUTHOR3_DTO);
    private final static CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto("a@a.com", "lastname", "firstname", "pswd", ADDRESS, "A33");
    private final static CreateMemberDto CREATE_MEMBER_DTO_INCORRECT_MAIL = new CreateMemberDto("a", "lastname", "firstname", "pswd", ADDRESS, "A33");
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    void createMember_whenGivenCorrectMember_thenMemberCreated() {
        //GIVEN
        MemberDto actualMemberDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(CREATE_MEMBER_DTO)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);

        //THEN
        Assertions.assertThat(actualMemberDto.getEmail()).isEqualTo(CREATE_MEMBER_DTO.getEmail());
        Assertions.assertThat(actualMemberDto.getFirstName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getLastName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getId()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getStreetName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getStreetNumber()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getCity()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getZipCode()).isNotNull();
    }
    @Test
    void createMember_whenGivenIncorrectMail_thenMemberNotCreated() {
        //GIVEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(CREATE_MEMBER_DTO_INCORRECT_MAIL)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    void givenMember_whenGetLentBooksByMember_thenReturnLendingsOfMember() {
        createLibrarian();
        createABook(CREATE_BOOK1_DTO);
        createABook(CREATE_BOOK2_DTO);
        createABook(CREATE_BOOK3_DTO);
        MemberDto memberDTO1 = createAMember(CREATE_MEMBER1_DTO);
        MemberDto memberDTO2 = createAMember(CREATE_MEMBER2_DTO);
        CreateLendingDto createLendingDto1 = new CreateLendingDto(CREATE_BOOK1_DTO.getIsbn(), memberDTO1.getId(), null);
        LendingDto lendingDto1 = createLending(createLendingDto1, CREATE_MEMBER1_DTO);
        CreateLendingDto createLendingDto2 = new CreateLendingDto(CREATE_BOOK2_DTO.getIsbn(), memberDTO2.getId(), null);
        LendingDto lendingDto2 = createLending(createLendingDto2, CREATE_MEMBER2_DTO);
        CreateLendingDto createLendingDto3 = new CreateLendingDto(CREATE_BOOK3_DTO.getIsbn(), memberDTO1.getId(), null);
        LendingDto lendingDto3 = createLending(createLendingDto3,CREATE_MEMBER1_DTO);

        List<LendingDto> lendingsByMember = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_LIBRARIAN_DTO.getEmail(),CREATE_LIBRARIAN_DTO.getPassword())
                .contentType(ContentType.JSON)
                .when()
                .get("/members/" + memberDTO1.getId() + "/lentbooks")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", LendingDto.class);

        Assertions.assertThat(lendingsByMember).containsExactlyInAnyOrder(lendingDto1, lendingDto3);

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

    private LendingDto createLending(CreateLendingDto createLendingDto, CreateMemberDto lendingMember) {
        return RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(lendingMember.getEmail(),lendingMember.getPassword())
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