package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.book.service.dto.BookDto;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {
    private final static Address ADDRESS = new Address("street", "number", "0000", "Bxl");
    private final static CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto("a@a.com", "lastname", "firstname", "pswd", ADDRESS, "A33");
    private final static CreateMemberDto CREATE_MEMBER_DTO_INCORRECT_MAIL = new CreateMemberDto("a", "lastname", "firstname", "pswd", ADDRESS, "A33");

    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    void createMember_whenGivenCorrect_thenMemberCreated() {
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

        //WHEN
        //THEN
        Assertions.assertThat(actualMemberDto.getEmail()).isEqualTo(CREATE_MEMBER_DTO.getEmail());
    }
    @Test
    void createMember_whenGivenIncorrectMail_thenMemberCreated() {
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

}