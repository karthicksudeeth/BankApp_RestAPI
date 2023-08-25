package com.practice.simpleRest.datasource.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
class MockBankDataSourceTest{
    private val mockDataSource=MockBankDataSource()
    @Test
    fun `should provide a collection of banks`(){
        val banks=mockDataSource.getBanks()
        assertThat(banks).isNotEmpty()
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }
    @Test
    fun `should provide some mock data`(){
        val banks=mockDataSource.getBanks()
        assertThat(banks).allMatch{ it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch{ it.trust != 0.0}
        assertThat(banks).allMatch{it.transactionFee != 0}
    }
}
