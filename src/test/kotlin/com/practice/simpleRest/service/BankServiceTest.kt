package com.practice.simpleRest.service

import com.practice.simpleRest.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyAll
import jakarta.activation.DataSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BankServiceTest{
    val dataSource: BankDataSource = mockk(relaxed = true)
    val bankService:BankService= BankService(dataSource)


    @Test
    fun `should call its data source to retrieve banks`(){
//        every { dataSource.getBanks() } returns emptyList()
        bankService.getBanks()
        verify(exactly = 1) {dataSource.getBanks()  }
    }
}