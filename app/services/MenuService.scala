package services

import javax.inject.Inject

import domain.{Menu, MenuRepo}
import org.joda.time.DateTime

class MenuService @Inject()(menuRepo: MenuRepo) {

  def getLatestMenu(lastKnownModificationDate: Option[DateTime]): Either[String, Menu] = {
/*
    for {
      menu

    }

    val menu = menuRepo.getLatestActiveMenu()

    if (lastKnownModificationDate.isDefined && menu.lastModificationDate == lastKnownModificationDate) {
        return Left("NO_CONTENT")
    } else {
      return Right(menu)
    }*/
    Left("")
  }

}
