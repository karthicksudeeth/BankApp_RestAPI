package com.practice.simpleRest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.simpleRest.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.web.bind.annotation.PathVariable

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest{

    @Autowired
    lateinit var mockMvc:MockMvc

    @Autowired
    lateinit var objectMapper:ObjectMapper

    @Nested
    @DisplayName("getbanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all the banks`(){

            mockMvc.get("/api/banks")
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].accountNumber"){value(23456)}
                    }
        }

    }

    @Nested
    @DisplayName("getbank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank{

        @Test()
        fun `should return the bank`(){

            val accountNumber=12345

            mockMvc.get("/api/banks/$accountNumber")
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.trust"){value(0.0)}
                        jsonPath("$.transactionFee"){value(2)}
                    }

        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`(){

            val accountNumber=12354325
            mockMvc.get("/api/banks/$accountNumber")
                    .andDo { print() }
                    .andExpect { status { isNotFound() } }
        }

    }

    @Nested
    @DisplayName("POST /api/bank/")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank{

        @Test
        fun `Should add new bank`(){
            val newBank=Bank("aslfja",34.2,26)

            val performPost=mockMvc.post("/api/banks"){
                contentType= MediaType.APPLICATION_JSON
                content=objectMapper.writeValueAsString(newBank)
            }
                   performPost
                           .andDo { print() }
                           .andExpect {
                               status { isCreated() }
                               content { contentType(MediaType.APPLICATION_JSON) }
                               jsonPath("$.accountNumber"){value("aslfja")}
                               jsonPath("$.trust"){value(34.2)}
                               jsonPath("$.transactionFee"){value(26)}
                           }
        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`(){

            val invalidBank=Bank("12345",1.33,2)
            val performPost=mockMvc.post("/api/banks"){
                contentType= MediaType.APPLICATION_JSON
                content=objectMapper.writeValueAsString(invalidBank)
            }
            performPost
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }

        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchBank{

        @Test
        fun ` update the existing bank fields with the PATCH request`(){
            val updateBank=Bank("12345",80.98,56)
            val performPatch=mockMvc.patch("/api/banks"){
                contentType=MediaType.APPLICATION_JSON
                content=objectMapper.writeValueAsString(updateBank)
            }
            performPatch
                    .andDo { print() }
                    .andExpect {
                        status { isOk()}
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            json(objectMapper.writeValueAsString(updateBank))
                        }
                    }

            mockMvc.get("/api/banks/${updateBank.accountNumber}").andExpect { content { json(objectMapper.writeValueAsString(updateBank)) } }
        }

        @Test
        fun `should return BAD REQUEST if no such account number is present`(){
            val invalidAccount=Bank("asfsa",89.34,8)
            val performPatch=mockMvc.patch("/api/banks"){
                contentType=MediaType.APPLICATION_JSON
                content=objectMapper.writeValueAsString(invalidAccount)
            }

            performPatch
                    .andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                    }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank{

        @Test
        fun `should DELETE the bank with the given account number`(){
            val deleteAccount=12345

            val delete=mockMvc.delete("/api/banks/$deleteAccount")

            delete.andDo { print() }
                    .andExpect {
                        status { isNoContent() }
                    }

            mockMvc.get("/api/banks/$deleteAccount")
                    .andExpect { status { isNotFound() }}
        }

        @Test
        fun `should return BAD REQUEST if the account number does not exist`(){
            val deleteAccout=214214

            mockMvc.delete("/api/banks/$deleteAccout")
                    .andDo { print() }
                    .andExpect { status { isNotFound() } }


        }
    }



}