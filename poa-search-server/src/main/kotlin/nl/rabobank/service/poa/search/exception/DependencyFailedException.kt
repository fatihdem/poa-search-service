package nl.rabobank.service.poa.search.exception

class DependencyFailedException(message: String, exception: Exception) : RuntimeException(message, exception)
