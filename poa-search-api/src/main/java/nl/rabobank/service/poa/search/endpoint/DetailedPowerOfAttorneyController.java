package nl.rabobank.service.poa.search.endpoint;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nl.rabobank.service.poa.search.detailedpowerofattorney.DetailedPowerOfAttorneys;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

@Api(value = "detailed-power-of-attorney", description = "Detailed Power of Attorney for a client")
@RestController
@RequestMapping(value = "/v1/detailed-power-of-attorney", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DetailedPowerOfAttorneyController {

    @ApiOperation(value = "Retrieves detailed Power of Attorney information for a client")
    @GetMapping
    DetailedPowerOfAttorneys getDetailedPowerOfAttorneyForClient(@Nonnull @RequestParam(value = "client-name") final String clientName);
}
