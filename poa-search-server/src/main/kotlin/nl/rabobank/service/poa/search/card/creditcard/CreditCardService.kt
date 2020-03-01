package nl.rabobank.service.poa.search.card.creditcard

import nl.rabobank.service.poa.search.card.creditcard.model.CreditCard
import org.springframework.stereotype.Service

@Service
class CreditCardService(private val creditCardAdapter: CreditCardAdapter) {

    fun getCreditCardById(id: String): CreditCard {
        return creditCardAdapter.getCreditCardById(id)
    }
}