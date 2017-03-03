package services

import javax.inject.Inject

import anorm._
import domain._
import org.joda.time.{DateTime, DateTimeZone}
import play.api.db.Database

class TestDataCreatorService @Inject()(db: Database, menuRepo: MenuRepo, itemDeMenuRepo: ItemDeMenuRepo) {

  def createTestData() = {
    createCategorias()
    createEstadosPedido()
    createEstadosItemDePedido()
    createEstadosSugerencia()
    createStatus()
    createMenuPlusItems()
  }

  private def createCategorias() = {
    Categoria.values.foreach { value =>
      db.withConnection { implicit connection =>
        SQL(s"insert into Categoria (id, name) values ({id}, {name})"
        ).on('id -> value.id, 'name -> value.toString).
          executeInsert()
      }
    }
  }

  private def createEstadosPedido() = {
    EstadoPedido.values.foreach { value =>
      db.withConnection { implicit connection =>
        SQL(s"insert into EstadoPedido (id, name) values ({id}, {name})"
        ).on('id -> value.id, 'name -> value.toString).
          executeInsert()
      }
    }
  }

  private def createEstadosItemDePedido() = {
    EstadoItemDePedido.values.foreach { value =>
      db.withConnection { implicit connection =>
        SQL(s"insert into EstadoItemDePedido (id, name) values ({id}, {name})"
        ).on('id -> value.id, 'name -> value.toString).
          executeInsert()
      }
    }
  }

  private def createEstadosSugerencia() = {
    EstadoSugerencia.values.foreach { value =>
      db.withConnection { implicit connection =>
        SQL(s"insert into EstadoSugerencia (id, name) values ({id}, {name})"
        ).on('id -> value.id, 'name -> value.toString).
          executeInsert()
      }
    }
  }

  private def createStatus() = {
    Status.values.foreach { value =>
      db.withConnection { implicit connection =>
        SQL(s"insert into Status (id, name) values ({id}, {name})"
        ).on('id -> value.id, 'name -> value.toString).
          executeInsert()
      }
    }
  }

  private def createMenuPlusItems() {
    for {
      item_1 <- itemDeMenuRepo.create(
                    ItemDeMenu(0L, Status.Active, Categoria.PlatosPrincipales, "Milanesa Cappresse", "Milanesa con queso, albahaca y tomate",
                               precio = 70, DateTime.now(), DateTime.now())
                ).right
      item_2 <- itemDeMenuRepo.create(
                  ItemDeMenu(0L, Status.Active, Categoria.PlatosPrincipales, "Puré de Papas", "Puré natural de papas",
                             precio = 30, DateTime.now(), DateTime.now())
                ).right
      menu <- menuRepo.create(
                Menu(0L, Status.Active, "Menú Principal", DateTime.now(DateTimeZone.UTC), DateTime.now(DateTimeZone.UTC), vigente = true, Seq(item_1, item_2))
              ).right
    } yield menu
  }

}
