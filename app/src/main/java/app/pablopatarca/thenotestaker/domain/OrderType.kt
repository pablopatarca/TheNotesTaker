package app.pablopatarca.thenotestaker.domain

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
