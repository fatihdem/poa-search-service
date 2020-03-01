package nl.rabobank.service.poa.search.exception

open class InternalSearchException(message: String, val errorCode: Int) : Exception(message)