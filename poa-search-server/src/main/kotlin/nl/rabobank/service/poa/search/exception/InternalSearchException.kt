package nl.rabobank.service.poa.search.exception

import java.lang.Exception

open class InternalSearchException(message: String, val errorCode: Int) : Exception(message)