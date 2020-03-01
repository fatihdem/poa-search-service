package nl.rabobank.service.poa.search.util

interface Exposable<T> {
    fun expose(): T
}