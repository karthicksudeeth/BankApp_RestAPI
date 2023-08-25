package com.practice.simpleRest.service
import com.practice.simpleRest.datasource.BankDataSource
import com.practice.simpleRest.model.Bank
import org.springframework.stereotype.Service
@Service
class BankService(private val dataSource:BankDataSource) {
    fun getBanks():Collection<Bank> = dataSource.getBanks()
    fun getBank(accountNumber: String): Bank = dataSource.getBank(accountNumber)
    fun addbank(bank: Bank): Bank {
      return  dataSource.addBank(bank)
    }
    fun updateBank(bank: Bank): Bank {
        return dataSource.updateBank(bank)
    }
    fun deleteAccount(accountNumber: String): Any {
        return dataSource.deleteAccount(accountNumber)
    }
}
