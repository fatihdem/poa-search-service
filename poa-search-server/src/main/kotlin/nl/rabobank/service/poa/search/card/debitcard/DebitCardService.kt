package nl.rabobank.service.poa.search.card.debitcard

import nl.rabobank.service.poa.search.card.debitcard.model.DebitCard
import org.springframework.stereotype.Service

@Service
class DebitCardService(private val debitCardAdapter: DebitCardAdapter) {

    fun getDebitCardById(id: String): DebitCard {
        return debitCardAdapter.getDebitCardById(id)
    }
}