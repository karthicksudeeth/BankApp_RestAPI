package com.practice.simpleRest.controller
import com.practice.simpleRest.datasource.BankDataSource
import com.practice.simpleRest.datasource.mock.MockBankDataSource
import com.practice.simpleRest.model.Bank
import com.practice.simpleRest.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
@RestController
@RequestMapping("api/banks")
class BankController(private val service:BankService) {
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String>{
      return  ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler(IllegalArgumentException::class)
    fun duplicateElement(e:IllegalArgumentException):ResponseEntity<String>{
        return ResponseEntity(e.message,HttpStatus.BAD_REQUEST)
    }
    @GetMapping
    fun banks()= service.getBanks()
    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber:String): Bank {
        return service.getBank(accountNumber)
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank:Bank):Bank=service.addbank(bank)
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateBank(@RequestBody bank:Bank):Bank=service.
    updateBank(bank)
    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDank(@PathVariable accountNumber: String)=service.deleteAccount(accountNumber)
}
