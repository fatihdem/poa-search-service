package nl.rabobank.service.poa.search.account.model

import nl.rabobank.service.poa.search.detailedpowerofattorney.ImmutableAccount
import nl.rabobank.service.poa.search.util.Exposable
import nl.rabobank.service.poa.search.util.toIbanString
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import io.swagger.client.model.Account as ExternalAccount
import nl.rabobank.service.poa.search.detailedpowerofattorney.Account as ExposedAccount

data class Account (
        val id: Int,
        val iban: String,
        val owner: String,
        val balance: Int,
        val created: LocalDate,
        val ended: LocalDate?
) : Exposable<ExposedAccount> {
    constructor(account: ExternalAccount, id: String) : this(
            id = id.toInt(),
            iban = id.toInt().toIbanString(),
            owner = account.owner,
            balance = account.balance,
            created = account.created.toLocalDate(),
            ended = account.ended?.toLocalDate()
    )

    override fun expose(): ExposedAccount = if (ended != null && ended.isBefore(LocalDate.now())) {
        ImmutableAccount.builder()
                .iban(this.iban)
                .endedDate(this.ended)
                .build()
    } else {
        ImmutableAccount.builder()
                .iban(this.iban)
                .owner(this.owner)
                .balance(this.balance)
                .createdDate(this.created)
                .endedDate(this.ended)
                .build()
    }
}

fun ExternalAccount.internalize(id: String) = Account(this, id)

private fun String.toLocalDate(): LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("dd-MM-yyyy"))




