package com.practice.simpleRest.datasource

import com.practice.simpleRest.model.Bank

interface BankDataSource {

    fun getBanks() : Collection<Bank>
    fun getBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteAccount(accountNumber: String)
}