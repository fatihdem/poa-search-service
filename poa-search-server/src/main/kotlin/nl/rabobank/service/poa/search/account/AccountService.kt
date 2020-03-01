package nl.rabobank.service.poa.search.account

import nl.rabobank.service.poa.search.account.model.Account
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountAdapter: AccountAdapter) {

    fun getAccountDetails(accountId: Int): Account {
        return accountAdapter.getAccountById(accountId.toString())
    }
}