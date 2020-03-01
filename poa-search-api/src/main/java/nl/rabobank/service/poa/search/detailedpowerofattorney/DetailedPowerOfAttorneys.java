package nl.rabobank.service.poa.search.detailedpowerofattorney;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.immutables.value.Value;

import javax.annotation.Nonnull;
import java.util.List;

@Value.Immutable
@JsonDeserialize(as = ImmutableDetailedPowerOfAttorneys.class)
public interface DetailedPowerOfAttorneys {
    @Nonnull
    @ApiModelProperty(required = true, value = "Contains all the detailed Power of Attorneys of a client")
    List<DetailedPowerOfAttorney> getDetailedPowerOfAttorneys();
}
