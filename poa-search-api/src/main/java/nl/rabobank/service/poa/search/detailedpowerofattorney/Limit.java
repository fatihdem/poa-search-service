package nl.rabobank.service.poa.search.detailedpowerofattorney;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;


@Value.Immutable
@JsonDeserialize(as = ImmutableLimit.class)
public interface Limit {
    @Nonnull
    @ApiModelProperty(required = true, value = "Limit in Euros")
    Integer getLimit();

    @Nonnull
    @ApiModelProperty(required = true, value = "Unit of period for limit")
    PeriodUnit getPeriodUnit();
}
