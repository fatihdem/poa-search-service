package nl.rabobank.service.poa.search.exception

class DependencyFailedException(message: String, val exception: Exception) : RuntimeException(message, exception)
