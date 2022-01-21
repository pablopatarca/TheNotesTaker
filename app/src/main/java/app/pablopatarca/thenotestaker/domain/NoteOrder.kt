package app.pablopatarca.thenotestaker.domain

sealed class NoteOrder(val orderType: OrderType) {
    class UpdatedAt(orderType: OrderType): NoteOrder(orderType)
    class CreatedAt(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)
}
