package com.ted.playground.akka.persistence

package object shopping {
  case class ShoppingItem(id: String, title: String, price: BigDecimal, quantity: Int)
}
