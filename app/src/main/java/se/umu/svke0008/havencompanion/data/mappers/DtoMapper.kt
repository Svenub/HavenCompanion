package se.umu.svke0008.havencompanion.data.mappers

interface DtoMapper<J, E> {

    fun J.toEntity() : E

}