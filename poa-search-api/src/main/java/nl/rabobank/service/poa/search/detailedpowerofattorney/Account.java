package nl.rabobank.service.poa.search.detailedpowerofattorney;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;


@Value.Immutable
@JsonDeserialize(as = ImmutableAccount.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Account {

    @Nonnull
    @ApiModelProperty(required = true, value = "Iban of the account.")
    String getIban();

    @Nullable
    @ApiModelProperty(required = true, value = "Owner of the account.")
    String getOwner();

    @Nullable
    @ApiModelProperty(required = true, value = "Balance of the account.")
    Integer getBalance();

    @Nullable
    @ApiModelProperty(required = true, value = "Date of creation for the account.")
    LocalDate getCreatedDate();

    @Nullable
    @ApiModelProperty(value = "Date of end for the account.")
    LocalDate getEndedDate();
}
