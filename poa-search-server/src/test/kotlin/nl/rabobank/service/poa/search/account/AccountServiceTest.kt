package nl.rabobank.service.poa.search.account

import com.nhaarman.mockito_kotlin.whenever
import nl.rabobank.service.poa.search.util.validInternalAccount
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class AccountServiceTest {

    @Mock
    private lateinit var accountAdapter: AccountAdapter

    @InjectMocks
    private lateinit var accountService: AccountService


    @Test
    fun `get account by account number successfully`() {
        val accountId = 123456789
        whenever(accountAdapter.getAccountById(accountId)).thenReturn(validInternalAccount())
        val accountDetails = accountService.getAccountDetails(accountId)
        assertNotNull(accountDetails)
    }
}