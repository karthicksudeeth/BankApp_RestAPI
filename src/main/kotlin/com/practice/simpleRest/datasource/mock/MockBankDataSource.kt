package com.practice.simpleRest.datasource.mock

import com.practice.simpleRest.datasource.BankDataSource
import com.practice.simpleRest.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

@Repository
class MockBankDataSource: BankDataSource {
    val banks= mutableListOf(
            Bank("23456", 1.0, 24),
            Bank("45678", 0.0, 6),
            Bank("12345", 0.0, 2)
    )

    override fun getBanks(): Collection<Bank> = banks
    override fun getBank(accountNumber: String): Bank =
            banks.firstOrNull(){it.accountNumber == accountNumber}
                    ?: throw NoSuchElementException("could not find a bank with the account number $accountNumber")

    override fun addBank(bank: Bank): Bank {
        if(banks.any{it.accountNumber==bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber } already exist")
        }
       banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank=banks.firstOrNull(){it.accountNumber==bank.accountNumber}
                ?:throw IllegalArgumentException("Could not find a bank with the the account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank;
    }

    override fun deleteAccount(accountNumber: String) {
        val bank=banks.firstOrNull(){it.accountNumber==accountNumber}
                ?:throw NoSuchElementException("Could not find a bank with the gien account number $accountNumber")
        banks.remove(bank)
    }


}