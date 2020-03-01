package nl.rabobank.service.poa.search.card.creditcard

import io.swagger.client.api.CreditCardApi
import nl.rabobank.service.poa.search.card.creditcard.model.CreditCard
import nl.rabobank.service.poa.search.card.creditcard.model.internalize
import nl.rabobank.service.poa.search.exception.DependencyFailedException
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class CreditCardAdapter(private val creditCardApi: CreditCardApi) {

    fun getCreditCardById(id: String): CreditCard {
        return try {
            creditCardApi.getCreditCardDetail(id).internalize()
        }  catch (e: Exception) {
            throw DependencyFailedException("Credit Card Service failed while getting Credit Card with id $id.", e)
        }
    }
}