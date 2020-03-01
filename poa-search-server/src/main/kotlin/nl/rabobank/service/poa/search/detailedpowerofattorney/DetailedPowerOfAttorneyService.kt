package nl.rabobank.service.poa.search.detailedpowerofattorney

import nl.rabobank.service.poa.search.account.AccountService
import nl.rabobank.service.poa.search.card.creditcard.CreditCardService
import nl.rabobank.service.poa.search.card.creditcard.model.CreditCard
import nl.rabobank.service.poa.search.card.debitcard.DebitCardService
import nl.rabobank.service.poa.search.card.debitcard.model.DebitCard
import nl.rabobank.service.poa.search.detailedpowerofattorney.model.DetailedPowerOfAttorney
import nl.rabobank.service.poa.search.powerofattorney.PowerOfAttorneyService
import nl.rabobank.service.poa.search.powerofattorney.model.Authorization
import nl.rabobank.service.poa.search.util.toAccountNumber
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class DetailedPowerOfAttorneyService(
        private val powerOfAttorneyService: PowerOfAttorneyService,
        private val accountService: AccountService,
        private val creditCardService: CreditCardService,
        private val debitCardService: DebitCardService
) {
    fun getDetailedPowerOfAttorneyByName(clientName: String): List<DetailedPowerOfAttorney> {
        val powerOfAttorneysOfClient = powerOfAttorneyService.getPowerOfAttorneysByClientName(clientName)

        return powerOfAttorneysOfClient.parallelStream().map { powerOfAttorney ->
            val debitCards = mutableListOf<DebitCard>()
            val creditCards = mutableListOf<CreditCard>()

            run {
                powerOfAttorney.cards.parallelStream().forEach {
                    if (it.isDebitCard && powerOfAttorney.authorizations.contains(Authorization.DEBIT_CARD)) {
                        debitCards.add(debitCardService.getDebitCardById(it.id))
                    } else if (!it.isDebitCard && powerOfAttorney.authorizations.contains(Authorization.CREDIT_CARD)) {
                        creditCards.add(creditCardService.getCreditCardById(it.id))
                    }
                }
            }

            val account = run { accountService.getAccountDetails(powerOfAttorney.accountIban.toAccountNumber()) }

            DetailedPowerOfAttorney(
                    id = powerOfAttorney.id,
                    grantor = powerOfAttorney.grantor,
                    grantee = powerOfAttorney.grantee,
                    direction = powerOfAttorney.direction,
                    authorizations = powerOfAttorney.authorizations,
                    account = account,
                    creditCards = creditCards,
                    debitCards = debitCards)
        }.toList()
    }
}