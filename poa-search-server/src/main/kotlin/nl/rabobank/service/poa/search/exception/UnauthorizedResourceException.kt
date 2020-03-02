package nl.rabobank.service.poa.search.exception

import nl.rabobank.service.poa.search.error.ErrorCodes

class UnauthorizedResourceException(username: String, resourceName: String) :
        InternalSearchException("Client $username is trying to reach resource $resourceName", ErrorCodes.UNAUTHORIZED_USER)