package com.switchfully.digibooky.lending.api;

import com.switchfully.digibooky.author.service.dto.CreateAuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.dto.CreateBookDto;
import com.switchfully.digibooky.lending.service.dto.CreateLendingDto;
import com.switchfully.digibooky.lending.service.dto.LendingDto;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LendingControllerTest {
    private static final Address ADDRESS = new Address("streetname", "streetnumber", "zipcode", "city");
    private static final CreateMemberDto CREATE_MEMBER1_DTO = new CreateMemberDto("email1@email.com", "lastname1", "firstname1", "password1", ADDRESS, "inss1");
    private static final CreateAuthorDto CREATE_AUTHOR1_DTO = new CreateAuthorDto("first1name1", "lastname1");
    private static final CreateAuthorDto CREATE_AUTHOR2_DTO = new CreateAuthorDto("firstname2", "lastname2");
    private static final CreateAuthorDto CREATE_AUTHOR3_DTO = new CreateAuthorDto("firstname3", "3lastname3");
    private static final CreateBookDto CREATE_BOOK1_DTO = new CreateBookDto("is1bn1", "title1", "summary", true, false, CREATE_AUTHOR1_DTO);
    private static final CreateBookDto CREATE_BOOK2_DTO = new CreateBookDto("isbn2", "title2", "summary", true, false, CREATE_AUTHOR2_DTO);
    private static final CreateBookDto CREATE_BOOK3_DTO = new CreateBookDto("isbn3", "3title", "summary", true, false, CREATE_AUTHOR3_DTO);
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    void givenCreateLendingDto_whenCreateLending_thenReturnLendingDto() {
        BookDto bookDto = createABook(CREATE_BOOK1_DTO);
        MemberDto memberDTO = createAMember(CREATE_MEMBER1_DTO);
        CreateLendingDto createLendingDto = new CreateLendingDto(CREATE_BOOK1_DTO.getIsbn(), memberDTO.getId(), null);

        LendingDto lendingDto = RestAssured
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

        Assertions.assertThat(lendingDto.getId()).isNotNull();
        Assertions.assertThat(lendingDto.getBook()).isEqualTo(bookDto);
        Assertions.assertThat(lendingDto.getMember()).isEqualTo(memberDTO);
        Assertions.assertThat(lendingDto.getLendingDate()).isNotNull();
        Assertions.assertThat(lendingDto.getReturningDate()).isNotNull();
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