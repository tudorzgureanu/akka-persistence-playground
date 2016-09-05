package com.ted.playground.akka.persistence.shopping

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.ted.playground.akka.persistence.fixtures.RestartableActor
import com.ted.playground.akka.persistence.fixtures.RestartableActor.RestartActor
import com.ted.playground.akka.persistence.shopping.ShoppingCartActor._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}


class ShoppingCartActorSpec2
  extends TestKit(ActorSystem("ShoppingCartActorSpec2"))
    with WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ImplicitSender {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "ShoppingCartActor" should {
    val shoppingItem = ShoppingItem("sku-000001", "Cheap headphones", 42.25, 2)

    "add an item to the shopping cart and preserve it after restart" in {
      val shoppingCartActor = system.actorOf(Props(new ShoppingCartActor("sc-000001") with RestartableActor))

      shoppingCartActor ! AddItemCommand(shoppingItem)
      expectMsg(AddItemResponse(shoppingItem))

      shoppingCartActor ! RestartActor
      shoppingCartActor ! GetItemsRequest

      expectMsg(GetItemsResponse(Seq(shoppingItem)))
    }

    "update an existing item to the shopping cart and preserve the changes after restart" in {
      val shoppingCartActor = system.actorOf(Props(new ShoppingCartActor("sc-000002") with RestartableActor))
      val updatedShoppingItem = shoppingItem.copy(quantity = 5)

      shoppingCartActor ! AddItemCommand(shoppingItem)
      expectMsg(AddItemResponse(shoppingItem))
      shoppingCartActor ! UpdateItemCommand(updatedShoppingItem)
      expectMsg(UpdateItemResponse(updatedShoppingItem))

      shoppingCartActor ! RestartActor
      shoppingCartActor ! GetItemsRequest

      expectMsg(GetItemsResponse(Seq(updatedShoppingItem)))
    }


    "remove an existing item from the shopping cart and preserve the changes after restart" in {
      val shoppingCartActor = system.actorOf(Props(new ShoppingCartActor("sc-000003") with RestartableActor))

      shoppingCartActor ! AddItemCommand(shoppingItem)
      expectMsg(AddItemResponse(shoppingItem))
      shoppingCartActor ! RemoveItemCommand(shoppingItem.id)
      expectMsg(RemoveItemResponse(shoppingItem.id))

      shoppingCartActor ! RestartActor
      shoppingCartActor ! GetItemsRequest

      expectMsg(GetItemsResponse(Seq.empty))
    }
  }
}
