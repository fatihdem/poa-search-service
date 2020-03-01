package nl.rabobank.service.poa.search.detailedpowerofattorney;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableCard.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Card {

    @Nonnull
    @ApiModelProperty(required = true, value = "Id of the card.")
    String getId();

    @Nonnull
    @ApiModelProperty(required = true, value = "Type of the card", allowableValues = "CREDIT_CARD, DEBIT_CARD")
    String getType();

    @Nonnull
    @ApiModelProperty(required = true, value = "Status of the card")
    Status getStatus();

    @Nullable
    @ApiModelProperty(required = true, value = "Card number")
    Integer getCardNumber();

    @Nullable
    @ApiModelProperty(required = true, value = "Card sequence number")
    Integer getSequenceNumber();

    @Nullable
    @ApiModelProperty(required = true, value = "Name of the card holder")
    String getCardHolder();

    //Debit Card properties
    @Nullable
    @ApiModelProperty(value = "Atm Limit of the debit card")
    Limit getAtmLimit();

    @Nullable
    @ApiModelProperty(value = "Pos Limit of the debit card")
    Limit getPosLimit();

    @Nullable
    @ApiModelProperty(value = "Does debit card support contactless payment")
    Boolean getContactless();

    //Credit Card properties
    @Nullable
    @ApiModelProperty(value = "Monthly Limit of the credit card")
    Integer getMonthlyLimit();
}
