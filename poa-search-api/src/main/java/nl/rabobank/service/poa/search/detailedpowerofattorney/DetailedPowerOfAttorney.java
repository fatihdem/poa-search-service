package nl.rabobank.service.poa.search.detailedpowerofattorney;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;
import java.util.List;


@Value.Immutable
@JsonDeserialize(as = ImmutableDetailedPowerOfAttorney.class)
public interface DetailedPowerOfAttorney {

    @Nonnull
    @ApiModelProperty(required = true, value = "Id of Power of Attorney.")
    String getId();

    @Nonnull
    @ApiModelProperty(required = true, value = "Grantor name of Power of Attorney")
    String getGrantor();

    @Nonnull
    @ApiModelProperty(required = true, value = "Grantee name of Power of Attorney")
    String getGrantee();

    @Nonnull
    @ApiModelProperty(required = true, value = "Information of the account that has been granted by Power of Attorney")
    Account getAccount();

    @Nonnull
    @ApiModelProperty(required = true, value = "Direction of Power of Attorney", allowableValues = "GIVEN, RECEIVED")
    Direction getDirection();

    @Nonnull
    @ApiModelProperty(required = true, value = "List of authorizations for Power of Attorney")
    List<Authorization> getAuthorizations();

    @Nonnull
    @ApiModelProperty(required = true, value = "List of cards that have been granted by Power of Attorney")
    List<Card> getCards();

}
