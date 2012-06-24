package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.db._
import play.api.Play.current

/**
 * Created with IntelliJ IDEA.
 * User: kwabena
 * Date: 21/06/12
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */

case class Task(id : Long, label : String)


object Task {

  def all() : List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}
