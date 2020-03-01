package nl.rabobank.service.poa.search.account

import io.swagger.client.api.AccountApi
import nl.rabobank.service.poa.search.account.model.Account
import nl.rabobank.service.poa.search.account.model.internalize
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import nl.rabobank.service.poa.search.exception.InvalidAccountNumberException
import nl.rabobank.service.poa.search.util.isValidAccountNumber
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class AccountAdapter(private val accountApi: AccountApi) {

    fun getAccountById(accountId: String): Account {
        if (accountId.toIntOrNull() != null && accountId.toInt().isValidAccountNumber())
            return try {
                accountApi.getAccountDetail(accountId).internalize(accountId)
            }  catch (e: Exception) {
                throw DependencyFailedException("Accounts Service failed while getting account with id $accountId.", e)
            }
        else
            throw InvalidAccountNumberException(accountId)
    }
}
