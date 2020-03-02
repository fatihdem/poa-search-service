package nl.rabobank.service.poa.search.card.debitcard

import io.swagger.client.api.DebitCardApi
import nl.rabobank.service.poa.search.card.debitcard.model.DebitCard
import nl.rabobank.service.poa.search.card.debitcard.model.internalize
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import org.springframework.stereotype.Component

@Component
class DebitCardAdapter(private val debitCardApi: DebitCardApi) {

    fun getDebitCardById(id: String): DebitCard {
        return try {
            debitCardApi.getDebitCardDetail(id).internalize()
        } catch (e: Exception) {
            throw DependencyFailedException("Debit Card Service failed while getting Debit Card with id $id.", e)
        }
    }
}