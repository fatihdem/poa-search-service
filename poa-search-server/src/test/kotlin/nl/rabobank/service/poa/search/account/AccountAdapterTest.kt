package nl.rabobank.service.poa.search.account

import com.nhaarman.mockito_kotlin.whenever
import io.swagger.client.api.AccountApi
import nl.rabobank.service.poa.search.error.ErrorCodes
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.exception.InvalidAccountNumberException
import nl.rabobank.service.poa.search.util.validExternalAccount
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class AccountAdapterTest {
    @Mock
    private lateinit var accountApi: AccountApi

    @InjectMocks
    private lateinit var accountAdapter: AccountAdapter

    @Test
    fun `get account by account number successfully`() {
        val accountId = 123456789
        whenever(accountApi.getAccountDetail(accountId.toString())).thenReturn(validExternalAccount())
        val accountById = accountAdapter.getAccountById(accountId)
        assertNotNull(accountById)
        assert(accountById.id == accountId)
        //Since there is an extra logic in adapter layer we have to test this conversion
        assertEquals("NL23RABO123456789", accountById.iban, "To IBAN logic failed")
        //Only the complex objects requires assertion
        assertEquals(LocalDate.of(2010, 10, 10), accountById.created, "Date mapping went wrong")
    }

    @Test
    fun `get account by account number with invalid pattern account number`() {
        val accountId = 123

        val exception = assertThrows(InvalidAccountNumberException::class.java) {
            accountAdapter.getAccountById(accountId)
        }

        assertEquals(ErrorCodes.ACCOUNT_NUMBER_FORMATTING_EXCEPTION, exception.errorCode)
        assertEquals("Invalid account number: 123", exception.message)
    }

    @Test
    fun `get account by account number api returns invalid date for date field`() {
        val accountId = 123456789
        whenever(accountApi.getAccountDetail(accountId.toString())).thenReturn(validExternalAccount().created("101-13-1221"))

        val exception = assertThrows(DependencyFailedException::class.java) {
            accountAdapter.getAccountById(accountId)
        }

        assertEquals("Accounts Service failed while getting account with id 123456789.", exception.message)
    }

    @Test
    fun `get account by account number api fails`() {
        val accountId = 123456789
        whenever(accountApi.getAccountDetail(accountId.toString())).thenThrow(RuntimeException())

        val exception = assertThrows(DependencyFailedException::class.java) {
            accountAdapter.getAccountById(accountId)
        }

        assertEquals("Accounts Service failed while getting account with id 123456789.", exception.message)
    }
}